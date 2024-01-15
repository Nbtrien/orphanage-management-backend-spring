package com.graduatebackend.service.impl;

import java.sql.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.graduatebackend.constant.AdoptionCode;
import com.graduatebackend.constant.ChildrenCode;
import com.graduatebackend.dto.adoption.AddAdoptionHistoryFromApplicationRequestDto;
import com.graduatebackend.dto.adoption.AddNewAdoptionHistoryRequestDto;
import com.graduatebackend.dto.adoption.GetAdoptionApplicationResponseDto;
import com.graduatebackend.dto.adoption.GetAdoptionHistoryDetailResponseDto;
import com.graduatebackend.dto.adoption.GetAdoptionHistoryResponseDto;
import com.graduatebackend.dto.adoption.GetApplicantDetailResponseDto;
import com.graduatebackend.dto.adoption.GetSpouseDetailResponseDto;
import com.graduatebackend.entity.Address;
import com.graduatebackend.entity.AdoptionApplication;
import com.graduatebackend.entity.AdoptionHistory;
import com.graduatebackend.entity.Applicant;
import com.graduatebackend.entity.ApplicantSpouse;
import com.graduatebackend.entity.Children;
import com.graduatebackend.entity.ChildrenFamilyHistory;
import com.graduatebackend.entity.ChildrenStatus;
import com.graduatebackend.entity.ChildrenStatusHistory;
import com.graduatebackend.exception.ResourceNotFoundException;
import com.graduatebackend.mapper.AdoptionApplicationMapper;
import com.graduatebackend.mapper.ApplicantMapper;
import com.graduatebackend.repository.AdoptionApplicationRepository;
import com.graduatebackend.repository.AdoptionHistoryRepository;
import com.graduatebackend.repository.ChildrenFamilyHistoryRepository;
import com.graduatebackend.repository.ChildrenRepository;
import com.graduatebackend.repository.ChildrenStatusRepository;
import com.graduatebackend.repository.DistrictRepository;
import com.graduatebackend.repository.ProvinceRepository;
import com.graduatebackend.repository.StatusHistoryRepository;
import com.graduatebackend.repository.WardRepository;
import com.graduatebackend.service.AdoptionHistoryService;
import com.graduatebackend.service.FileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdoptionHistoryServiceImpl implements AdoptionHistoryService {
	private final AdoptionHistoryRepository adoptionHistoryRepository;
	private final ChildrenRepository childrenRepository;
	private final ProvinceRepository provinceRepository;
	private final DistrictRepository districtRepository;
	private final WardRepository wardRepository;
	private final ChildrenStatusRepository statusRepository;
	private final StatusHistoryRepository statusHistoryRepository;
	private final ChildrenFamilyHistoryRepository childrenFamilyHistoryRepository;
	private final FileService fileService;
	private final AdoptionApplicationRepository adoptionApplicationRepository;

	@Override
	@Transactional
	public void addNew(AddNewAdoptionHistoryRequestDto requestDto) {
		Children children = childrenRepository.findByChildrenIdAndIsDeleteIsFalse(requestDto.getChildrenId())
				.orElseThrow(() -> new ResourceNotFoundException("Children not found!"));
		AdoptionHistory adoptionHistory = AdoptionApplicationMapper.INSTANCE.toAdoptionHistoryEntity(requestDto);
		adoptionHistory.setChildren(children);
		// Set applicant and spouse full name
		Applicant applicant = adoptionHistory.getApplicant();
		applicant.setApplicantFullName(applicant.getApplicantLastName() + " " + applicant.getApplicantFirstName());
		Address address = Address.builder()
				.province(provinceRepository.getReferenceById(requestDto.getApplicant().getAddress().getProvinceId()))
				.district(districtRepository.getReferenceById(requestDto.getApplicant().getAddress().getDistrictId()))
				.ward(wardRepository.getReferenceById(requestDto.getApplicant().getAddress().getWardId()))
				.addressDetail(requestDto.getApplicant().getAddress().getAddressDetail()).build();

		applicant.setAddress(address);
		if (requestDto.getSpouse() != null) {
			ApplicantSpouse spouse = ApplicantMapper.INSTANCE.toApplicantSpouseEntity(requestDto.getSpouse());
			spouse.setSpouseFullName(spouse.getSpouseLastName() + " " + spouse.getSpouseFirstName());
			Address address1 = Address.builder()
					.province(provinceRepository.getReferenceById(requestDto.getSpouse().getAddress().getProvinceId()))
					.district(districtRepository.getReferenceById(requestDto.getSpouse().getAddress().getDistrictId()))
					.ward(wardRepository.getReferenceById(requestDto.getSpouse().getAddress().getWardId()))
					.addressDetail(requestDto.getSpouse().getAddress().getAddressDetail()).build();
			spouse.setAddress(address1);
			applicant.setSpouse(spouse);
		}
		adoptionHistory.setMaritalStatus(applicant.getMaritalStatus());
		AdoptionHistory adoptionHistorySaved = adoptionHistoryRepository.saveAndFlush(adoptionHistory);

		adoptionHistorySaved.setSpouse(adoptionHistorySaved.getApplicant().getSpouse());
		adoptionHistoryRepository.saveAndFlush(adoptionHistorySaved);
		// change to new children status
		ChildrenStatus status = statusRepository
				.findChildrenStatusByChildrenStatusIdAndIsDeleteIsFalse(ChildrenCode.ChildrenStatus.ADOPTED.getCode())
				.orElseThrow(() -> new ResourceNotFoundException("Children status not found!"));
		// set end date for current status
		ChildrenStatusHistory oldStatusHistory = statusHistoryRepository
				.findNewestByChildrenAndStatus(children, children.getChildrenStatus()).orElseThrow(null);
		oldStatusHistory.setEndDate(requestDto.getAdoptionDate());
		statusHistoryRepository.save(oldStatusHistory);

		if (children.getFamily() != null) {
			ChildrenFamilyHistory oldHistory = childrenFamilyHistoryRepository
					.findNewestByChildrenAndFamily(children, children.getFamily())
					.orElseThrow(() -> new ResourceNotFoundException("family not found"));
			oldHistory.setEndDate(new Date(System.currentTimeMillis()));
			childrenFamilyHistoryRepository.save(oldHistory);
			children.setFamily(null);
		}

		children.setChildrenStatus(status);
		childrenRepository.save(children);

		ChildrenStatusHistory newStatusHistory = ChildrenStatusHistory.builder().children(children)
				.childrenStatus(status).startDate(requestDto.getAdoptionDate())
				.note(requestDto.getAdoptionDescription()).build();
		statusHistoryRepository.save(newStatusHistory);
	}

	@Override
	@Transactional
	public void addNewFromApplication(Integer applicationId, AddAdoptionHistoryFromApplicationRequestDto requestDto) {
		AdoptionApplication application = adoptionApplicationRepository
				.findByAdoptionApplicationIdAndIsDeleteIsFalse(applicationId)
				.orElseThrow(() -> new ResourceNotFoundException("Application not found."));

		Children children = childrenRepository.findByChildrenIdAndIsDeleteIsFalse(requestDto.getChildrenId())
				.orElseThrow(() -> new ResourceNotFoundException("Children not found!"));
		AdoptionHistory adoptionHistory = AdoptionHistory.builder().children(children)
				.adoptionDate(requestDto.getAdoptionDate()).adoptionDescription(requestDto.getAdoptionDescription())
				.documentFilePath(requestDto.getDocumentFilePath()).maritalStatus(application.getMaritalStatus())
				.applicant(application.getApplicant()).spouse(application.getSpouse()).build();

		adoptionHistoryRepository.saveAndFlush(adoptionHistory);

		application.setApplicationStatus(AdoptionCode.ApplicationStatus.DONE.getCode());
		adoptionApplicationRepository.save(application);
		// change to new children status
		ChildrenStatus status = statusRepository
				.findChildrenStatusByChildrenStatusIdAndIsDeleteIsFalse(ChildrenCode.ChildrenStatus.ADOPTED.getCode())
				.orElseThrow(() -> new ResourceNotFoundException("Children status not found!"));
		// set end date for current status
		ChildrenStatusHistory oldStatusHistory = statusHistoryRepository
				.findNewestByChildrenAndStatus(children, children.getChildrenStatus()).orElseThrow(null);
		oldStatusHistory.setEndDate(requestDto.getAdoptionDate());
		statusHistoryRepository.save(oldStatusHistory);

		if (children.getFamily() != null) {
			ChildrenFamilyHistory oldHistory = childrenFamilyHistoryRepository
					.findNewestByChildrenAndFamily(children, children.getFamily())
					.orElseThrow(() -> new ResourceNotFoundException("family not found"));
			oldHistory.setEndDate(new Date(System.currentTimeMillis()));
			childrenFamilyHistoryRepository.save(oldHistory);
			children.setFamily(null);
		}

		children.setChildrenStatus(status);
		childrenRepository.save(children);

		ChildrenStatusHistory newStatusHistory = ChildrenStatusHistory.builder().children(children)
				.childrenStatus(status).startDate(requestDto.getAdoptionDate())
				.note(requestDto.getAdoptionDescription()).build();
		statusHistoryRepository.save(newStatusHistory);
	}

	@Override
	public Page<GetAdoptionHistoryResponseDto> fetch(PageRequest pageRequest, String keyword, String fromDate,
			String toDate) {
		Date minDate = fromDate == null ? null : Date.valueOf(fromDate);
		Date maxDate = toDate == null ? null : Date.valueOf(toDate);
		Page<AdoptionHistory> adoptionHistories = adoptionHistoryRepository.findAll(pageRequest, keyword, minDate,
				maxDate);
		Page<GetAdoptionHistoryResponseDto> responseDtoPage = adoptionHistories.map(adoptionHistory -> {
			GetAdoptionHistoryResponseDto responseDto = AdoptionApplicationMapper.INSTANCE
					.toGetAdoptionHistoryResponseDto(adoptionHistory);
			responseDto.setChildrenImageUrl(
					fileService.generateFileUrl(adoptionHistory.getChildren().getImage().getImageFilePath()));
			return responseDto;
		});
		return responseDtoPage;
	}

	@Override
	public GetAdoptionHistoryDetailResponseDto getDetail(Integer id) {
		AdoptionHistory adoptionHistory = adoptionHistoryRepository.findByAdoptionHistoryIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Adoption History not found."));
		GetAdoptionHistoryDetailResponseDto responseDto = AdoptionApplicationMapper.INSTANCE
				.toGetAdoptionHistoryDetailResponseDto(adoptionHistory);

		responseDto.getChildren().setChildrenGender(
				ChildrenCode.Gender.of(adoptionHistory.getChildren().getChildrenGender()).getDisplay());
		responseDto.getChildren()
				.setImageUrl(fileService.generateFileUrl(adoptionHistory.getChildren().getImage().getImageFilePath()));

		Applicant applicant = adoptionHistory.getApplicant();
		GetApplicantDetailResponseDto applicantResponseDto = responseDto.getApplicant();

		applicantResponseDto
				.setCitizenIdFrontImageUrl(fileService.generateFileUrl(applicant.getFrontImage().getImageFilePath()));
		applicantResponseDto
				.setCitizenIdBackImageUrl(fileService.generateFileUrl(applicant.getBackImage().getImageFilePath()));
		applicantResponseDto.setMaritalStatus(adoptionHistory.getMaritalStatus().getMaritalStatusName());
		applicantResponseDto.setApplicantGender(ChildrenCode.Gender.of(applicant.getApplicantGender()).getDisplay());

		if (adoptionHistory.getSpouse() != null) {
			GetSpouseDetailResponseDto spouse = ApplicantMapper.INSTANCE
					.toGetSpouseDetailResponseDto(adoptionHistory.getSpouse());
			spouse.setSpouseGender(ChildrenCode.Gender.of(adoptionHistory.getSpouse().getSpouseGender()).getDisplay());
			applicantResponseDto.setSpouse(spouse);
		}

		return responseDto;
	}

	@Override
	public GetAdoptionApplicationResponseDto getApplicationForHistory(Integer id) {
		AdoptionApplication application = adoptionApplicationRepository
				.findByAdoptionApplicationIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Application not found."));

		return AdoptionApplicationMapper.INSTANCE.toGetAdoptionApplicationResponseDto(application);
	}
}
