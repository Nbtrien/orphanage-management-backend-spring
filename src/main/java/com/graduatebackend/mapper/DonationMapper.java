package com.graduatebackend.mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.ReportingPolicy.IGNORE;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.graduatebackend.blockchain.GetDonationContractResponseDto;
import com.graduatebackend.dto.donation.AddNewDonationPurposeRequestDto;
import com.graduatebackend.dto.donation.AddNewDonorRequestDto;
import com.graduatebackend.dto.donation.GetAccountDonorResponseDto;
import com.graduatebackend.dto.donation.GetDonationDetailResponseDto;
import com.graduatebackend.dto.donation.GetDonationMonthlyResponseDto;
import com.graduatebackend.dto.donation.GetDonationPurposeResponseDto;
import com.graduatebackend.dto.donation.GetDonationResponseDto;
import com.graduatebackend.dto.donation.GetDonationTrackingResponseDto;
import com.graduatebackend.dto.donation.GetDonationUsageResponseDto;
import com.graduatebackend.dto.donation.GetDonorDetailResponseDto;
import com.graduatebackend.dto.donation.GetDonorListResponseDto;
import com.graduatebackend.dto.donation.GetDonorResponseDto;
import com.graduatebackend.dto.donation.GetFundingUsageDetailResponseDto;
import com.graduatebackend.dto.donation.GetFundingUsageResponseDto;
import com.graduatebackend.dto.statistics.GetTopDonorResponseDto;
import com.graduatebackend.entity.Applicant;
import com.graduatebackend.entity.Donation;
import com.graduatebackend.entity.DonationPurpose;
import com.graduatebackend.entity.DonationUsage;
import com.graduatebackend.entity.Donor;
import com.graduatebackend.entity.FundingUsage;

@Mapper(unmappedTargetPolicy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface DonationMapper {
	DonationMapper INSTANCE = Mappers.getMapper(DonationMapper.class);

	/**
	 * Convert AddNewDonorRequestDto to Donor Entity
	 *
	 * @param requestDto AddNewDonorRequestDto
	 * @return Donor
	 */
	@Mapping(target = "address", expression = "java(AddressMapper.INSTANCE.toEntity(requestDto.getAddress()))")
	Donor toDonorEntity(AddNewDonorRequestDto requestDto);

	/**
	 * Convert Donor Entity to GetDonorResponseDto
	 *
	 * @param donor Donor
	 * @return GetDonorResponseDto
	 */
	@Mapping(target = "address", expression = "java(AddressMapper.INSTANCE.toAddNewAddressDto(donor.getAddress()))")
	GetDonorResponseDto toGetDonorResponseDto(Donor donor);

	/**
	 * Convert Donor Entity to GetAccountDonorResponseDto
	 *
	 * @param donor
	 * @return
	 */
	@Mapping(target = "address", expression = "java(AddressMapper.INSTANCE.toAddNewAddressDto(donor.getAddress()))")
	GetAccountDonorResponseDto toGetAccountDonorResponseDto(Donor donor);

	/**
	 * Convert Applicant Entity to GetAccountDonorResponseDto
	 *
	 * @param applicant
	 * @return
	 */
	@Mapping(target = "address", expression = "java(AddressMapper.INSTANCE.toAddNewAddressDto(applicant.getAddress()))")
	@Mapping(target = "donorGender", source = "applicant.applicantGender")
	@Mapping(target = "donorDateOfBirth", source = "applicant.applicantDateOfBirth")
	@Mapping(target = "donorFirstName", source = "applicant.applicantFirstName")
	@Mapping(target = "donorFullName", source = "applicant.applicantFullName")
	@Mapping(target = "donorLastName", source = "applicant.applicantLastName")
	@Mapping(target = "donorMailAddress", source = "applicant.applicantMailAddress")
	@Mapping(target = "donorPhoneNumber", source = "applicant.applicantPhoneNumber")
	GetAccountDonorResponseDto toGetAccountDonorResponseDto(Applicant applicant);

	/**
	 * Convert Donor Entity to GetDonorListResponseDto
	 *
	 * @param donor Donor
	 * @return GetDonorListResponseDto
	 */
	@Mapping(target = "address", expression = "java(AddressMapper.INSTANCE.toAddressString(donor.getAddress()))")
	GetDonorListResponseDto toGetDonorListResponseDto(Donor donor);

	/**
	 * Convert Donor Entity to GetDonorDetailResponseDto
	 *
	 * @param donor Donor
	 * @return GetDonorDetailResponseDto
	 */
	@Mapping(target = "address", expression = "java(AddressMapper.INSTANCE.toAddressString(donor.getAddress()))")
	@Mapping(target = "donations", expression = "java(toGetDonationResponseDtoList(donor.getDonations()))")
	GetDonorDetailResponseDto toGetDonorDetailResponseDto(Donor donor);

	/**
	 * Convert from DonationPurpose entity to GetDonationPurposeResponseDto
	 *
	 * @param donationPurpose DonationPurpose
	 * @return GetDonationPurposeResponseDto
	 */
	GetDonationPurposeResponseDto toGetDonationPurposeResponseDto(DonationPurpose donationPurpose);

	/**
	 * Convert from Donation entity to GetDonationResponseDto
	 *
	 * @param donation Donation
	 * @return GetDonationResponseDto
	 */
	@Mapping(target = "purpose", expression = "java(toGetDonationPurposeResponseDto(donation.getPurpose()))")
	@Mapping(target = "family", expression = "java(FamilyMapper.INSTANCE.toGetFamilyForDonateResponseDto(donation"
			+ ".getFamily()))")
	@Mapping(target = "donorId", source = "donor.donorId")
	@Mapping(target = "donorName", source = "donor.donorFullName")
	@Named("GetDonationResponseDto")
	GetDonationResponseDto toGetDonationResponseDto(Donation donation);

	/**
	 * Convert Donation Entity list to GetDonationResponseDto list
	 *
	 * @param donations List<Donation>
	 * @return List<GetDonationResponseDto>
	 */
	@IterableMapping(qualifiedByName = "GetDonationResponseDto")
	List<GetDonationResponseDto> toGetDonationResponseDtoList(List<Donation> donations);

	/**
	 * Convert Donation Entity toGetDonationDetailResponseDto
	 *
	 * @param donation Donation
	 * @return GetDonationDetailResponseDto
	 */
	@Mapping(target = "family", expression = "java(FamilyMapper.INSTANCE.toGetFamilyForDonateResponseDto(donation"
			+ ".getFamily()))")
	@Mapping(target = "purpose", expression = "java(toGetDonationPurposeResponseDto(donation.getPurpose()))")
	@Mapping(target = "donor", expression = "java(toGetDonorListResponseDto(donation.getDonor()))")
	@Mapping(target = "donationUsages", expression = "java(toGetDonationUsageResponseDtoList(donation.getUsages()))")
	GetDonationDetailResponseDto toGetDonationDetailResponseDto(Donation donation);

	/**
	 * Convert FundingUsage entity to GetFundingUsageResponseDto
	 *
	 * @param fundingUsage FundingUsage
	 * @return GetFundingUsageResponseDto
	 */
	@Mapping(target = "purpose", expression = "java(toGetDonationPurposeResponseDto(fundingUsage.getPurpose()))")
	@Mapping(target = "family", expression = "java(FamilyMapper.INSTANCE.toGetFamilyForDonateResponseDto(fundingUsage"
			+ ".getFamily()))")
	@Named("toGetFundingUsageResponseDto")
	GetFundingUsageResponseDto toGetFundingUsageResponseDto(FundingUsage fundingUsage);

	/**
	 * Convert FundingUsage Entity to GetFundingUsageDetailResponseDto
	 *
	 * @param fundingUsage FundingUsage
	 * @return GetFundingUsageDetailResponseDto
	 */
	@Mapping(target = "purpose", expression = "java(toGetDonationPurposeResponseDto(fundingUsage.getPurpose()))")
	@Mapping(target = "family", expression = "java(FamilyMapper.INSTANCE.toGetFamilyForDonateResponseDto(fundingUsage"
			+ ".getFamily()))")
	@Mapping(target = "donationUsages", expression = "java(toGetDonationUsageResponseDtoList(fundingUsage"
			+ ".getDonationUsages()))")
	@Named("toGetFundingUsageDetailResponseDto")
	GetFundingUsageDetailResponseDto toGetFundingUsageDetailResponseDto(FundingUsage fundingUsage);

	/**
	 * Convert FundingUsage entity list to GetFundingUsageDetailResponseDto list
	 *
	 * @param fundingUsages List<FundingUsage>
	 * @return List<GetFundingUsageDetailResponseDto>
	 */
	@IterableMapping(qualifiedByName = "toGetFundingUsageResponseDto")
	List<GetFundingUsageResponseDto> toGetFundingUsageResponseDtoList(List<FundingUsage> fundingUsages);

	/**
	 * Convert DonationUsage Entity to GetDonationUsageResponseDto
	 *
	 * @param donationUsage DonationUsage
	 * @return GetDonationUsageResponseDto
	 */
	@Mapping(target = "family", expression = "java(FamilyMapper.INSTANCE.toGetFamilyForDonateResponseDto"
			+ "(donationUsage.getFundingUsage().getFamily()))")
	@Named("toGetDonationUsageResponseDto")
	@Mapping(target = "usageNote", source = "fundingUsage.usageNote")
	@Mapping(target = "donationId", source = "donation.donationId")
	@Mapping(target = "donationAmount", source = "donation.amount")
	@Mapping(target = "donationTime", source = "donation.donationTime")
	@Mapping(target = "donorId", source = "donation.donor.donorId")
	@Mapping(target = "donorFullName", source = "donation.donor.donorFullName")
	GetDonationUsageResponseDto toGetDonationUsageResponseDto(DonationUsage donationUsage);

	/**
	 * Convert From DonationUsage list to GetDonationUsageResponseDto list
	 *
	 * @param donationUsages List<DonationUsage>
	 * @return List<GetDonationUsageResponseDto>
	 */
	@IterableMapping(qualifiedByName = "toGetDonationUsageResponseDto")
	List<GetDonationUsageResponseDto> toGetDonationUsageResponseDtoList(List<DonationUsage> donationUsages);

	/**
	 * Convert GetDonationContractResponseDto to GetDonationTrackingResponseDto
	 *
	 * @param donationContract
	 * @return
	 */
	GetDonationTrackingResponseDto toGetDonationTrackingResponseDto(GetDonationContractResponseDto donationContract);

	/**
	 * Convert Donation to GetDonationMonthlyResponseDto
	 *
	 * @param donation
	 * @return
	 */
	@Mapping(target = "donorName", source = "donation.donor.donorFullName")
	@Mapping(target = "donationPurpose", source = "donation.purpose.purpose")
	GetDonationMonthlyResponseDto toGetDonationMonthlyResponseDto(Donation donation);

	/**
	 * Convert AddNewDonationPurposeRequestDto to DonationPurpose Entity
	 *
	 * @param requestDto
	 * @return
	 */
	DonationPurpose toDonationPurposeEntity(AddNewDonationPurposeRequestDto requestDto);

	/**
	 * Convert Donor entity to GetTopDonorResponseDto
	 *
	 * @param donor
	 * @return
	 */
	GetTopDonorResponseDto toGetTopDonorResponseDto(Donor donor);
}
