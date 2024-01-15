package com.graduatebackend.service.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.graduatebackend.constant.ChildrenCode;
import com.graduatebackend.dto.children.AddNewChildrenRequestDto;
import com.graduatebackend.dto.children.DocumentDto;
import com.graduatebackend.dto.children.DocumentTypeDto;
import com.graduatebackend.dto.children.GetChildrenDetailResponseDto;
import com.graduatebackend.dto.children.GetChildrenForAdoptionHistoryResponseDto;
import com.graduatebackend.dto.children.GetChildrenRecordResponseDto;
import com.graduatebackend.dto.children.GetChildrenResponseDto;
import com.graduatebackend.dto.children.GetChildrenStatusResponseDto;
import com.graduatebackend.dto.children.GetChildrenUpdateInfoResponseDto;
import com.graduatebackend.dto.children.GetChildrenUpdateStatusResponseDto;
import com.graduatebackend.dto.children.GetRelativeResponseDto;
import com.graduatebackend.dto.children.UpdateChildrenInfoRequestDto;
import com.graduatebackend.dto.children.UpdateChildrenStatusRequestDto;
import com.graduatebackend.dto.medical.GetChildMedicalRecordResponseDto;
import com.graduatebackend.entity.Children;
import com.graduatebackend.entity.ChildrenDocument;
import com.graduatebackend.entity.ChildrenFamilyHistory;
import com.graduatebackend.entity.ChildrenStatus;
import com.graduatebackend.entity.ChildrenStatusHistory;
import com.graduatebackend.entity.District;
import com.graduatebackend.entity.DocumentType;
import com.graduatebackend.entity.Family;
import com.graduatebackend.entity.MedicalRecord;
import com.graduatebackend.entity.OrphanType;
import com.graduatebackend.entity.Province;
import com.graduatebackend.entity.StatusTransactionOption;
import com.graduatebackend.entity.VaccinationRecord;
import com.graduatebackend.entity.Ward;
import com.graduatebackend.exception.ResourceNotFoundException;
import com.graduatebackend.mapper.ChildrenMapper;
import com.graduatebackend.mapper.ChildrenStatusMapper;
import com.graduatebackend.mapper.DocumentTypeMapper;
import com.graduatebackend.mapper.MedicalMapper;
import com.graduatebackend.mapper.RelativeMapper;
import com.graduatebackend.repository.ChildrenDocumentRepository;
import com.graduatebackend.repository.ChildrenFamilyHistoryRepository;
import com.graduatebackend.repository.ChildrenRepository;
import com.graduatebackend.repository.ChildrenStatusRepository;
import com.graduatebackend.repository.DocumentTypeRepository;
import com.graduatebackend.repository.FamilyRepository;
import com.graduatebackend.repository.MedicalRecordRepository;
import com.graduatebackend.repository.StatusHistoryRepository;
import com.graduatebackend.repository.VaccinationRecordRepository;
import com.graduatebackend.service.ChildrenService;
import com.graduatebackend.service.FileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChildrenServiceImpl implements ChildrenService {
	private final ChildrenRepository childrenRepository;
	private final DocumentTypeRepository documentTypeRepository;
	private final ChildrenDocumentRepository documentRepository;
	private final FamilyRepository familyRepository;
	private final FileService fileService;
	private final ChildrenStatusRepository statusRepository;
	private final ChildrenFamilyHistoryRepository childrenFamilyHistoryRepository;
	private final MedicalRecordRepository medicalRecordRepository;
	private final VaccinationRecordRepository vaccinationRecordRepository;
	private final StatusHistoryRepository statusHistoryRepository;

	@Override
	public GetChildrenDetailResponseDto getDetail(Integer id) {
		Children children = childrenRepository.findByChildrenIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Children not found!"));
		GetChildrenDetailResponseDto childrenDetailResponseDto = ChildrenMapper.INSTANCE
				.toGetChildrenDetailResponseDto(children);
		childrenDetailResponseDto.setChangeStatus(children.getChildrenStatus().getTransactionOptions().size() != 0);
		// Create s3 image url from image file path
		childrenDetailResponseDto.setImageUrl(fileService.generateFileUrl(children.getImage().getImageFilePath()));
		childrenDetailResponseDto.setChildrenGender(ChildrenCode.Gender.of(children.getChildrenGender()).getDisplay());

		// Set relatives response
		List<GetRelativeResponseDto> relativeResponseDtos = children.getRelativeRelationships().stream()
				.filter(r -> !r.getRelative().isDelete()).map(r -> {
					GetRelativeResponseDto relativeDto = RelativeMapper.INSTANCE
							.toGetRelativeResponseDto(r.getRelative());
					relativeDto.setRelationship(r.getRelationship());
					return relativeDto;
				}).collect(Collectors.toList());
		childrenDetailResponseDto.setRelatives(relativeResponseDtos);

		// Set documents response
		List<DocumentDto> documents = children.getDocuments().stream().filter(d -> !d.isDelete()).map(d -> {
			// Create s3 file url from image file path
			String fileUrl = fileService.generateFileUrl(d.getDocumentFilePath());
			return DocumentDto.builder().documentFileName(d.getDocumentType().getDocumentTypeName())
					.documentFilePath(fileUrl).documentTypeId(d.getDocumentType().getDocumentTypeId()).build();
		}).collect(Collectors.toList());
		childrenDetailResponseDto.setDocuments(documents);
		return childrenDetailResponseDto;
	}

	@Override
	public GetChildMedicalRecordResponseDto getMedicalRecord(Integer id) {
		Children children = childrenRepository.findByChildrenIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Children not found!"));

		List<MedicalRecord> medicalRecords = medicalRecordRepository.getByChildren(children);
		List<VaccinationRecord> vaccinationRecords = vaccinationRecordRepository.getByChildren(children);
		GetChildMedicalRecordResponseDto responseDto = GetChildMedicalRecordResponseDto.builder()
				.childrenId(children.getChildrenId()).childrenFullName(children.getChildrenFullName())
				.medicalRecords(MedicalMapper.INSTANCE.toMedicalRecordResponseDtoList(medicalRecords))
				.vaccinationRecords(MedicalMapper.INSTANCE.toVaccinationRecordResponseDtoList(vaccinationRecords))
				.build();

		responseDto.getMedicalRecords().forEach(medicalRecord -> medicalRecord
				.setDocumentUrl(fileService.generateFileUrl(medicalRecord.getDocumentUrl())));
		responseDto.getVaccinationRecords().forEach(vaccinationRecord -> vaccinationRecord
				.setDocumentUrl(fileService.generateFileUrl(vaccinationRecord.getDocumentUrl())));

		return responseDto;
	}

	@Override
	public GetChildrenUpdateInfoResponseDto getUpdateInfo(Integer id) {
		Children children = childrenRepository.findByChildrenIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Children not found!"));
		return ChildrenMapper.INSTANCE.toGetChildrenUpdateInfoResponseDto(children);
	}

	@Override
	public Page<GetChildrenResponseDto> fetchAll(PageRequest pageRequest) {
		Page<Children> page = childrenRepository.findByIsDeleteIsFalse(pageRequest);

		return page.map(children -> {
			GetChildrenResponseDto responseDto = ChildrenMapper.INSTANCE.toGetChildrenResponseDto(children);
			responseDto.setChildrenGender(ChildrenCode.Gender.of(children.getChildrenGender()).getDisplay());
			// Create s3 image url from image file path
			responseDto.setImageUrl(fileService.generateFileUrl(children.getImage().getImageFilePath()));
			return responseDto;
		});
	}

	@Override
	public Page<GetChildrenStatusResponseDto> fetchAllChildrenStatus(PageRequest pageRequest) {
		Page<Children> page = childrenRepository.findByIsDeleteIsFalse(pageRequest);
		return page.map(children -> {
			GetChildrenStatusResponseDto responseDto = ChildrenMapper.INSTANCE.toGetChildrenStatusResponseDto(children);
			responseDto.setChildrenGender(ChildrenCode.Gender.of(children.getChildrenGender()).getDisplay());
			responseDto.setChangeStatus(children.getChildrenStatus().getTransactionOptions().size() != 0);
			// Create s3 image url from image file path
			responseDto.setImageUrl(fileService.generateFileUrl(children.getImage().getImageFilePath()));
			return responseDto;
		});
	}

	@Override
	public Page<GetChildrenRecordResponseDto> fetchAllChildrenRecord(PageRequest pageRequest, String keyword) {
		Page<Children> page = childrenRepository.search(pageRequest, keyword, null, null, null, null, null, null);
		Page<GetChildrenRecordResponseDto> responseDtoPage = page.map(children -> {
			GetChildrenRecordResponseDto responseDto = ChildrenMapper.INSTANCE.toGetChildrenRecordResponseDto(children);
			responseDto.setChildrenGender(ChildrenCode.Gender.of(children.getChildrenGender()).getDisplay());
			// Create s3 image url from image file path
			responseDto.setImageUrl(fileService.generateFileUrl(children.getImage().getImageFilePath()));

			// Set documents response
			List<DocumentDto> documents = children.getDocuments().stream().filter(d -> !d.isDelete()).map(d -> {
				// Create s3 file url from image file path
				String fileUrl = fileService.generateFileUrl(d.getDocumentFilePath());
				return DocumentDto.builder().documentFileName(d.getDocumentType().getDocumentTypeName())
						.documentFilePath(fileUrl).documentTypeId(d.getDocumentType().getDocumentTypeId()).build();
			}).collect(Collectors.toList());
			responseDto.setDocuments(documents);

			responseDto.getMedicalRecords().forEach(medicalRecord -> medicalRecord
					.setDocumentUrl(fileService.generateFileUrl(medicalRecord.getDocumentUrl())));
			responseDto.getVaccinationRecords().forEach(vaccinationRecord -> vaccinationRecord
					.setDocumentUrl(fileService.generateFileUrl(vaccinationRecord.getDocumentUrl())));

			return responseDto;
		});
		return responseDtoPage;
	}

	@Override
	public Page<GetChildrenResponseDto> searchByKeyword(PageRequest pageRequest, String keyword) {
		Page<Children> page = childrenRepository.searchByKeyword(pageRequest, keyword);

		return page.map(children -> {
			GetChildrenResponseDto responseDto = ChildrenMapper.INSTANCE.toGetChildrenResponseDto(children);
			responseDto.setChildrenGender(ChildrenCode.Gender.of(children.getChildrenGender()).getDisplay());
			// Create s3 image url from image file path
			responseDto.setImageUrl(fileService.generateFileUrl(children.getImage().getImageFilePath()));
			return responseDto;
		});
	}

	@Override
	@Transactional
	public void addNew(AddNewChildrenRequestDto requestDto) {
		Children children = ChildrenMapper.INSTANCE.toEntity(requestDto);
		children.setChildrenFullName(children.getChildrenLastName() + " " + children.getChildrenFirstName());

		// Set children status is in care
		children.setChildrenStatus(ChildrenStatus.builder().childrenStatusId(1).build());
		children.setOrphanType(OrphanType.builder().orphanTypeId(requestDto.getOrphanageTypeId()).build());

		children.getDocuments().forEach(document -> document.setChildren(children));
		children.setWaitingAdoption(requestDto.getIsWaitingAdoption() == 1);
		Children childrenSaved = childrenRepository.save(children);

		ChildrenStatusHistory statusHistory = ChildrenStatusHistory.builder().children(childrenSaved)
				.childrenStatus(ChildrenStatus.builder().childrenStatusId(ChildrenCode.ChildrenStatus.IN_CARE.getCode())
						.build())
				.startDate(childrenSaved.getDateOfAdmission())
				.note("Trẻ được nhận vào và được chăm sóc tại trại trẻ.").build();
		statusHistoryRepository.save(statusHistory);
	}

	@Override
	public void delete(List<Integer> ids) {
		childrenRepository.softDeleteByIds(ids);
	}

	@Override
	public List<DocumentTypeDto> getDocumentTypes() {
		List<DocumentType> documentTypes = documentTypeRepository.findAll();
		return DocumentTypeMapper.INSTANCE.toDtoList(documentTypes);
	}

	@Override
	public List<GetChildrenResponseDto> searchChildren(String name, Integer id, Integer minAge, Integer maxAge) {
		LocalDate currentDate = LocalDate.now();
		Date minBirthDateAsSqlDate = null;
		Date maxBirthDateAsSqlDate = null;
		if (maxAge != null) {
			LocalDate minBirthDate = currentDate.minusYears(maxAge);
			minBirthDateAsSqlDate = Date.valueOf(minBirthDate);
		}
		if (minAge != null) {
			LocalDate maxBirthDate = currentDate.minusYears(minAge);
			maxBirthDateAsSqlDate = Date.valueOf(maxBirthDate);
		}

		List<Children> childrenList = childrenRepository.searchChildren(name, id, minBirthDateAsSqlDate,
				maxBirthDateAsSqlDate);

		return childrenList.stream().map(children -> {
			GetChildrenResponseDto responseDto = ChildrenMapper.INSTANCE.toGetChildrenResponseDto(children);
			responseDto.setChildrenGender(ChildrenCode.Gender.of(children.getChildrenGender()).getDisplay());
			// Create s3 image url from image file path
			responseDto.setImageUrl(fileService.generateFileUrl(children.getImage().getImageFilePath()));
			return responseDto;
		}).collect(Collectors.toList());
	}

	@Override
	public Page<GetChildrenResponseDto> search(PageRequest pageRequest, String keyword, String orphanType,
			String minAge, String maxAge, String status, String minDateOfAdmission, String maxDateOfAdmission) {

		Page<Children> page = searchFromRepo(pageRequest, keyword, orphanType, minAge, maxAge, status,
				minDateOfAdmission, maxDateOfAdmission);

		return page.map(children -> {
			GetChildrenResponseDto responseDto = ChildrenMapper.INSTANCE.toGetChildrenResponseDto(children);
			responseDto.setChildrenGender(ChildrenCode.Gender.of(children.getChildrenGender()).getDisplay());
			// Create s3 image url from image file path
			responseDto.setImageUrl(fileService.generateFileUrl(children.getImage().getImageFilePath()));
			return responseDto;
		});
	}

	@Override
	public Page<GetChildrenStatusResponseDto> searchChildrenStatus(PageRequest pageRequest, String keyword,
			String orphanType, String minAge, String maxAge, String status, String minDateOfAdmission,
			String maxDateOfAdmission) {
		Page<Children> page = searchFromRepo(pageRequest, keyword, orphanType, minAge, maxAge, status,
				minDateOfAdmission, maxDateOfAdmission);

		return page.map(children -> {
			GetChildrenStatusResponseDto responseDto = ChildrenMapper.INSTANCE.toGetChildrenStatusResponseDto(children);
			responseDto.setChangeStatus(children.getChildrenStatus().getTransactionOptions().size() != 0);
			responseDto.setChildrenGender(ChildrenCode.Gender.of(children.getChildrenGender()).getDisplay());
			// Create s3 image url from image file path
			responseDto.setImageUrl(fileService.generateFileUrl(children.getImage().getImageFilePath()));
			return responseDto;
		});
	}

	@Override
	public List<GetChildrenResponseDto> fetchByFamilyId(Integer familyId) {
		Family family = familyRepository.findFamilyByFamilyIdAndIsDeleteIsFalse(familyId)
				.orElseThrow(() -> new ResourceNotFoundException("Family not found."));

		List<Children> childrenList = childrenRepository.findByFamilyAndIsDeleteIsFalse(family);

		return childrenList.stream().map(children -> {
			GetChildrenResponseDto responseDto = ChildrenMapper.INSTANCE.toGetChildrenResponseDto(children);
			responseDto.setChildrenGender(ChildrenCode.Gender.of(children.getChildrenGender()).getDisplay());
			// Create s3 image url from image file path
			responseDto.setImageUrl(fileService.generateFileUrl(children.getImage().getImageFilePath()));
			return responseDto;
		}).collect(Collectors.toList());
	}

	@Override
	public List<GetChildrenForAdoptionHistoryResponseDto> getChildrenForAdoptionHistory() {
		List<Children> childrenList = childrenRepository.findAllInCareChildren();
		return childrenList.stream().map(ChildrenMapper.INSTANCE::toGetChildrenForAdoptionHistoryResponseDto)
				.collect(Collectors.toList());
	}

	@Override
	public GetChildrenUpdateStatusResponseDto getUpdateStatusOption(Integer id) {
		Children children = childrenRepository.findByChildrenIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Children not found"));
		List<ChildrenStatus> statusOptions = children.getChildrenStatus().getTransactionOptions().stream()
				.map(StatusTransactionOption::getToStatus).collect(Collectors.toList());
		return GetChildrenUpdateStatusResponseDto.builder().childrenId(children.getChildrenId())
				.childrenName(children.getChildrenFullName())
				.statusId(children.getChildrenStatus().getChildrenStatusId())
				.statusName(children.getChildrenStatus().getChildrenStatusName())
				.statusOptions(ChildrenStatusMapper.INSTANCE.toDtoList(statusOptions)).build();
	}

	@Override
	public Object updateInfo(Integer id, UpdateChildrenInfoRequestDto requestDto) {
		Children children = childrenRepository.findByChildrenIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Children not found"));
		ChildrenMapper.INSTANCE.updateChildrenEntity(children, requestDto);
		children.setChildrenFullName(children.getChildrenLastName() + " " + children.getChildrenFirstName());
		children.getAddress().setWard(Ward.builder().wardId(requestDto.getAddress().getWardId()).build());
		children.getAddress()
				.setProvince(Province.builder().provinceId(requestDto.getAddress().getProvinceId()).build());
		children.getAddress()
				.setDistrict(District.builder().districtId(requestDto.getAddress().getDistrictId()).build());
		children.getAddress().setAddressDetail(requestDto.getAddress().getAddressDetail());

		if (requestDto.getImage() != null) {
			children.getImage().setImageFileName(requestDto.getImage().getImageFileName());
			children.getImage().setImageFilePath(requestDto.getImage().getImageFilePath());
		}

		if (requestDto.getOrphanTypeId() != children.getOrphanType().getOrphanTypeId()) {
			OrphanType orphanType = OrphanType.builder().orphanTypeId(requestDto.getOrphanTypeId()).build();
			children.setOrphanType(orphanType);
		}

		childrenRepository.save(children);
		return children.toString();
	}

	@Override
	@Transactional
	public void setFamilyForChildren(Integer childrenId, Integer familyId) {
		Children children = childrenRepository.findByChildrenIdAndIsDeleteIsFalse(childrenId)
				.orElseThrow(() -> new ResourceNotFoundException("Children not found."));

		Family family = familyRepository.findFamilyByFamilyIdAndIsDeleteIsFalse(familyId)
				.orElseThrow(() -> new ResourceNotFoundException("Family not found."));

		// set end date for family history if family is exists
		if (children.getFamily() != null) {
			ChildrenFamilyHistory oldHistory = childrenFamilyHistoryRepository
					.findNewestByChildrenAndFamily(children, children.getFamily())
					.orElseThrow(ResourceNotFoundException::new);
			oldHistory.setEndDate(new Date(System.currentTimeMillis()));
			childrenFamilyHistoryRepository.save(oldHistory);
		}

		children.setFamily(family);
		childrenRepository.save(children);

		// set new family history
		ChildrenFamilyHistory childrenFamilyHistory = ChildrenFamilyHistory.builder().children(children).family(family)
				.startDate(new Date(System.currentTimeMillis())).build();
		childrenFamilyHistoryRepository.save(childrenFamilyHistory);
	}

	@Override
	public void uploadDocuments(Integer id, DocumentDto requestDto) {
		Children children = childrenRepository.findByChildrenIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Children not found!"));

		ChildrenDocument document = ChildrenMapper.INSTANCE.toChildrenDocument(requestDto);

		document.setChildren(children);
		documentRepository.save(document);
	}

	@Override
	@Transactional
	public void updateDocuments(Integer id, DocumentDto requestDto) {
		Children children = childrenRepository.findByChildrenIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Children not found!"));
		ChildrenDocument document = ChildrenMapper.INSTANCE.toChildrenDocument(requestDto);
		children.getDocuments().stream().filter(d -> Objects.equals(d.getDocumentType().getDocumentTypeId(),
				document.getDocumentType().getDocumentTypeId()) && !d.isDelete()).forEach(document1 -> {
					document1.setDelete(true);
					documentRepository.save(document1);
				});

		document.setChildren(children);
		documentRepository.save(document);
	}

	@Override
	@Transactional
	public void updateChildrenStatus(Integer id, UpdateChildrenStatusRequestDto requestDto) {
		Children children = childrenRepository.findByChildrenIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Children not found!"));
		ChildrenStatus status = statusRepository
				.findChildrenStatusByChildrenStatusIdAndIsDeleteIsFalse(requestDto.getStatusId())
				.orElseThrow(() -> new ResourceNotFoundException("Children status not found!"));

		// set end date for current status
		ChildrenStatusHistory oldStatusHistory = statusHistoryRepository
				.findNewestByChildrenAndStatus(children, children.getChildrenStatus()).orElseThrow(null);
		oldStatusHistory.setEndDate(requestDto.getEndDate());
		statusHistoryRepository.save(oldStatusHistory);

		// check if children not in care
		if (children.getChildrenStatus().getChildrenStatusId() == ChildrenCode.ChildrenStatus.IN_CARE.getCode()
				&& status.getChildrenStatusId() != ChildrenCode.ChildrenStatus.IN_CARE.getCode()) {
			if (children.getFamily() != null) {
				ChildrenFamilyHistory oldHistory = childrenFamilyHistoryRepository
						.findNewestByChildrenAndFamily(children, children.getFamily())
						.orElseThrow(() -> new ResourceNotFoundException("family not found"));
				oldHistory.setEndDate(new Date(System.currentTimeMillis()));
				childrenFamilyHistoryRepository.save(oldHistory);
				children.setFamily(null);
			}
		}
		// change to new status
		children.setChildrenStatus(status);
		childrenRepository.save(children);

		ChildrenStatusHistory newStatusHistory = ChildrenStatusHistory.builder().children(children)
				.childrenStatus(status).startDate(requestDto.getStartDate()).note(requestDto.getNote()).build();
		statusHistoryRepository.save(newStatusHistory);
	}

	@Override
	public void updateAdoptionStatus(Integer id, boolean adoptionStatus) {
		Children children = childrenRepository.findByChildrenIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Children not found!"));
		children.setWaitingAdoption(adoptionStatus);
		childrenRepository.save(children);
	}

	private Page<Children> searchFromRepo(PageRequest pageRequest, String keyword, String orphanType, String minAge,
			String maxAge, String status, String minDateOfAdmission, String maxDateOfAdmission) {
		Date minBirthDateAsSqlDate = maxAge == null ? null : generateFromAge(Integer.valueOf(maxAge));
		Date maxBirthDateAsSqlDate = minAge == null ? null : generateFromAge(Integer.valueOf(minAge));
		Integer orphanTypeId = orphanType == null ? null : Integer.valueOf(orphanType);

		Date minDOA = minDateOfAdmission == null ? null : Date.valueOf(minDateOfAdmission);
		Date maxDOA = maxDateOfAdmission == null ? null : Date.valueOf(maxDateOfAdmission);
		Integer statusId = status == null ? null : Integer.valueOf(status);

		return childrenRepository.search(pageRequest, keyword, orphanTypeId, minBirthDateAsSqlDate,
				maxBirthDateAsSqlDate, statusId, minDOA, maxDOA);
	}

	private Date generateFromAge(Integer age) {
		LocalDate currentDate = LocalDate.now();
		Date birthDateAsSqlDate = null;
		LocalDate minBirthDate = currentDate.minusYears(age);
		birthDateAsSqlDate = Date.valueOf(minBirthDate);

		return birthDateAsSqlDate;
	}
}
