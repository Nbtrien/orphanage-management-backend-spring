package com.graduatebackend.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graduatebackend.dto.ResponseDto;
import com.graduatebackend.dto.employee.AddNewPositionRequestDto;
import com.graduatebackend.dto.employee.GetPositionListResponseDto;
import com.graduatebackend.service.EmployeePositionService;
import com.graduatebackend.utils.PaginationUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/employee-positions")
@RequiredArgsConstructor
public class EmployeePositionController {
	private final EmployeePositionService employeePositionService;

	@GetMapping("/get-all")
	public ResponseEntity<?> fetchAll() {
		return ResponseEntity.ok(ResponseDto.ok(employeePositionService.fetchAll()));
	}

	@GetMapping()
	public ResponseEntity<?> fetch(@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		String keyword = requestParams.get("keyword");
		Page<GetPositionListResponseDto> page = (employeePositionService.fetch(pageRequest, keyword));
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@PostMapping()
	public ResponseEntity<?> addNew(@RequestBody AddNewPositionRequestDto requestDto) {
		employeePositionService.addNew(requestDto);
		return ResponseEntity.noContent().build();
	}
}
