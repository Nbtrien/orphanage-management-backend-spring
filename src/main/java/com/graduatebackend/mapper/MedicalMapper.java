package com.graduatebackend.mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.ReportingPolicy.IGNORE;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.graduatebackend.dto.medical.AddNewMedicalRecordRequestDto;
import com.graduatebackend.dto.medical.AddNewVaccinationRecordRequestDto;
import com.graduatebackend.dto.medical.MedicalRecordResponseDto;
import com.graduatebackend.dto.medical.VaccinationRecordResponseDto;
import com.graduatebackend.entity.MedicalRecord;
import com.graduatebackend.entity.VaccinationRecord;

@Mapper(unmappedTargetPolicy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface MedicalMapper {
	MedicalMapper INSTANCE = Mappers.getMapper(MedicalMapper.class);

	/**
	 * Convert AddNewMedicalRecordRequestDto to MedicalRecord entity
	 *
	 * @param requestDto
	 * @return
	 */
	MedicalRecord toMedicalRecord(AddNewMedicalRecordRequestDto requestDto);

	/**
	 * Convert AddNewVaccinationRecordRequestDto to VaccinationRecord Entity
	 *
	 * @param requestDto
	 * @return
	 */
	VaccinationRecord toVaccinationRecord(AddNewVaccinationRecordRequestDto requestDto);

	/**
	 * Convert MedicalRecord entity to MedicalRecordResponseDto
	 *
	 * @param medicalRecord
	 * @return
	 */
	@Mapping(target = "documentName", source = "medicalDocumentFileName")
	@Mapping(target = "documentUrl", source = "medicalDocumentFilePath")
	@Named("toMedicalRecordResponseDto")
	MedicalRecordResponseDto toMedicalRecordResponseDto(MedicalRecord medicalRecord);

	/**
	 * Convert MedicalRecord entity list to MedicalRecordResponseDto list
	 *
	 * @param medicalRecords
	 * @return
	 */
	@IterableMapping(qualifiedByName = "toMedicalRecordResponseDto")
	List<MedicalRecordResponseDto> toMedicalRecordResponseDtoList(List<MedicalRecord> medicalRecords);

	/**
	 * Convert VaccinationRecord entity to VaccinationRecordResponseDto
	 *
	 * @param vaccinationRecord
	 * @return
	 */
	@Mapping(target = "documentName", source = "vaccinationDocumentFileName")
	@Mapping(target = "documentUrl", source = "vaccinationDocumentFilePath")
	@Named("toVaccinationRecordResponseDto")
	VaccinationRecordResponseDto toVaccinationRecordResponseDto(VaccinationRecord vaccinationRecord);

	/**
	 * Convert VaccinationRecord entity list to VaccinationRecordResponseDto list
	 *
	 * @param vaccinationRecords
	 * @return
	 */
	@IterableMapping(qualifiedByName = "toVaccinationRecordResponseDto")
	List<VaccinationRecordResponseDto> toVaccinationRecordResponseDtoList(List<VaccinationRecord> vaccinationRecords);
}
