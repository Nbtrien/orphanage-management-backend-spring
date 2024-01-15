package com.graduatebackend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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
import com.graduatebackend.dto.children.UpdateChildrenInfoRequestDto;
import com.graduatebackend.dto.children.UpdateChildrenStatusRequestDto;
import com.graduatebackend.dto.medical.GetChildMedicalRecordResponseDto;

public interface ChildrenService {
	/**
	 * Save new children
	 *
	 * @param requestDto
	 * @return
	 */
	void addNew(AddNewChildrenRequestDto requestDto);

	/**
	 * get children detail
	 *
	 * @param id
	 * @return
	 */
	GetChildrenDetailResponseDto getDetail(Integer id);

	/**
	 * get medical & vaccination records for child
	 *
	 * @param id
	 * @return
	 */
	GetChildMedicalRecordResponseDto getMedicalRecord(Integer id);

	/**
	 * get children update information
	 *
	 * @param id
	 * @return
	 */
	GetChildrenUpdateInfoResponseDto getUpdateInfo(Integer id);

	/**
	 * get all children
	 *
	 * @param pageRequest
	 * @return
	 */
	Page<GetChildrenResponseDto> fetchAll(PageRequest pageRequest);

	/**
	 * get all children status
	 *
	 * @param pageRequest
	 * @return
	 */
	Page<GetChildrenStatusResponseDto> fetchAllChildrenStatus(PageRequest pageRequest);

	/**
	 * get all children record
	 *
	 * @param pageRequest
	 * @return
	 */
	Page<GetChildrenRecordResponseDto> fetchAllChildrenRecord(PageRequest pageRequest, String keyword);

	/**
	 * search children
	 *
	 * @param pageRequest
	 * @param keyword
	 * @return
	 */
	Page<GetChildrenResponseDto> searchByKeyword(PageRequest pageRequest, String keyword);

	/**
	 * Delete children
	 *
	 * @param ids
	 */
	void delete(List<Integer> ids);

	/**
	 * get all document types
	 *
	 * @return
	 */
	List<DocumentTypeDto> getDocumentTypes();

	/**
	 * search children by multiple params
	 *
	 * @param name
	 * @param id
	 * @param minAge
	 * @param maxAge
	 * @return
	 */
	List<GetChildrenResponseDto> searchChildren(String name, Integer id, Integer minAge, Integer maxAge);

	/**
	 * search children
	 *
	 * @param keyword
	 * @param orphanType
	 * @param minAge
	 * @param maxAge
	 * @return
	 */
	Page<GetChildrenResponseDto> search(PageRequest pageRequest, String keyword, String orphanType, String minAge,
			String maxAge, String status, String minDateOfAdmission, String maxDateOfAdmission);

	/**
	 * search children status
	 *
	 * @param pageRequest
	 * @param keyword
	 * @param status
	 * @param minDateOfAdmission
	 * @param maxDateOfAdmission
	 * @return
	 */
	Page<GetChildrenStatusResponseDto> searchChildrenStatus(PageRequest pageRequest, String keyword, String orphanType,
			String minAge, String maxAge, String status, String minDateOfAdmission, String maxDateOfAdmission);

	/**
	 * fetch by family id
	 *
	 * @param familyId
	 * @return
	 */
	List<GetChildrenResponseDto> fetchByFamilyId(Integer familyId);

	/**
	 * getChildrenForAdoptionHistory
	 *
	 * @return
	 */
	List<GetChildrenForAdoptionHistoryResponseDto> getChildrenForAdoptionHistory();

	/**
	 * get update status options
	 *
	 * @param id
	 * @return
	 */
	GetChildrenUpdateStatusResponseDto getUpdateStatusOption(Integer id);

	/**
	 * update info
	 *
	 * @param requestDto
	 * @return
	 */
	Object updateInfo(Integer id, UpdateChildrenInfoRequestDto requestDto);

	/**
	 * Set family for children
	 *
	 * @param childrenId
	 * @param familyId
	 */
	void setFamilyForChildren(Integer childrenId, Integer familyId);

	/**
	 * upload new documents for children
	 *
	 * @param id
	 * @param requestDto
	 */
	void uploadDocuments(Integer id, DocumentDto requestDto);

	/**
	 * update document for children
	 *
	 * @param id
	 * @param requestDto
	 */
	void updateDocuments(Integer id, DocumentDto requestDto);

	/**
	 * update children status
	 *
	 * @param id
	 * @param requestDto
	 */
	void updateChildrenStatus(Integer id, UpdateChildrenStatusRequestDto requestDto);

	void updateAdoptionStatus(Integer id, boolean adoptionStatus);
}
