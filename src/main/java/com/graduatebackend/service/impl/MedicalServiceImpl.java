package com.graduatebackend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.graduatebackend.dto.medical.AddNewMedicalRecordRequestDto;
import com.graduatebackend.dto.medical.AddNewVaccinationRecordRequestDto;
import com.graduatebackend.dto.medical.MedicalRecordResponseDto;
import com.graduatebackend.dto.medical.VaccinationRecordResponseDto;
import com.graduatebackend.entity.Children;
import com.graduatebackend.entity.MedicalRecord;
import com.graduatebackend.entity.VaccinationRecord;
import com.graduatebackend.exception.ResourceNotFoundException;
import com.graduatebackend.mapper.MedicalMapper;
import com.graduatebackend.repository.ChildrenRepository;
import com.graduatebackend.repository.MedicalRecordRepository;
import com.graduatebackend.repository.VaccinationRecordRepository;
import com.graduatebackend.service.MedicalService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicalServiceImpl implements MedicalService {
	private final ChildrenRepository childrenRepository;
	private final MedicalRecordRepository medicalRecordRepository;
	private final VaccinationRecordRepository vaccinationRecordRepository;

	@Override
	public List<MedicalRecordResponseDto> getMedicalRecordsByChildren(Integer id) {
		Children children = childrenRepository.findByChildrenIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Children not found!"));
		List<MedicalRecord> medicalRecords = medicalRecordRepository.getByChildren(children);
		return MedicalMapper.INSTANCE.toMedicalRecordResponseDtoList(medicalRecords);
	}

	@Override
	public List<VaccinationRecordResponseDto> getVaccinationRecordsByChildren(Integer id) {
		Children children = childrenRepository.findByChildrenIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Children not found!"));
		List<VaccinationRecord> vaccinationRecords = vaccinationRecordRepository.getByChildren(children);
		return MedicalMapper.INSTANCE.toVaccinationRecordResponseDtoList(vaccinationRecords);
	}

	@Override
	public void addNewMedicalRecord(Integer childrenId, AddNewMedicalRecordRequestDto requestDto) {
		Children children = childrenRepository.findByChildrenIdAndIsDeleteIsFalse(childrenId)
				.orElseThrow(() -> new ResourceNotFoundException("Children not found!"));

		MedicalRecord medicalRecord = MedicalMapper.INSTANCE.toMedicalRecord(requestDto);
		medicalRecord.setChildren(children);
		medicalRecordRepository.save(medicalRecord);
	}

	@Override
	public void addNewVaccinationRecord(Integer childrenId, AddNewVaccinationRecordRequestDto requestDto) {
		Children children = childrenRepository.findByChildrenIdAndIsDeleteIsFalse(childrenId)
				.orElseThrow(() -> new ResourceNotFoundException("Children not found!"));

		VaccinationRecord vaccinationRecord = MedicalMapper.INSTANCE.toVaccinationRecord(requestDto);
		vaccinationRecord.setChildren(children);
		vaccinationRecordRepository.save(vaccinationRecord);
	}

	@Override
	@Transactional
	public void deleteMedicalRecord(Integer id) {
		MedicalRecord medicalRecord = medicalRecordRepository.findByMedicalRecordIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Medical record not found"));
		medicalRecord.setDelete(true);
		medicalRecordRepository.save(medicalRecord);
	}

	@Override
	@Transactional
	public void deleteVaccinationRecord(Integer id) {
		VaccinationRecord vaccinationRecord = vaccinationRecordRepository
				.findByVaccinationRecordIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Vaccination record not found"));
		vaccinationRecord.setDelete(true);
		vaccinationRecordRepository.save(vaccinationRecord);
	}
}
