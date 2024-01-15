package com.graduatebackend.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graduatebackend.dto.ResponseDto;
import com.graduatebackend.dto.children.AddNewChildrenRequestDto;
import com.graduatebackend.dto.children.DocumentDto;
import com.graduatebackend.dto.children.GetChildrenRecordResponseDto;
import com.graduatebackend.dto.children.GetChildrenResponseDto;
import com.graduatebackend.dto.children.GetChildrenStatusResponseDto;
import com.graduatebackend.dto.children.UpdateChildrenInfoRequestDto;
import com.graduatebackend.dto.children.UpdateChildrenStatusRequestDto;
import com.graduatebackend.service.ChildrenService;
import com.graduatebackend.service.FamilyService;
import com.graduatebackend.utils.PaginationUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/children")
@RequiredArgsConstructor
public class ChildrenController {
	private final ChildrenService childrenService;
	private final FamilyService familyService;

	@GetMapping()
	public ResponseEntity<?> fetch(@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		Page<GetChildrenResponseDto> page = childrenService.fetchAll(pageRequest);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@GetMapping("/status")
	public ResponseEntity<?> fetchAllChildrenStatus(
			@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		Page<GetChildrenStatusResponseDto> page = childrenService.fetchAllChildrenStatus(pageRequest);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@GetMapping("/records")
	public ResponseEntity<?> fetchAllChildrenRecords(
			@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		String keyword = requestParams.get("keyword");
		Page<GetChildrenRecordResponseDto> page = childrenService.fetchAllChildrenRecord(pageRequest, keyword);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@GetMapping(params = "search")
	public ResponseEntity<?> search(@RequestParam(required = false) final Map<String, String> requestParams) {
		String keyword = requestParams.get("search");
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		Page<GetChildrenResponseDto> page = childrenService.searchByKeyword(pageRequest, keyword);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@GetMapping("/search")
	public ResponseEntity<?> searchAllChildren(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "minAge", required = false) Integer minAge,
			@RequestParam(value = "maxAge", required = false) Integer maxAge) {
		return ResponseEntity.ok(ResponseDto.ok(childrenService.searchChildren(name, id, minAge, maxAge)));
	}

	@GetMapping(value = "/search", params = "limit")
	public ResponseEntity<?> searchChildren(@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		String keyword = requestParams.get("keyword");
		String minAge = requestParams.get("minAge");
		String maxAge = requestParams.get("maxAge");
		String orphanType = requestParams.get("orphanType");
		String minDateOfAdmission = requestParams.get("minDateOfAdmission");
		String maxDateOfAdmission = requestParams.get("maxDateOfAdmission");
		String status = requestParams.get("status");

		Page<GetChildrenResponseDto> page = childrenService.search(pageRequest, keyword, orphanType, minAge, maxAge,
				status, minDateOfAdmission, maxDateOfAdmission);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@GetMapping(value = "/status/search")
	public ResponseEntity<?> searchChildrenStatus(
			@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		String keyword = requestParams.get("keyword");
		String minAge = requestParams.get("minAge");
		String maxAge = requestParams.get("maxAge");
		String orphanType = requestParams.get("orphanType");
		String minDateOfAdmission = requestParams.get("minDateOfAdmission");
		String maxDateOfAdmission = requestParams.get("maxDateOfAdmission");
		String status = requestParams.get("status");

		Page<GetChildrenStatusResponseDto> page = childrenService.searchChildrenStatus(pageRequest, keyword, orphanType,
				minAge, maxAge, status, minDateOfAdmission, maxDateOfAdmission);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@GetMapping("/family/{family_id}")
	public ResponseEntity<?> fetchByFamilyId(@PathVariable("family_id") Integer familyId) {
		return ResponseEntity.ok(ResponseDto.ok(childrenService.fetchByFamilyId(familyId)));
	}

	@GetMapping("/{id}/family-history")
	public ResponseEntity<?> getFamilyHistory(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(familyService.getChildrenFamilyHistory(id)));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getDetail(@PathVariable Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(childrenService.getDetail(id)));
	}

	@GetMapping("/{id}/medical-vaccination-records")
	public ResponseEntity<?> getMedicalRecord(@PathVariable Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(childrenService.getMedicalRecord(id)));
	}

	@GetMapping("/{id}/update-info")
	public ResponseEntity<?> getUpdateInfo(@PathVariable Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(childrenService.getUpdateInfo(id)));
	}

	@PostMapping()
	public ResponseEntity<?> addNew(@Valid @RequestBody AddNewChildrenRequestDto requestDto) {
		childrenService.addNew(requestDto);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/update-info")
	public ResponseEntity<?> updateInfo(@PathVariable Integer id,
			@RequestBody UpdateChildrenInfoRequestDto requestDto) {
		return ResponseEntity.ok(ResponseDto.ok(childrenService.updateInfo(id, requestDto)));
	}

	@DeleteMapping
	public ResponseEntity<?> delete(@RequestParam("ids") List<Integer> ids) {
		childrenService.delete(ids);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/document-types")
	public ResponseEntity<?> getDocumentTypes() {
		return ResponseEntity.ok(ResponseDto.ok(childrenService.getDocumentTypes()));
	}

	@PutMapping("/{children_id}/set-family")
	public ResponseEntity<?> setFamilyForChild(@PathVariable("children_id") Integer childrenId,
			@RequestParam(value = "family_id") Integer familyId) {
		childrenService.setFamilyForChildren(childrenId, familyId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/upload-documents")
	public ResponseEntity<?> uploadDocuments(@PathVariable("id") Integer id,
			@Valid @RequestBody DocumentDto requestDto) {
		childrenService.uploadDocuments(id, requestDto);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/update-documents")
	public ResponseEntity<?> updateDocuments(@PathVariable("id") Integer id,
			@Valid @RequestBody DocumentDto requestDto) {
		childrenService.updateDocuments(id, requestDto);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}/update-status")
	public ResponseEntity<?> getUpdateStatusOptions(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(childrenService.getUpdateStatusOption(id)));
	}

	@PutMapping("/{id}/update-status")
	public ResponseEntity<?> updateStatus(@PathVariable("id") Integer id,
			@RequestBody UpdateChildrenStatusRequestDto requestDto) {
		childrenService.updateChildrenStatus(id, requestDto);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/adoption-status")
	public ResponseEntity<?> updateAdoptionStatus(@PathVariable("id") Integer id,
			@RequestParam(value = "adoption_status") boolean adoptionStatus) {
		childrenService.updateAdoptionStatus(id, adoptionStatus);
		return ResponseEntity.noContent().build();
	}
}
