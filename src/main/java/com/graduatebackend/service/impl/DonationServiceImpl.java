package com.graduatebackend.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.graduatebackend.blockchain.AddNewDonationContractRequestDto;
import com.graduatebackend.blockchain.AddNewDonationContractResponseDto;
import com.graduatebackend.blockchain.BlockChainService;
import com.graduatebackend.blockchain.GetDonationContractResponseDto;
import com.graduatebackend.blockchain.GetDonationStatsResponseDto;
import com.graduatebackend.config.VnpayConfiguration;
import com.graduatebackend.constant.ChildrenCode;
import com.graduatebackend.constant.DonationCode;
import com.graduatebackend.dto.donation.AddNewDonationRequestDto;
import com.graduatebackend.dto.donation.GetDonationByPurposeResponseDto;
import com.graduatebackend.dto.donation.GetDonationDetailResponseDto;
import com.graduatebackend.dto.donation.GetDonationMonthlyResponseDto;
import com.graduatebackend.dto.donation.GetDonationResponseDto;
import com.graduatebackend.dto.donation.GetDonationStatResponseDto;
import com.graduatebackend.dto.donation.GetDonationTrackingResponseDto;
import com.graduatebackend.dto.donation.VnpayResponseDto;
import com.graduatebackend.email.EmailService;
import com.graduatebackend.entity.Account;
import com.graduatebackend.entity.Donation;
import com.graduatebackend.entity.DonationPurpose;
import com.graduatebackend.entity.Donor;
import com.graduatebackend.entity.Family;
import com.graduatebackend.exception.ResourceNotFoundException;
import com.graduatebackend.mapper.DonationMapper;
import com.graduatebackend.mapper.FamilyMapper;
import com.graduatebackend.repository.AccountRepository;
import com.graduatebackend.repository.DonationPurposeRepository;
import com.graduatebackend.repository.DonationRepository;
import com.graduatebackend.repository.DonorRepository;
import com.graduatebackend.repository.FamilyRepository;
import com.graduatebackend.service.DonationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {
	private final DonorRepository donorRepository;
	private final DonationRepository donationRepository;
	private final VnpayConfiguration vnpayConfig;
	private final EmailService emailService;
	private final BlockChainService blockChainService;
	private final DonationPurposeRepository donationPurposeRepository;
	private final FamilyRepository familyRepository;
	private final AccountRepository accountRepository;

	@Override
	@Transactional
	public String addNew(AddNewDonationRequestDto requestDto, String ipAddress) {
		Donor donor = DonationMapper.INSTANCE.toDonorEntity(requestDto.getDonor());
		donor.setDonorFullName(donor.getDonorLastName() + " " + donor.getDonorFirstName());
		donor.setDonorToken(generateTokenFromEmail(donor.getDonorMailAddress()));
		Donor donorSaved = donorRepository.saveAndFlush(donor);

		DonationPurpose donationPurpose = donationPurposeRepository
				.findByDonationPurposeIdAndIsDeleteIsFalse(requestDto.getDonationPurposeId())
				.orElseThrow(() -> new ResourceNotFoundException("Purpose not found."));

		Donation donation = Donation.builder().donor(donorSaved).donationTime(new Timestamp(System.currentTimeMillis()))
				.amount(requestDto.getAmount()).donationMessage(requestDto.getDonationMessage())
				.paymentStatus(DonationCode.PaymentStatus.NOT_PAID.getCode()).purpose(donationPurpose)
				.isInBlockchain(false).isSentEmail(false).isUsed(false).build();

		// Check if purpose is family donate and family id is not null
		if (donationPurpose.getDonationPurposeId() == 2 && requestDto.getFamilyId() != null) {
			Family family = familyRepository.findFamilyByFamilyIdAndIsDeleteIsFalse(requestDto.getFamilyId())
					.orElseThrow(() -> new ResourceNotFoundException("Family not found."));
			donation.setFamily(family);
		}
		Donation donationSaved = donationRepository.saveAndFlush(donation);

		return generatePaymentUrl(requestDto.getAmount(), donationSaved.getDonationId(), requestDto.getUrlReturn(),
				requestDto.getDonationMessage(), ipAddress);
	}

	@Override
	public String accountAddNewDonation(Integer accountId, AddNewDonationRequestDto requestDto, String ipAddress) {
		Account account = accountRepository.findByAccountIdAndIsDeleteIsFalse(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found."));

		Donor donor = DonationMapper.INSTANCE.toDonorEntity(requestDto.getDonor());
		donor.setDonorFullName(donor.getDonorLastName() + " " + donor.getDonorFirstName());
		donor.setDonorToken(generateTokenFromEmail(donor.getDonorMailAddress()));
		Donor donorSaved = donorRepository.saveAndFlush(donor);

		account.setDonor(donor);
		accountRepository.save(account);

		DonationPurpose donationPurpose = donationPurposeRepository
				.findByDonationPurposeIdAndIsDeleteIsFalse(requestDto.getDonationPurposeId())
				.orElseThrow(() -> new ResourceNotFoundException("Purpose not found."));

		Donation donation = Donation.builder().donor(donorSaved).donationTime(new Timestamp(System.currentTimeMillis()))
				.amount(requestDto.getAmount()).donationMessage(requestDto.getDonationMessage())
				.paymentStatus(DonationCode.PaymentStatus.NOT_PAID.getCode()).purpose(donationPurpose)
				.isInBlockchain(false).isSentEmail(false).isUsed(false).build();

		// Check if purpose is family donate and family id is not null
		if (donationPurpose.getDonationPurposeId() == 2 && requestDto.getFamilyId() != null) {
			Family family = familyRepository.findFamilyByFamilyIdAndIsDeleteIsFalse(requestDto.getFamilyId())
					.orElseThrow(() -> new ResourceNotFoundException("Family not found."));
			donation.setFamily(family);
		}
		Donation donationSaved = donationRepository.saveAndFlush(donation);

		return generatePaymentUrl(requestDto.getAmount(), donationSaved.getDonationId(), requestDto.getUrlReturn(),
				requestDto.getDonationMessage(), ipAddress);
	}

	@Override
	public String donorDoDonate(Integer donorId, AddNewDonationRequestDto requestDto, String ipAddress) {
		Donor donor = donorRepository.findByDonorIdAndIsDeleteIsFalse(donorId)
				.orElseThrow(() -> new ResourceNotFoundException("Donor not found!"));
		DonationPurpose donationPurpose = donationPurposeRepository
				.findByDonationPurposeIdAndIsDeleteIsFalse(requestDto.getDonationPurposeId())
				.orElseThrow(() -> new ResourceNotFoundException("Purpose not found."));

		Donation donation = Donation.builder().donor(donor).donationTime(new Timestamp(System.currentTimeMillis()))
				.amount(requestDto.getAmount()).donationMessage(requestDto.getDonationMessage())
				.paymentStatus(DonationCode.PaymentStatus.NOT_PAID.getCode()).purpose(donationPurpose)
				.isInBlockchain(false).isSentEmail(false).isUsed(false).build();
		// Check if purpose is family donate and family id is not null
		if (donationPurpose.getDonationPurposeId() == 2 && requestDto.getFamilyId() != null) {
			Family family = familyRepository.findFamilyByFamilyIdAndIsDeleteIsFalse(requestDto.getFamilyId())
					.orElseThrow(() -> new ResourceNotFoundException("Family not found."));
			donation.setFamily(family);
		}
		Donation donationSaved = donationRepository.saveAndFlush(donation);

		return generatePaymentUrl(requestDto.getAmount(), donationSaved.getDonationId(), requestDto.getUrlReturn(),
				requestDto.getDonationMessage(), ipAddress);
	}

	@Override
	public VnpayResponseDto update(Map<String, String> requestParams) {
		Integer donationId = Integer.valueOf(requestParams.get("vnp_TxnRef"));
		Donation donation = donationRepository.findByDonationIdAndIsDeleteIsFalse(donationId)
				.orElseThrow(() -> new ResourceNotFoundException("Donation not found!"));

		if (donation.getPaymentStatus() == DonationCode.PaymentStatus.NOT_PAID.getCode()) {
			donation.setBankCode(requestParams.get("vnp_BankCode"));
			donation.setBankTranNo(requestParams.get("vnp_BankTranNo"));
			donation.setTransactionNo(requestParams.get("vnp_TransactionNo"));
			donation.setCardType(requestParams.get("vnp_CardType"));

			String transactionStatus = requestParams.get("vnp_TransactionStatus");
			donation.setTransactionStatus(transactionStatus);
			if (DonationCode.ResponseCode.SUCCESS.getCode().equals(transactionStatus)) {
				donation.setPaymentStatus(DonationCode.PaymentStatus.PAID.getCode());
			} else {
				donation.setPaymentStatus(DonationCode.PaymentStatus.PAYMENT_FAILED.getCode());
			}

			String vnp_PayDate = requestParams.get("vnp_PayDate");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			try {
				Date parsedDate = dateFormat.parse(vnp_PayDate);
				Timestamp timestamp = new Timestamp(parsedDate.getTime());
				donation.setPayDate(timestamp);
			} catch (ParseException e) {
				donation.setPayDate(null);
			}

			AddNewDonationContractRequestDto newDonationContractRequestDto = AddNewDonationContractRequestDto.builder()
					.donationId(donation.getDonationId()).donorName(donation.getDonor().getDonorFullName())
					.donorToken(donation.getDonor().getDonorToken()).amount(donation.getAmount())
					.purposeId(donation.getPurpose().getDonationPurposeId()).purpose(donation.getPurpose().getPurpose())
					.familyId(donation.getFamily() == null ? 0 : donation.getFamily().getFamilyId())
					.donationTime(donation.getDonationTime().getTime()).build();

			AddNewDonationContractResponseDto donationContractResponse = blockChainService
					.addDonation(newDonationContractRequestDto);

			donation.setBlockchainHash(donationContractResponse.getBlockHash());
			donation.setDonationHash(donationContractResponse.getDonationHash());
			donation.setTransactionHash(donationContractResponse.getTransactionHash());
			donation.setInBlockchain(true);

			Donation donationSaved = donationRepository.saveAndFlush(donation);
			if (!donationSaved.isSentEmail())
				sendDonationConfirmationEmail(donationSaved);
		}

		return VnpayResponseDto.builder().RspCode("00").Message("Confirm Success").build();
	}

	@Override
	public Page<GetDonationResponseDto> fetch(PageRequest pageRequest, String keyword, Integer purposeId,
			String fromDate, String toDate) {
		java.sql.Date minDonationDate = fromDate == null ? null : java.sql.Date.valueOf(fromDate);
		java.sql.Date maxDonationDate = toDate == null ? null : java.sql.Date.valueOf(toDate);
		Page<Donation> donations = donationRepository.findAllDonations(pageRequest, keyword, purposeId, minDonationDate,
				maxDonationDate);
		List<GetDonationResponseDto> responseDtoList = donations.stream().filter(donation -> {
			GetDonationContractResponseDto contractResponseDto = blockChainService
					.getDonationByDonationHash(donation.getDonationHash());
			if (contractResponseDto.getDonationId() != 0) {
				return contractResponseDto.getAmount() == donation.getAmount()
						&& contractResponseDto.getDonationId().intValue() == donation.getDonationId()
						&& contractResponseDto.getDonorToken().equals(donation.getDonor().getDonorToken());
			} else {
				return false;
			}
		}).map(donation -> {
			GetDonationResponseDto responseDto = DonationMapper.INSTANCE.toGetDonationResponseDto(donation);
			responseDto.setRemainingAmount(responseDto.getAmount() - responseDto.getUsedAmount());
			return responseDto;
		}).collect(Collectors.toList());

		return new PageImpl<>(responseDtoList, pageRequest, donations.getTotalElements());
	}

	@Override
	public GetDonationDetailResponseDto getDetail(Integer id) {
		Donation donation = donationRepository.findByDonationIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Donation not found."));
		GetDonationDetailResponseDto donationDetailResponseDto = DonationMapper.INSTANCE
				.toGetDonationDetailResponseDto(donation);
		donationDetailResponseDto.getDonor()
				.setDonorGender(ChildrenCode.Gender.of(donation.getDonor().getDonorGender()).getDisplay());
		donationDetailResponseDto.setRemainingAmount(donation.getAmount() - donation.getUsedAmount());
		return donationDetailResponseDto;
	}

	@Override
	public GetDonationStatResponseDto getDonationStats() {
		List<Object[]> result = donationRepository.getDonationStatistics();
		Object[] row = result.get(0);

		Long donationCount = (Long) row[0];
		Double totalAmount = (Double) row[1];
		Double usedAmount = (Double) row[2];
		Long donorCount = (Long) row[3];

		return GetDonationStatResponseDto.builder().donationCount(Math.toIntExact(donationCount))
				.totalAmount(totalAmount).usedAmount(usedAmount).donorCount(Math.toIntExact(donorCount)).build();
	}

	@Override
	public GetDonationByPurposeResponseDto fetchByPurpose(PageRequest pageRequest, Integer purposeId) {
		Page<Donation> donations = donationRepository.findAvailableByPurpose(pageRequest, purposeId);
		List<GetDonationResponseDto> responseDtoList = donations.stream().filter(donation -> {
			GetDonationContractResponseDto contractResponseDto = blockChainService
					.getDonationByDonationHash(donation.getDonationHash());
			if (contractResponseDto.getDonationId() != 0) {
				return contractResponseDto.getAmount() == donation.getAmount()
						&& contractResponseDto.getDonationId().intValue() == donation.getDonationId()
						&& contractResponseDto.getDonorToken().equals(donation.getDonor().getDonorToken());
			} else {
				return false;
			}
		}).map(donation -> {
			GetDonationResponseDto responseDto = DonationMapper.INSTANCE.toGetDonationResponseDto(donation);
			responseDto.setRemainingAmount(responseDto.getAmount() - responseDto.getUsedAmount());
			return responseDto;
		}).collect(Collectors.toList());

		Page<GetDonationResponseDto> donationResponseDtoPage = new PageImpl<>(responseDtoList, pageRequest,
				donations.getTotalElements());
		GetDonationStatsResponseDto donationStats = blockChainService.getPurposeStats(purposeId);

		return new GetDonationByPurposeResponseDto(donationResponseDtoPage, donationStats);
	}

	@Override
	public List<GetDonationTrackingResponseDto> getDonationsByDonorToken(String donorToken) {
		List<GetDonationContractResponseDto> donationContracts = blockChainService.getDonationsByDonorToken(donorToken);
		List<GetDonationTrackingResponseDto> donationTrackingResponses = donationContracts.stream()
				.map(donationContract -> {
					GetDonationTrackingResponseDto donationTracking = DonationMapper.INSTANCE
							.toGetDonationTrackingResponseDto(donationContract);
					Donation donation = donationRepository
							.findByDonationIdAndIsDeleteIsFalse(donationContract.getDonationId())
							.orElseThrow(() -> new ResourceNotFoundException("Donation not found."));
					donationTracking.setDonationMessage(donation.getDonationMessage());
					donationTracking.setFamily(donation.getFamily() != null
							? FamilyMapper.INSTANCE.toGetFamilyForDonateResponseDto(donation.getFamily())
							: null);
					donationTracking.setUsed(donation.isUsed());
					donationTracking.setUsedAmount(donation.getUsedAmount());
					donationTracking.setRemainingAmount(donation.getAmount() - donation.getUsedAmount());
					donationTracking.setDonationUsages(donation.getUsages().size() != 0
							? DonationMapper.INSTANCE.toGetDonationUsageResponseDtoList(donation.getUsages())
							: null);
					return donationTracking;
				}).collect(Collectors.toList());
		return donationTrackingResponses;
	}

	@Override
	public GetDonationTrackingResponseDto getDonationTrackingByDonationHash(String donationHash) {
		GetDonationContractResponseDto donationContract = blockChainService.getDonationByDonationHash(donationHash);
		GetDonationTrackingResponseDto donationTracking = DonationMapper.INSTANCE
				.toGetDonationTrackingResponseDto(donationContract);
		Donation donation = donationRepository.findByDonationIdAndIsDeleteIsFalse(donationContract.getDonationId())
				.orElseThrow(() -> new ResourceNotFoundException("Donation not found."));
		donationTracking.setDonationMessage(donation.getDonationMessage());
		donationTracking.setFamily(donation.getFamily() != null
				? FamilyMapper.INSTANCE.toGetFamilyForDonateResponseDto(donation.getFamily())
				: null);
		donationTracking.setUsed(donation.isUsed());
		donationTracking.setUsedAmount(donation.getUsedAmount());
		donationTracking.setRemainingAmount(donation.getAmount() - donation.getUsedAmount());
		donationTracking.setDonationUsages(donation.getUsages().size() != 0
				? DonationMapper.INSTANCE.toGetDonationUsageResponseDtoList(donation.getUsages())
				: null);
		return donationTracking;
	}

	@Override
	public Page<GetDonationMonthlyResponseDto> getMonthlyDonations(PageRequest pageRequest, int year, int month) {
		Page<Donation> donations = donationRepository.getDonationByMonthInYear(pageRequest, year, month);
		return donations.map(DonationMapper.INSTANCE::toGetDonationMonthlyResponseDto);
	}

	private void sendDonationConfirmationEmail(Donation donation) {
		String subject = "Xác Nhận Nhận Được Quyên Góp của Bạn";
		String message = "Kính gửi quý ông/bà " + donation.getDonor().getDonorFullName() + ", \n\n"
				+ "Chúng tôi xin trân trọng cảm ơn bạn quý ông/bà, đã quyên góp cho trại trẻ mồ côi của chúng"
				+ " tôi. Sự hỗ trợ của bạn có ý nghĩa lớn và sẽ góp phần giúp đỡ những trẻ em có hoàn cảnh khó khăn. \n\n"
				+ "Thông tin về Quyên Góp:\n" + "Số Tiền: " + formatToVND(donation.getAmount()) + ". \n"
				+ "Ngày quyên Góp: " + formatTimestamp(donation.getDonationTime()) + ". \n" + "Mã quyên Góp: "
				+ donation.getDonationHash() + ". Hãy sử dụng mã này để xem thông tin quyên"
				+ " góp và kiểm tra quá trình chúng tôi sử khoản quyên góp của bạn. \n"
				+ "Hãy sử dụng id: " + donation.getDonor().getDonorToken()
				+ " để xem thông tin các khoản quyên góp mà bạn đã thực hiện và dùng nó "
				+ " cho những lần quyên góp tiếp theo của bạn. \n\n"
				+ "Chúng tôi cam kết sử dụng mọi đồng tiền quyên góp của bạn một cách minh bạch và hiệu quả nhất để "
				+ "đảm bảo rằng nó sẽ đến đúng nơi và tạo ra sự thay đổi tích cực.\n Hãy theo dõi trang web của chúng "
				+ "tôi hoặc liên hệ trực tiếp để cập nhật về cách chúng tôi sử dụng quyên góp của bạn và những tiến "
				+ "triển mới nhất.\n\n"
				+ "Một lần nữa, chúng tôi chân thành cảm ơn sự hỗ trợ của bạn và hy vọng nhận được sự tiếp tục ủng hộ"
				+ " trong tương lai.\n\n" + "Mọi thắc mắc và góp ý, xin vui lòng liên hệ với chúng tôi qua:\n"
				+ "Email: support.orphanage@gmail.com\n" + "Hotline: 0123456789\n\n" + "Trân trọng,\n" + "Orphanage";

		emailService.sendEmailWithSimpleMessage(donation.getDonor().getDonorMailAddress(), subject, message);
		donation.setSentEmail(true);
		donationRepository.saveAndFlush(donation);
	}

	private String generatePaymentUrl(double amount, Integer donationId, String urlReturn, String message,
			String ipAddress) {
		String vnp_TxnRef = String.valueOf(donationId);
		// Convert amount from double to VNPAY format
		double amountDb = amount * 100;
		NumberFormat f = new DecimalFormat("0");
		String amountFm = f.format(amountDb);

		// generate vnp_createDate
		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String vnp_CreateDate = formatter.format(cld.getTime());

		// Generate payment expiration date
		cld.add(Calendar.MINUTE, 15);
		String vnp_ExpireDate = formatter.format(cld.getTime());

		// Map to store the parameters to be sent
		Map<String, String> vnp_Params = new HashMap<>();
		vnp_Params.put("vnp_Version", vnpayConfig.getVnpayVersion());
		vnp_Params.put("vnp_Command", "pay");
		vnp_Params.put("vnp_TmnCode", vnpayConfig.getTmnCode());
		vnp_Params.put("vnp_Amount", amountFm);
		vnp_Params.put("vnp_CurrCode", "VND");
		vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
		vnp_Params.put("vnp_OrderInfo", message);
		vnp_Params.put("vnp_OrderType", "other");
		vnp_Params.put("vnp_Locale", "vn");
		vnp_Params.put("vnp_ReturnUrl", urlReturn);
		vnp_Params.put("vnp_IpAddr", ipAddress);
		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
		vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

		// Sort the parameter names in dictionary order
		List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
		Collections.sort(fieldNames);

		// Build hashData and query strings
		StringBuilder hashData = new StringBuilder();
		StringBuilder query = new StringBuilder();
		Iterator<String> itr = fieldNames.iterator();

		while (itr.hasNext()) {
			String fieldName = itr.next();
			String fieldValue = vnp_Params.get(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				// Build hash data
				hashData.append(fieldName);
				hashData.append('=');
				try {
					hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
					// Build query
					query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
					query.append('=');
					query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (itr.hasNext()) {
					query.append('&');
					hashData.append('&');
				}
			}
		}

		// Add vnp_SecureHash to the query
		String queryUrl = query.toString();
		String vnp_SecureHash = vnpayConfig.hmacSHA512(vnpayConfig.getHashSecret(), hashData.toString());
		queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;

		return vnpayConfig.getVnpayUrl() + "?" + queryUrl;
	}

	private String generateTokenFromEmail(String email) {
		try {
			String uniqueString = email + System.currentTimeMillis();
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(uniqueString.getBytes(StandardCharsets.UTF_8));

			String token = Base64.getEncoder().encodeToString(hash);
			token = token.replace('+', '-').replace('/', '_');

			return token;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String formatToVND(double amount) {
		DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###.## VND");
		return decimalFormat.format(amount);
	}

	private static String formatTimestamp(Timestamp timestamp) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date(timestamp.getTime());
		return simpleDateFormat.format(date);
	}

//    @Override
//    public void mockDonations() {
//        Donor donor =
//                donorRepository.findByDonorIdAndIsDeleteIsFalse(20)
//                        .orElseThrow(() -> new ResourceNotFoundException("Donor not found!"));
//        Random random = new Random();
//        for (int i = 1; i < 15; i++) {
//
//            int randomPurpose = random.nextInt(12) + 1;
//            Optional<DonationPurpose> purpose =
//                    donationPurposeRepository.findByDonationPurposeIdAndIsDeleteIsFalse(randomPurpose);
//            Family family = null;
//            if (purpose.get().getDonationPurposeId() == 2) {
//                int randomFamily = random.nextInt(4) + 3;
//                family = familyRepository.findFamilyByFamilyIdAndIsDeleteIsFalse(randomFamily).get();
//            }
//            Donation donation =
//                    Donation.builder()
//                            .donor(donor)
//                            .amount(100000 * i)
//                            .donationMessage("cho em")
//                            .donationTime(new Timestamp(System.currentTimeMillis()))
//                            .purpose(purpose.get())
//                            .family(family)
//                            .paymentStatus(DonationCode.PaymentStatus.PAID.getCode())
//                            .bankCode("bc")
//                            .bankTranNo("btn")
//                            .payDate(new Timestamp(System.currentTimeMillis()))
//                            .cardType("ct")
//                            .transactionStatus(DonationCode.ResponseCode.SUCCESS.getCode())
//                            .transactionNo("tn")
//                            .build();
//            Donation donationSaved = donationRepository.saveAndFlush(donation);
//
//            AddNewDonationContractRequestDto newDonationContractRequestDto =
//                    AddNewDonationContractRequestDto.builder()
//                            .donationId(donationSaved.getDonationId())
//                            .donorName(donationSaved.getDonor().getDonorFullName())
//                            .donorToken(donationSaved.getDonor().getDonorToken())
//                            .amount(donationSaved.getAmount())
//                            .purposeId(donationSaved.getPurpose().getDonationPurposeId())
//                            .purpose(donationSaved.getPurpose().getPurpose())
//                            .familyId(donationSaved.getFamily() == null ? 0 : donation.getFamily().getFamilyId())
//                            .donationTime(donationSaved.getDonationTime().getTime())
//                            .build();
//
//            AddNewDonationContractResponseDto donationContractResponse =
//                    blockChainService.addDonation(newDonationContractRequestDto);
//
//            donationSaved.setBlockchainHash(donationContractResponse.getBlockHash());
//            donationSaved.setDonationHash(donationContractResponse.getDonationHash());
//            donationSaved.setTransactionHash(donationContractResponse.getTransactionHash());
//            donationSaved.setInBlockchain(true);
//
//            donationRepository.saveAndFlush(donationSaved);
//        }
//    }
}
