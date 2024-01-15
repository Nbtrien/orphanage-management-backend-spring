package com.graduatebackend.mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.ReportingPolicy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.graduatebackend.dto.adoption.AddNewAdoptionApplicationRequestDto;
import com.graduatebackend.dto.adoption.AddNewAdoptionHistoryRequestDto;
import com.graduatebackend.dto.adoption.GetAdoptionApplicationDetailResponseDto;
import com.graduatebackend.dto.adoption.GetAdoptionApplicationHistoryResponseDto;
import com.graduatebackend.dto.adoption.GetAdoptionApplicationResponseDto;
import com.graduatebackend.dto.adoption.GetAdoptionHistoryDetailResponseDto;
import com.graduatebackend.dto.adoption.GetAdoptionHistoryResponseDto;
import com.graduatebackend.entity.AdoptionApplication;
import com.graduatebackend.entity.AdoptionHistory;

@Mapper(unmappedTargetPolicy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface AdoptionApplicationMapper {
	AdoptionApplicationMapper INSTANCE = Mappers.getMapper(AdoptionApplicationMapper.class);

	/**
	 * Convert AddNewAdoptionApplicationRequestDto to AdoptionApplication entity
	 *
	 * @param requestDto
	 * @return
	 */
	@Mapping(target = "applicant", expression = "java(ApplicantMapper.INSTANCE.toEntity(requestDto.getApplicant()))")
	@Mapping(target = "spouse", ignore = true)
	AdoptionApplication toEntity(AddNewAdoptionApplicationRequestDto requestDto);

	/**
	 * Convert AddNewAdoptionHistoryRequestDto to AdoptionHistory Entity
	 *
	 * @param requestDto
	 * @return
	 */
	@Mapping(target = "applicant", expression = "java(ApplicantMapper.INSTANCE.toEntity(requestDto.getApplicant()))")
	@Mapping(target = "spouse", ignore = true)
	AdoptionHistory toAdoptionHistoryEntity(AddNewAdoptionHistoryRequestDto requestDto);

	/**
	 * Convert AdoptionHistory Entity to GetAdoptionHistoryResponseDto
	 *
	 * @param adoptionHistory
	 * @return
	 */
	@Mapping(target = "applicantId", source = "applicant.applicantId")
	@Mapping(target = "applicantFullName", source = "applicant.applicantFullName")
	@Mapping(target = "spouseFullName", source = "spouse.spouseFullName")
	@Mapping(target = "childrenId", source = "children.childrenId")
	@Mapping(target = "childrenFullName", source = "children.childrenFullName")
	GetAdoptionHistoryResponseDto toGetAdoptionHistoryResponseDto(AdoptionHistory adoptionHistory);

	/**
	 * Convert AdoptionHistory Entity to GetAdoptionHistoryDetailResponseDto
	 *
	 * @param adoptionHistory
	 * @return
	 */
	@Mapping(target = "applicant", expression = "java(ApplicantMapper.INSTANCE.toGetApplicantDetailResponseDto"
			+ "(adoptionHistory.getApplicant()))")
	@Mapping(target = "spouse", expression = "java(ApplicantMapper.INSTANCE.toGetSpouseDetailResponseDto"
			+ "(adoptionHistory.getSpouse()))")
	@Mapping(target = "children", expression = "java(ChildrenMapper.INSTANCE.toGetChildrenResponseDto"
			+ "(adoptionHistory.getChildren()))")
	GetAdoptionHistoryDetailResponseDto toGetAdoptionHistoryDetailResponseDto(AdoptionHistory adoptionHistory);

	/**
	 * Convert AdoptionApplication entity to GetAdoptionApplicationResponseDto
	 *
	 * @param application
	 * @return
	 */
	@Mapping(target = "applicantId", source = "applicant.applicantId")
	@Mapping(target = "applicantFullName", source = "applicant.applicantFullName")
	@Mapping(target = "applicantMailAddress", source = "applicant.applicantMailAddress")
	GetAdoptionApplicationResponseDto toGetAdoptionApplicationResponseDto(AdoptionApplication application);

	/**
	 * Convert AdoptionApplication Entity to
	 * GetAdoptionApplicationHistoryResponseDto
	 *
	 * @param application
	 * @return
	 */
	GetAdoptionApplicationHistoryResponseDto toGetAdoptionApplicationHistoryResponseDto(
			AdoptionApplication application);

	/**
	 * Convert AdoptionApplication entity to GetAdoptionApplicationDetailResponseDto
	 *
	 * @param application
	 * @return
	 */
	@Mapping(target = "applicant", expression = "java(ApplicantMapper.INSTANCE.toGetApplicantDetailResponseDto"
			+ "(application.getApplicant()))")
	GetAdoptionApplicationDetailResponseDto toGetAdoptionApplicationDetailResponseDto(AdoptionApplication application);
}
