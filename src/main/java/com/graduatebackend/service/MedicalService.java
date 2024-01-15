package com.graduatebackend.service;

import java.util.List;

import com.graduatebackend.dto.medical.AddNewMedicalRecordRequestDto;
import com.graduatebackend.dto.medical.AddNewVaccinationRecordRequestDto;
import com.graduatebackend.dto.medical.MedicalRecordResponseDto;
import com.graduatebackend.dto.medical.VaccinationRecordResponseDto;

public interface MedicalService {
	/**
	 * get medical records by children
	 *
	 * @param id
	 * @return
	 */
	List<MedicalRecordResponseDto> getMedicalRecordsByChildren(Integer id);

	/**
	 * get vaccination records by children
	 *
	 * @param id
	 * @return
	 */
	List<VaccinationRecordResponseDto> getVaccinationRecordsByChildren(Integer id);

	/**
	 * Add new medical record
	 *
	 * @param childrenId
	 * @param requestDto
	 */
	void addNewMedicalRecord(Integer childrenId, AddNewMedicalRecordRequestDto requestDto);

	/**
	 * Add new vaccination record
	 *
	 * @param childrenId
	 * @param requestDto
	 */
	void addNewVaccinationRecord(Integer childrenId, AddNewVaccinationRecordRequestDto requestDto);

	/**
	 * delete medical record
	 *
	 * @param id
	 */
	void deleteMedicalRecord(Integer id);

	/**
	 * delete vaccination record
	 *
	 * @param id
	 */
	void deleteVaccinationRecord(Integer id);
}
