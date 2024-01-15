package com.graduatebackend.mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.ReportingPolicy.IGNORE;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.graduatebackend.dto.articles.AddNewFamilyPostRequestDto;
import com.graduatebackend.dto.articles.GetFamilyPostResponseDto;
import com.graduatebackend.dto.articles.GetFamilyPostUpdateResponseDto;
import com.graduatebackend.dto.family.AddNewFamilyConditionRequestDto;
import com.graduatebackend.dto.family.AddNewFamilyRequestDto;
import com.graduatebackend.dto.family.GetFamilyConditionResponseDto;
import com.graduatebackend.dto.family.GetFamilyDonationDetailResponseDto;
import com.graduatebackend.dto.family.GetFamilyForDonateResponseDto;
import com.graduatebackend.dto.family.GetFamilyResponseDto;
import com.graduatebackend.dto.family.GetMotherResponseDto;
import com.graduatebackend.entity.Family;
import com.graduatebackend.entity.FamilyCondition;
import com.graduatebackend.entity.FamilyPost;
import com.graduatebackend.entity.Mother;

@Mapper(unmappedTargetPolicy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface FamilyMapper {
	FamilyMapper INSTANCE = Mappers.getMapper(FamilyMapper.class);

	/**
	 * Convert FamilyCondition entity to GetFamilyConditionResponseDto
	 *
	 * @param familyCondition
	 * @return
	 */
	@Named("toGetFamilyConditionResponseDto")
	GetFamilyConditionResponseDto toGetFamilyConditionResponseDto(FamilyCondition familyCondition);

	/**
	 * Convert FamilyCondition entity list to GetFamilyConditionResponseDto list
	 *
	 * @param familyConditions
	 * @return
	 */
	List<GetFamilyConditionResponseDto> toGetFamilyConditionResponseDtoList(List<FamilyCondition> familyConditions);

	/**
	 * Convert AddNewFamilyConditionRequestDto to FamilyCondition entity
	 *
	 * @param requestDto
	 * @return
	 */
	FamilyCondition toFamilyConditionEntity(AddNewFamilyConditionRequestDto requestDto);

	/**
	 * Convert AddNewFamilyRequestDto to Family entity
	 *
	 * @param requestDto
	 * @return
	 */
	@Mapping(target = "condition.familyConditionId", source = "familyConditionId")
	Family toFamilyEntity(AddNewFamilyRequestDto requestDto);

	/**
	 * Convert Family entity to GetFamilyResponseDto
	 *
	 * @param family
	 * @return
	 */
	GetFamilyResponseDto toGetFamilyResponseDto(Family family);

	/**
	 * Convert Family entity to GetFamilyForDonateResponseDto
	 *
	 * @param family
	 * @return
	 */
	GetFamilyForDonateResponseDto toGetFamilyForDonateResponseDto(Family family);

	/**
	 * Convert Family entity to GetFamilyDonationDetailResponseDto
	 *
	 * @param family Family
	 * @return GetFamilyDonationDetailResponseDto
	 */
	@Mapping(target = "donations", expression = "java(DonationMapper.INSTANCE.toGetDonationResponseDtoList"
			+ "(family.getDonations()))")
	@Mapping(target = "fundingUsages", expression = "java(DonationMapper.INSTANCE"
			+ ".toGetFundingUsageResponseDtoList(family.getFundingUsages()))")
	GetFamilyDonationDetailResponseDto toGetFamilyDonationDetailResponseDto(Family family);

	/**
	 * Convert Mother entity to GetMotherResponseDto
	 *
	 * @param mother
	 * @return
	 */
	@Mapping(target = "employeeId", source = "employee.employeeId")
	@Mapping(target = "dateOfBirth", source = "employee.employeeDateOfBirth")
	@Mapping(target = "phoneNumber", source = "employee.employeePhoneNumber")
	@Mapping(target = "mailAddress", source = "employee.employeeMailAddress")
	@Mapping(target = "ethnicity", source = "employee.employeeEthnicity")
	@Mapping(target = "religion", source = "employee.employeeReligion")
	@Mapping(target = "hireDate", source = "employee.hireDate")
	@Mapping(target = "address", expression = "java(AddressMapper.INSTANCE.toAddressString(mother.getEmployee()"
			+ ".getAddress()))")
	GetMotherResponseDto toGetMotherResponseDto(Mother mother);

	/**
	 * Convert Mother entity list to GetMotherResponseDto list
	 *
	 * @param mothers
	 * @return
	 */
	List<GetMotherResponseDto> toGetMotherResponseDtoList(List<Mother> mothers);

	/**
	 * Convert AddNewFamilyPostRequestDto to FamilyPost entity
	 *
	 * @param requestDto
	 * @return
	 */
	FamilyPost toFamilyPostEntity(AddNewFamilyPostRequestDto requestDto);

	/**
	 * Convert Family entity to GetFamilyPostResponseDto
	 *
	 * @param family
	 * @return
	 */
	GetFamilyPostResponseDto toGetFamilyPostResponseDto(Family family);

	/**
	 * Convert FamilyPost Entity to GetFamilyPostUpdateResponseDto
	 *
	 * @param familyPost
	 * @return
	 */
	@Mapping(target = "familyId", source = "family.familyId")
	@Mapping(target = "familyName", source = "family.familyName")
	GetFamilyPostUpdateResponseDto toGetFamilyPostUpdateResponseDto(FamilyPost familyPost);

	/**
	 * update family post entity
	 *
	 * @param familyPost
	 * @param requestDto
	 */
	@Mapping(target = "active", ignore = true)
	@Mapping(target = "images", ignore = true)
	void updateFamilyPostEntity(@MappingTarget FamilyPost familyPost, AddNewFamilyPostRequestDto requestDto);
}
