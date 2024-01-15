package com.graduatebackend.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graduatebackend.dto.ResponseDto;
import com.graduatebackend.dto.articles.AddNewFamilyPostRequestDto;
import com.graduatebackend.dto.family.AddNewFamilyConditionRequestDto;
import com.graduatebackend.dto.family.AddNewFamilyRequestDto;
import com.graduatebackend.dto.family.GetFamilyConditionResponseDto;
import com.graduatebackend.dto.family.GetFamilyDonationStatsResponseDto;
import com.graduatebackend.dto.family.GetFamilyResponseDto;
import com.graduatebackend.entity.FamilyPost;
import com.graduatebackend.service.DialogflowService;
import com.graduatebackend.service.ExcelFileService;
import com.graduatebackend.service.FamilyService;
import com.graduatebackend.utils.PaginationUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/families")
@RequiredArgsConstructor
public class FamilyController {
	private final FamilyService familyService;
	private final DialogflowService dialogflowService;
	private final ExcelFileService excelFileService;

	@GetMapping("/family-conditions")
	public ResponseEntity<?> fetchAllFamilyConditions() {
		return ResponseEntity.ok(ResponseDto.ok(familyService.fetchAllFamilyConditions()));
	}

	@GetMapping("/conditions")
	public ResponseEntity<?> fetchConditions(@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		Page<GetFamilyConditionResponseDto> page = familyService.fetchConditions(pageRequest);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@GetMapping("/mothers")
	public ResponseEntity<?> fetchAllMothersAvailable() {
		return ResponseEntity.ok(ResponseDto.ok(familyService.fetchAllMothersAvailable()));
	}

	@GetMapping()
	public ResponseEntity<?> fetchFamilies(@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		Page<GetFamilyResponseDto> page = familyService.fetchFamilies(pageRequest);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@GetMapping("/search")
	public ResponseEntity<?> searchFamilies(@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		String keyword = requestParams.get("keyword");
		Integer familyConditionId = requestParams.get("familyCondition") == null ? null
				: Integer.valueOf(requestParams.get("familyCondition"));
		String fromDate = requestParams.get("fromDate");
		String toDate = requestParams.get("toDate");
		Page<GetFamilyResponseDto> page = familyService.searchFamilies(pageRequest, keyword, familyConditionId,
				fromDate, toDate);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@GetMapping(params = "children_id")
	public ResponseEntity<?> fetchFamiliesForChildren(@RequestParam(value = "children_id") Integer childrenId) {
		return ResponseEntity.ok(ResponseDto.ok(familyService.fetchFamiliesForChildren(childrenId)));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getDetail(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(familyService.getDetail(id)));
	}

	@GetMapping("/donation-statistics")
	public ResponseEntity<?> getFamilyDonationStatistics(
			@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		String keyword = requestParams.get("keyword");
		Page<GetFamilyDonationStatsResponseDto> page = familyService.getFamilyDonationStats(pageRequest, keyword);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@GetMapping("/donation-statistics/{id}")
	public ResponseEntity<?> getFamilyDonationStatisticsDetail(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(familyService.getFamilyDonationStatsDetail(id)));
	}

	@PostMapping("/family-conditions")
	public ResponseEntity<?> addNewFamilyConditions(@Valid @RequestBody AddNewFamilyConditionRequestDto requestDto) {
		familyService.addNewFamilyCondition(requestDto);
		return ResponseEntity.noContent().build();
	}

	@PostMapping()
	public ResponseEntity<?> addNewFamily(@Valid @RequestBody AddNewFamilyRequestDto requestDto) {
		familyService.addNewFamily(requestDto);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}/posts/update-info")
	public ResponseEntity<?> getFamilyPostUpdateInfo(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(familyService.getFamilyPostUpdate(id)));
	}

	@PostMapping("/{id}/posts")
	public ResponseEntity<?> addNewFamilyPost(@PathVariable("id") Integer id,
			@RequestBody AddNewFamilyPostRequestDto requestDto, Model model) {
		FamilyPost post = familyService.addNewFamilyPost(id, requestDto);
		dialogflowService.createFamilyDonationIntent(model, post);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping
	public ResponseEntity<?> delete(@RequestParam("ids") List<Integer> ids) {
		familyService.delete(ids);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{familyId}/mothers/{motherId}")
	public ResponseEntity<?> updateMotherForFamily(@PathVariable("familyId") Integer familyId,
			@PathVariable("motherId") Integer motherId) {
		familyService.updateMotherForFamily(familyId, motherId);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{familyId}/delete-children")
	public ResponseEntity<?> deleteChildren(@PathVariable("familyId") Integer familyId,
			@RequestParam("ids") List<Integer> ids) {
		familyService.deleteChildren(familyId, ids);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/donation-statistics/export-to-excel")
	public ResponseEntity<?> exportFamilyDonationStatsToExcel() {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		String fileName = UUID.randomUUID() + "-donations.xlsx";
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
		return ResponseEntity.ok().headers(header)
				.body(new InputStreamResource(excelFileService.exportFamilyDonationStatsExcelFile()));
	}
}
