package com.graduatebackend.controller;

import java.util.List;
import java.util.Map;

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
import com.graduatebackend.dto.employee.AddNewEmployeeRequestDto;
import com.graduatebackend.dto.employee.GetEmployeeResponseDto;
import com.graduatebackend.dto.employee.UpdateEmployeeRequestDto;
import com.graduatebackend.service.EmployeeService;
import com.graduatebackend.utils.PaginationUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/employees")
@RequiredArgsConstructor
public class EmployeeController {
	private final EmployeeService employeeService;

	@GetMapping()
	public ResponseEntity<?> fetch(@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		String keyword = requestParams.get("keyword");
		Integer positionId = requestParams.get("positionId") == null ? null
				: Integer.valueOf(requestParams.get("positionId"));
		String minDateOfAdmission = requestParams.get("minDateOfAdmission");
		String maxDateOfAdmission = requestParams.get("maxDateOfAdmission");

		Page<GetEmployeeResponseDto> page = employeeService.fetch(pageRequest, keyword, positionId, minDateOfAdmission,
				maxDateOfAdmission);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@GetMapping(params = "search")
	public ResponseEntity<?> search(@RequestParam(required = false) final Map<String, String> requestParams) {
		String keyword = requestParams.get("search");
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		Page<GetEmployeeResponseDto> page = employeeService.search(pageRequest, keyword);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getDetail(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(employeeService.getDetail(id)));
	}

	@GetMapping("/{id}/update-info")
	public ResponseEntity<?> getUpdateInfo(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(employeeService.getEmployeeUpdateInfo(id)));
	}

	@PostMapping()
	public ResponseEntity<?> addNew(@RequestBody AddNewEmployeeRequestDto requestDto) {
		employeeService.addNew(requestDto);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/update-info")
	public ResponseEntity<?> updateInfo(@PathVariable Integer id, @RequestBody UpdateEmployeeRequestDto requestDto) {
		employeeService.updateEmployee(id, requestDto);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping
	public ResponseEntity<?> delete(@RequestParam("ids") List<Integer> ids) {
		employeeService.delete(ids);
		return ResponseEntity.noContent().build();
	}
}
