package com.graduatebackend.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.graduatebackend.constant.Role;
import com.graduatebackend.dto.ResponseDto;
import com.graduatebackend.dto.medical.AddNewMedicalRecordRequestDto;
import com.graduatebackend.dto.medical.AddNewVaccinationRecordRequestDto;
import com.graduatebackend.service.MedicalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class MedicalController {
	private final MedicalService medicalService;

	@GetMapping("/children/{id}/medical-records")
	@Secured({ Role.Code.SUPER_ADMIN })
	public ResponseEntity<?> getMedicalRecordsByChildren(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(medicalService.getMedicalRecordsByChildren(id)));
	}

	@GetMapping("/children/{id}/vaccination-records")
	@Secured({ Role.Code.SUPER_ADMIN })
	public ResponseEntity<?> getVaccinationRecordsByChildren(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(medicalService.getVaccinationRecordsByChildren(id)));
	}

	@PostMapping("/children/{id}/medical-records")
	@Secured({ Role.Code.SUPER_ADMIN })
	public ResponseEntity<?> addNewMedicalRecord(@PathVariable("id") Integer childrenId,
			@Valid @RequestBody AddNewMedicalRecordRequestDto requestDto) {
		medicalService.addNewMedicalRecord(childrenId, requestDto);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/children/{id}/vaccination-records")
	@Secured({ Role.Code.SUPER_ADMIN })
	public ResponseEntity<?> addNewVaccinationRecord(@PathVariable("id") Integer childrenId,
			@Valid @RequestBody AddNewVaccinationRecordRequestDto requestDto) {
		medicalService.addNewVaccinationRecord(childrenId, requestDto);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/medical-records/{id}")
	public ResponseEntity<?> deleteMedicalRecord(@PathVariable("id") Integer id) {
		medicalService.deleteMedicalRecord(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/vaccinations-records/{id}")
	public ResponseEntity<?> deleteVaccinationRecord(@PathVariable("id") Integer id) {
		medicalService.deleteVaccinationRecord(id);
		return ResponseEntity.noContent().build();
	}
}
