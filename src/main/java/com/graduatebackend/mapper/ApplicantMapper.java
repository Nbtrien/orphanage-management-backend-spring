package com.graduatebackend.mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.ReportingPolicy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.graduatebackend.dto.account.UpdateAccountRequestDto;
import com.graduatebackend.dto.adoption.AddNewApplicantRequestDto;
import com.graduatebackend.dto.adoption.AddNewApplicantSpouseRequestDto;
import com.graduatebackend.dto.adoption.GetAccountFistAdoptionResponseDto;
import com.graduatebackend.dto.adoption.GetAccountSpouseResponseDto;
import com.graduatebackend.dto.adoption.GetApplicantDetailResponseDto;
import com.graduatebackend.dto.adoption.GetSpouseDetailResponseDto;
import com.graduatebackend.entity.Applicant;
import com.graduatebackend.entity.ApplicantSpouse;

@Mapper(unmappedTargetPolicy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface ApplicantMapper {
	ApplicantMapper INSTANCE = Mappers.getMapper(ApplicantMapper.class);

	/**
	 * Convert AddNewApplicantRequestDto to Applicant entity
	 *
	 * @param requestDto
	 * @return
	 */
	@Mapping(target = "address", expression = "java(AddressMapper.INSTANCE.toEntity(requestDto.getAddress()))")
	@Mapping(target = "maritalStatus.maritalStatusId", source = "maritalStatusId")
	Applicant toEntity(AddNewApplicantRequestDto requestDto);

	/**
	 * Convert AddNewApplicantSpouseRequestDto to ApplicantSpouse Entity
	 *
	 * @param requestDto
	 * @return
	 */
	@Mapping(target = "address", expression = "java(AddressMapper.INSTANCE.toEntity(requestDto.getAddress()))")
	ApplicantSpouse toApplicantSpouseEntity(AddNewApplicantSpouseRequestDto requestDto);

	/**
	 * Update Applicant entity from UpdateAccountRequestDto
	 *
	 * @param requestDto
	 */
	@Mapping(target = "address", expression = "java(AddressMapper.INSTANCE.toEntity(requestDto.getAddress()))")
	void updateEntity(@MappingTarget Applicant applicant, UpdateAccountRequestDto requestDto);

	/**
	 * Convert Applicant entity to GetApplicantDetailResponseDto
	 *
	 * @param applicant
	 * @return GetApplicantDetailResponseDto
	 */
	@Mapping(target = "address", expression = "java(AddressMapper.INSTANCE.toAddressString(applicant.getAddress()))")
	@Mapping(target = "maritalStatus", ignore = true)
	@Mapping(target = "spouse", ignore = true)
	GetApplicantDetailResponseDto toGetApplicantDetailResponseDto(Applicant applicant);

	/**
	 * Convert ApplicantSpouse Entity to GetSpouseDetailResponseDto
	 *
	 * @param applicantSpouse
	 * @return
	 */
	@Mapping(target = "address", expression = "java(AddressMapper.INSTANCE.toAddressString(applicantSpouse"
			+ ".getAddress()))")
	GetSpouseDetailResponseDto toGetSpouseDetailResponseDto(ApplicantSpouse applicantSpouse);

	/**
	 * Convert Applicant Entity to GetAccountFistApplicationResponseDto
	 *
	 * @param applicant
	 * @return
	 */
	@Mapping(target = "address", expression = "java(AddressMapper.INSTANCE.toAddNewAddressDto(applicant.getAddress()))")
	@Mapping(target = "maritalStatusId", source = "maritalStatus.maritalStatusId")
	GetAccountFistAdoptionResponseDto toGetAccountFistAdoptionResponseDto(Applicant applicant);

	/**
	 * Convert ApplicantSpouse to GetAccountSpouseResponseDto
	 *
	 * @param applicantSpouse
	 * @return
	 */
	@Mapping(target = "address", expression = "java(AddressMapper.INSTANCE.toAddNewAddressDto(applicantSpouse"
			+ ".getAddress()))")
	GetAccountSpouseResponseDto toGetAccountSpouseResponseDto(ApplicantSpouse applicantSpouse);
}
