package com.graduatebackend.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.graduatebackend.constant.ChildrenCode;
import com.graduatebackend.constant.DonationCode;
import com.graduatebackend.dto.content.GetStatisticsContentResponseDto;
import com.graduatebackend.dto.statistics.GetChildrenStatisticsByAgeResponseDto;
import com.graduatebackend.dto.statistics.GetChildrenStatisticsByFamilyResponseDto;
import com.graduatebackend.dto.statistics.GetChildrenStatisticsByGenderResponseDto;
import com.graduatebackend.dto.statistics.GetChildrenStatisticsByStatusResponseDto;
import com.graduatebackend.dto.statistics.GetDonationStatisticsByFamilyResponseDto;
import com.graduatebackend.dto.statistics.GetDonationStatisticsByMonthResponseDto;
import com.graduatebackend.dto.statistics.GetDonationStatisticsByPurposeResponseDto;
import com.graduatebackend.dto.statistics.GetTopDonorResponseDto;
import com.graduatebackend.dto.statistics.SummaryStatisticsResponseDto;
import com.graduatebackend.entity.ChildrenStatus;
import com.graduatebackend.entity.Donation;
import com.graduatebackend.entity.DonationPurpose;
import com.graduatebackend.entity.Donor;
import com.graduatebackend.entity.Family;
import com.graduatebackend.mapper.DonationMapper;
import com.graduatebackend.repository.ChildrenRepository;
import com.graduatebackend.repository.ChildrenStatusRepository;
import com.graduatebackend.repository.DonationPurposeRepository;
import com.graduatebackend.repository.DonationRepository;
import com.graduatebackend.repository.DonationUsageRepository;
import com.graduatebackend.repository.DonorRepository;
import com.graduatebackend.repository.EmployeeRepository;
import com.graduatebackend.repository.FamilyRepository;
import com.graduatebackend.repository.FundingUsageRepository;
import com.graduatebackend.service.StatisticsService;
import com.graduatebackend.utils.ConvertUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
	private final ChildrenRepository childrenRepository;
	private final EmployeeRepository employeeRepository;
	private final DonationRepository donationRepository;
	private final DonorRepository donorRepository;
	private final DonationUsageRepository donationUsageRepository;
	private final DonationPurposeRepository donationPurposeRepository;
	private final ChildrenStatusRepository childrenStatusRepository;
	private final FamilyRepository familyRepository;
	private final FundingUsageRepository fundingUsageRepository;

	@Override
	public SummaryStatisticsResponseDto getSummaryStatistics() {
		return SummaryStatisticsResponseDto.builder().totalChildren(childrenRepository.count())
				.totalEmployees(employeeRepository.count()).totalDonations(donationRepository.count())
				.totalDonationAmounts(donationRepository.getDonationAmount()).build();
	}

	@Override
	public GetChildrenStatisticsByGenderResponseDto getChildrenStatisticsByGender() {
		return GetChildrenStatisticsByGenderResponseDto.builder()
				.male(childrenRepository.count(ChildrenCode.Gender.MALE.getCode()))
				.female(childrenRepository.count(ChildrenCode.Gender.FEMALE.getCode())).build();
	}

	@Override
	public List<GetChildrenStatisticsByAgeResponseDto> getChildrenStatisticsByAge() {
		List<GetChildrenStatisticsByAgeResponseDto> responseDtoList = new ArrayList<>();
		responseDtoList.add(new GetChildrenStatisticsByAgeResponseDto("0 ~ 1",
				childrenRepository.count(ConvertUtils.generateFromAge(0), ConvertUtils.generateFromAge(1))));
		responseDtoList.add(new GetChildrenStatisticsByAgeResponseDto("1 ~ 2",
				childrenRepository.count(ConvertUtils.generateFromAge(1), ConvertUtils.generateFromAge(3))));
		responseDtoList.add(new GetChildrenStatisticsByAgeResponseDto("3 ~ 5",
				childrenRepository.count(ConvertUtils.generateFromAge(3), ConvertUtils.generateFromAge(6))));
		responseDtoList.add(new GetChildrenStatisticsByAgeResponseDto("6 ~ 9",
				childrenRepository.count(ConvertUtils.generateFromAge(6), ConvertUtils.generateFromAge(10))));
		responseDtoList.add(new GetChildrenStatisticsByAgeResponseDto("10 ~ 14",
				childrenRepository.count(ConvertUtils.generateFromAge(10), ConvertUtils.generateFromAge(15))));
		return responseDtoList;
	}

	@Override
	public List<GetChildrenStatisticsByStatusResponseDto> getChildrenStatisticsByStatus() {
		List<ChildrenStatus> childrenStatusList = childrenStatusRepository.findByIsDeleteIsFalse();
		List<GetChildrenStatisticsByStatusResponseDto> responseDtoList = new ArrayList<>();

		childrenStatusList.forEach(childrenStatus -> {
			long childrenCount = childrenRepository.count(childrenStatus);
			if (childrenCount > 0)
				responseDtoList.add(new GetChildrenStatisticsByStatusResponseDto(childrenStatus.getChildrenStatusName(),
						childrenCount));
		});
		return responseDtoList;
	}

	@Override
	public List<GetChildrenStatisticsByFamilyResponseDto> getChildrenStatisticsByFamily() {
		List<Family> families = familyRepository.findByIsDeleteIsFalse();
		List<GetChildrenStatisticsByFamilyResponseDto> responseDtoList = new ArrayList<>();

		families.forEach(family -> {
			long childrenCount = childrenRepository.count(family);
			if (childrenCount > 0)
				responseDtoList
						.add(new GetChildrenStatisticsByFamilyResponseDto(family.getFamilyName(), childrenCount));
		});
		return responseDtoList;
	}

	@Override
	public List<GetDonationStatisticsByMonthResponseDto> getDonationStatisticsByMonth() {
		int year = LocalDate.now().getYear();
		List<GetDonationStatisticsByMonthResponseDto> responseDtoList = new ArrayList<>();
		for (int month = 1; month <= 12; month++) {
			List<Donation> donations = donationRepository.getDonationByMonth(year, month);
			GetDonationStatisticsByMonthResponseDto responseDto = new GetDonationStatisticsByMonthResponseDto();
			responseDto.setMonth(month);
			responseDto.setDonationCount(donations.size());
			responseDto.setDonationAmount(donations.stream().mapToDouble(Donation::getAmount).sum());
			responseDto.setUsageAmount(
					donationUsageRepository.getUsageAmountByMonthInYear(year, month).orElse(Long.valueOf(0)));
			Set<Integer> uniqueDonors = new HashSet<>();
			for (Donation donation : donations) {
				Integer donorId = donation.getDonor().getDonorId();
				uniqueDonors.add(donorId);
			}
			responseDto.setDonorCount(uniqueDonors.size());
			responseDtoList.add(responseDto);
		}
		return responseDtoList;
	}

	@Override
	public List<GetDonationStatisticsByPurposeResponseDto> getDonationStatisticsByPurpose(Integer year) {
		List<DonationPurpose> donationPurposes = donationPurposeRepository.findByIsDeleteIsFalse();
		List<GetDonationStatisticsByPurposeResponseDto> responseDtoList = new ArrayList<>();
		for (DonationPurpose purpose : donationPurposes) {
			GetDonationStatisticsByPurposeResponseDto responseDto = GetDonationStatisticsByPurposeResponseDto.builder()
					.purpose(purpose.getPurpose()).donationCount(donationRepository.count(purpose, year))
					.donationAmount(donationRepository.getDonationAmount(purpose, year).orElse(Double.valueOf(0)))
					.build();
			responseDtoList.add(responseDto);
		}
		return responseDtoList;
	}

	@Override
	public List<GetDonationStatisticsByFamilyResponseDto> getDonationStatisticsByFamily(Integer year) {
		List<Family> families = familyRepository.findByIsDeleteIsFalse();
		List<GetDonationStatisticsByFamilyResponseDto> responseDtoList = new ArrayList<>();

		for (Family family : families) {
			GetDonationStatisticsByFamilyResponseDto responseDto = GetDonationStatisticsByFamilyResponseDto.builder()
					.family(family.getFamilyName()).donationCount(donationRepository.count(family, year))
					.donationAmount(donationRepository.getDonationAmount(family, year).orElse((double) 0))
					.usageAmount(fundingUsageRepository.getUsageAmount(family, year).orElse((double) 0)).build();
			responseDtoList.add(responseDto);
		}
		return responseDtoList;
	}

	@Override
	public List<GetTopDonorResponseDto> getTopDonor() {
		List<Donor> donorList = donorRepository.findByTopDonation(PageRequest.of(0, 10));
		List<GetTopDonorResponseDto> responseDtoList = donorList.stream().map(donor -> {
			GetTopDonorResponseDto responseDto = DonationMapper.INSTANCE.toGetTopDonorResponseDto(donor);
			long donationCount = 0;
			double totalAmount = 0;
			for (Donation donation : donor.getDonations()) {
				if (donation.getPaymentStatus() == DonationCode.PaymentStatus.PAID.getCode()
						&& donation.isInBlockchain() && !donation.isDelete()) {
					donationCount++;
					totalAmount += donation.getAmount();
				}
			}
			responseDto.setDonationCount(donationCount);
			responseDto.setTotalAmount(totalAmount);
			return responseDto;
		}).collect(Collectors.toList());
		return responseDtoList;
	}

	@Override
	public GetStatisticsContentResponseDto getStatisticsContent() {

		long inCareCount = childrenRepository.count(
				ChildrenStatus.builder().childrenStatusId(ChildrenCode.ChildrenStatus.IN_CARE.getCode()).build());
		long adoptedCount = childrenRepository.count(
				ChildrenStatus.builder().childrenStatusId(ChildrenCode.ChildrenStatus.ADOPTED.getCode()).build());
		return GetStatisticsContentResponseDto.builder().inCareChildren(inCareCount).adoptedChildren(adoptedCount)
				.build();
	}
}
