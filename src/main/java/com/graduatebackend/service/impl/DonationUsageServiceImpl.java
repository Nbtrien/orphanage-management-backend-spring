package com.graduatebackend.service.impl;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.web3j.protocol.exceptions.TransactionException;

import com.graduatebackend.blockchain.AddNewDonationUsageRequestDto;
import com.graduatebackend.blockchain.BlockChainService;
import com.graduatebackend.blockchain.DonationContract;
import com.graduatebackend.blockchain.GetDonationContractResponseDto;
import com.graduatebackend.blockchain.GetDonationStatsResponseDto;
import com.graduatebackend.dto.donation.GetFundingUsageDetailResponseDto;
import com.graduatebackend.dto.donation.GetFundingUsageResponseDto;
import com.graduatebackend.dto.donation.UseDonationByPurposeRequestDto;
import com.graduatebackend.email.EmailService;
import com.graduatebackend.email.EmailUtilsService;
import com.graduatebackend.entity.Donation;
import com.graduatebackend.entity.DonationPurpose;
import com.graduatebackend.entity.DonationUsage;
import com.graduatebackend.entity.Family;
import com.graduatebackend.entity.FundingUsage;
import com.graduatebackend.exception.DonationUsageException;
import com.graduatebackend.exception.ResourceNotFoundException;
import com.graduatebackend.mapper.DonationMapper;
import com.graduatebackend.repository.DonationPurposeRepository;
import com.graduatebackend.repository.DonationRepository;
import com.graduatebackend.repository.FamilyRepository;
import com.graduatebackend.repository.FundingUsageRepository;
import com.graduatebackend.service.DonationUsageService;
import com.graduatebackend.utils.ConvertUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DonationUsageServiceImpl implements DonationUsageService {
	private final FundingUsageRepository fundingUsageRepository;
	private final DonationRepository donationRepository;
	private final DonationPurposeRepository donationPurposeRepository;
	private final FamilyRepository familyRepository;
	private final BlockChainService blockChainService;
	private final EmailService emailService;
	private final EmailUtilsService emailUtilsService;

	@Override
	public GetFundingUsageDetailResponseDto getFundingUsageDetail(Integer id) {
		FundingUsage fundingUsage = fundingUsageRepository.findByFundingUsageIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Funding usage not found."));
		return DonationMapper.INSTANCE.toGetFundingUsageDetailResponseDto(fundingUsage);
	}

	@Override
	public Page<GetFundingUsageResponseDto> fetchFundingUsage(PageRequest pageRequest, String keyword,
			Integer purposeId, String fromDate, String toDate) {
		Date minUsageDate = fromDate == null ? null : Date.valueOf(fromDate);
		Date maxUsageDate = toDate == null ? null : Date.valueOf(toDate);
		Page<FundingUsage> fundingUsages = fundingUsageRepository.getAllByIsDeleteIsFalse(pageRequest, keyword,
				purposeId, minUsageDate, maxUsageDate);
		return fundingUsages.map(DonationMapper.INSTANCE::toGetFundingUsageResponseDto);
	}

	@Override
	public FundingUsage useDonationByPurpose(Integer purposeId, UseDonationByPurposeRequestDto requestDto) {
		DonationPurpose donationPurpose = donationPurposeRepository.findByDonationPurposeIdAndIsDeleteIsFalse(purposeId)
				.orElseThrow(() -> new ResourceNotFoundException("Purpose not found."));

		Family family = null;
		if (requestDto.getFamilyId() != null) {
			family = familyRepository.findFamilyByFamilyIdAndIsDeleteIsFalse(requestDto.getFamilyId()).orElse(null);
		}

		GetDonationStatsResponseDto donationStats = blockChainService
				.getPurposeStats(donationPurpose.getDonationPurposeId());
		if (donationStats.getRemainingAmount() < requestDto.getAmount()) {
			throw new DonationUsageException("Amount is over.");
		}

		List<GetDonationContractResponseDto> donationContractResponses = blockChainService
				.getDonationByPurposeForUse(purposeId, requestDto.getAmount());
		List<DonationUsage> donationUsageList = new ArrayList<>();
		double amountRequest = requestDto.getAmount();
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		for (GetDonationContractResponseDto donationContract : donationContractResponses) {
			double remainingAmount = donationContract.getAmount();
			double usageAmount = Math.min(amountRequest, remainingAmount);
			AddNewDonationUsageRequestDto donationUsageRequestDto = AddNewDonationUsageRequestDto.builder()
					.donationId(BigInteger.valueOf(donationContract.getDonationId()))
					.donationHash(ConvertUtils.hexToBytes(donationContract.getDonationHash()))
					.usageTime(currentTime.getTime()).amount(usageAmount).purposeId(BigInteger.valueOf(purposeId))
					.familyId(BigInteger.valueOf(family == null ? 0 : family.getFamilyId()))
					.purpose(requestDto.getNote() == null ? "" : requestDto.getNote()).build();
			DonationContract.DonationUsageAddedEventResponse eventResponse = null;
			try {
				eventResponse = blockChainService.addDonationUsage(donationUsageRequestDto);
			} catch (ExecutionException e) {
				throw new RuntimeException(e);
			}
			amountRequest = amountRequest - usageAmount;

			Donation donation = donationRepository.findByDonationIdAndIsDeleteIsFalse(donationContract.getDonationId())
					.get();
			donation.setUsedAmount(eventResponse.usedAmount.longValueExact());
			if (donation.getUsedAmount() >= donation.getAmount())
				donation.setUsed(true);
			Donation donationSaved = donationRepository.saveAndFlush(donation);

			DonationUsage donationUsage = DonationUsage.builder().donation(donationSaved)
					.donationHash(ConvertUtils.bytesToHex(eventResponse.donationHash))
					.usageHash(ConvertUtils.bytesToHex(eventResponse.usageHash))
					.amount(eventResponse.amount.longValueExact())
					.blockchainHash(eventResponse.blockHash.intValueExact()).isInBlockchain(true).usageTime(currentTime)
					.transactionHash(eventResponse.log.getTransactionHash()).build();
			donationUsageList.add(donationUsage);
		}
		FundingUsage fundingUsage = FundingUsage.builder().usageTime(currentTime).usageNote(requestDto.getNote())
				.amount(requestDto.getAmount()).purpose(donationPurpose).family(family).build();
		fundingUsage.addDonationUsages(donationUsageList);
		FundingUsage fundingUsageSaved = fundingUsageRepository.save(fundingUsage);
//		sendFundingUsageEmail(fundingUsageSaved);
		return fundingUsageSaved;
	}

	@Override
	public FundingUsage useDonationById(Integer id, UseDonationByPurposeRequestDto requestDto) {
		Donation donation = donationRepository.findByDonationIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Donation not found."));
		if (donation.isUsed())
			throw new DonationUsageException("Donation is used");
		Family family = null;
		if (requestDto.getFamilyId() != null) {
			family = familyRepository.findFamilyByFamilyIdAndIsDeleteIsFalse(requestDto.getFamilyId()).orElse(null);
		}
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		AddNewDonationUsageRequestDto donationUsageRequestDto = AddNewDonationUsageRequestDto.builder()
				.donationId(BigInteger.valueOf(donation.getDonationId()))
				.donationHash(ConvertUtils.hexToBytes(donation.getDonationHash())).usageTime(currentTime.getTime())
				.amount(requestDto.getAmount())
				.purposeId(BigInteger.valueOf(donation.getPurpose().getDonationPurposeId()))
				.familyId(BigInteger.valueOf(family == null ? 0 : family.getFamilyId()))
				.purpose(requestDto.getNote() == null ? "" : requestDto.getNote()).build();
		DonationContract.DonationUsageAddedEventResponse eventResponse = null;
		try {
			eventResponse = blockChainService.addDonationUsage(donationUsageRequestDto);
		} catch (ExecutionException e) {
			Throwable cause = e.getCause();
			if (cause.getCause() instanceof TransactionException) {
				TransactionException transactionException = (TransactionException) cause.getCause();
				transactionException.printStackTrace();
				throw new DonationUsageException(transactionException.getMessage());
			}
			throw new DonationUsageException(e.getMessage());
		} catch (RuntimeException e) {
			throw new DonationUsageException(e.getMessage());
		}

		donation.setUsedAmount(eventResponse.usedAmount.longValueExact());
		if (donation.getUsedAmount() >= donation.getAmount())
			donation.setUsed(true);
		Donation donationSaved = donationRepository.saveAndFlush(donation);

		DonationUsage donationUsage = DonationUsage.builder().donation(donationSaved)
				.donationHash(ConvertUtils.bytesToHex(eventResponse.donationHash))
				.usageHash(ConvertUtils.bytesToHex(eventResponse.usageHash))
				.amount(eventResponse.amount.longValueExact()).blockchainHash(eventResponse.blockHash.intValueExact())
				.isInBlockchain(true).usageTime(currentTime).transactionHash(eventResponse.log.getTransactionHash())
				.build();
		FundingUsage fundingUsage = FundingUsage.builder().usageTime(currentTime).usageNote(requestDto.getNote())
				.amount(requestDto.getAmount()).purpose(donation.getPurpose()).family(family).build();
		fundingUsage.addDonationUsage(donationUsage);
		FundingUsage fundingUsageSaved = fundingUsageRepository.save(fundingUsage);
//		sendFundingUsageEmail(fundingUsageSaved);
		return fundingUsageSaved;
	}

	@Async
	@Override
	public CompletableFuture<Void> sendFundingUsageEmail(FundingUsage fundingUsage) {
		fundingUsage.getDonationUsages()
				.forEach(donationUsage -> emailUtilsService.sendDonationUsageConfirmationEmail(
						donationUsage.getDonation().getDonor().getDonorMailAddress(),
						donationUsage.getDonation().getDonor().getDonorFullName(),
						donationUsage.getDonation().getDonor().getDonorToken(),
						donationUsage.getDonation().getDonationHash(), donationUsage.getDonation().getAmount(),
						donationUsage.getDonation().getDonationTime(), donationUsage.getAmount(),
						donationUsage.getUsageTime(), fundingUsage.getUsageNote()));
		return CompletableFuture.completedFuture(null);
	}

//	@Async
//	public CompletableFuture<Void> sendDonationUsageConfirmationEmail(String to, String donorName, String donorToken,
//			String donationHash, double donationAmount, Timestamp donationTime, double usageAmount, Timestamp usageTime,
//			String usageNote) {
//		String subject = "Xác Nhận Sử Dụng Quyên Góp của Bạn";
//		String message = "Kính gửi quý ông/bà " + donorName + ", \n\n"
//				+ "Chúng tôi xin thông báo với bạn về khoản tài trợ của bạn:\n" + "Số Tiền: "
//				+ ConvertUtils.formatToVND(donationAmount) + ". \n" + "Ngày quyên Góp: "
//				+ ConvertUtils.formatTimestamp(donationTime) + ". \n" + "Mã quyên Góp: " + donationHash + ".\n"
//				+ "Đã được trại trẻ của chúng tôi sử dụng với thông tin như sau: \n"
//				+ "Số tiền đã sử dụng: " + ConvertUtils.formatToVND(usageAmount) + ". \n" + "Ngày sử dụng: "
//				+ ConvertUtils.formatTimestamp(usageTime) + ". \n" + "Chi tiết: " + usageNote + ". \n"
//				+ "Chúng tôi cam kết sử dụng mọi đồng tiền quyên góp của bạn một cách minh bạch và hiệu quả nhất để "
//				+ "đảm bảo rằng nó sẽ đến đúng nơi và tạo ra sự thay đổi tích cực.\n"
//				+ "Hãy theo dõi trang web của chúng tôi hoặc liên hệ trực tiếp để cập nhật về cách chúng tôi sử dụng "
//				+ "quyên góp của bạn và những tiến " + "triển mới nhất.\n\n"
//				+ "Một lần nữa, chúng tôi chân thành cảm ơn sự hỗ trợ của bạn và hy vọng nhận được sự tiếp tục ủng hộ"
//				+ " trong tương lai.\n\n" + "Mọi thắc mắc và góp ý, xin vui lòng liên hệ với chúng tôi qua:\n"
//				+ "Email: support.orphanage@gmail.com\n" + "Hotline: 0123456789\n\n" + "Trân trọng,\n" + "Orphanage";
//
//		emailService.sendEmailWithSimpleMessage(to, subject, message);
//		return CompletableFuture.completedFuture(null);
//	}
}
