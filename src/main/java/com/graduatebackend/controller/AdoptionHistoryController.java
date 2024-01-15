package com.graduatebackend.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graduatebackend.dto.ResponseDto;
import com.graduatebackend.dto.adoption.AddAdoptionHistoryFromApplicationRequestDto;
import com.graduatebackend.dto.adoption.AddNewAdoptionHistoryRequestDto;
import com.graduatebackend.dto.adoption.GetAdoptionHistoryResponseDto;
import com.graduatebackend.service.AdoptionHistoryService;
import com.graduatebackend.service.ChildrenService;
import com.graduatebackend.utils.PaginationUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/adoption-history")
@CrossOrigin
@RequiredArgsConstructor
public class AdoptionHistoryController {
	private final AdoptionHistoryService adoptionHistoryService;
	private final ChildrenService childrenService;

	@GetMapping("/children")
	public ResponseEntity<?> getChildrenForHistory() {
		return ResponseEntity.ok(ResponseDto.ok(childrenService.getChildrenForAdoptionHistory()));
	}

	@PostMapping()
	public ResponseEntity<?> addNew(@RequestBody AddNewAdoptionHistoryRequestDto requestDto) {
		adoptionHistoryService.addNew(requestDto);
		return ResponseEntity.noContent().build();
	}

	@GetMapping()
	public ResponseEntity<?> fetch(@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		String keyword = requestParams.get("keyword");
		String fromDate = requestParams.get("fromDate");
		String toDate = requestParams.get("toDate");
		Page<GetAdoptionHistoryResponseDto> page = adoptionHistoryService.fetch(pageRequest, keyword, fromDate, toDate);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getDetail(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(adoptionHistoryService.getDetail(id)));
	}

	@GetMapping("/application/{id}")
	public ResponseEntity<?> getApplication(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(adoptionHistoryService.getApplicationForHistory(id)));
	}

	@PostMapping("/application/{id}")
	public ResponseEntity<?> addFromApplication(@PathVariable("id") Integer id,
			@RequestBody AddAdoptionHistoryFromApplicationRequestDto requestDto) {
		adoptionHistoryService.addNewFromApplication(id, requestDto);
		return ResponseEntity.noContent().build();
	}
}
