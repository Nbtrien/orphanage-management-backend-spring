package com.graduatebackend.controller;

import java.sql.Date;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graduatebackend.dto.ResponseDto;
import com.graduatebackend.dto.employee.AddNewMonthlyPayrollRequestDto;
import com.graduatebackend.dto.employee.GetEmployeePayrollResponseDto;
import com.graduatebackend.dto.employee.GetMonthlyPayrollDetailResponseDto;
import com.graduatebackend.dto.employee.GetMonthlyPayrollResponseDto;
import com.graduatebackend.entity.MonthlyPayroll;
import com.graduatebackend.service.EmployeePayrollService;
import com.graduatebackend.utils.PaginationUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin
public class EmployeePayrollController {
	private final EmployeePayrollService employeePayrollService;

	@GetMapping(value = "/monthly-payrolls")
	public ResponseEntity<?> fetchMonthlyPayrolls(
			@RequestParam(required = false) final Map<String, String> requestParams) {
		if (requestParams.get("sortColumn") == null) {
			requestParams.put("sortColumn", "start_date");
			requestParams.put("sortType", "desc");
		}
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);

		Date fromDate = requestParams.get("fromDate") == null ? null : Date.valueOf(requestParams.get("fromDate"));
		Date toDate = requestParams.get("toDate") == null ? null : Date.valueOf(requestParams.get("toDate"));
		Integer status = requestParams.get("status") == null ? null : Integer.valueOf(requestParams.get("status"));

		Page<GetMonthlyPayrollResponseDto> page = (employeePayrollService.fetchMonthlyPayrolls(pageRequest, status, fromDate, toDate));
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@PostMapping(value = "/monthly-payrolls")
	public ResponseEntity<?> addNewMonthlyPayroll(@RequestBody AddNewMonthlyPayrollRequestDto requestDto) {
		MonthlyPayroll monthlyPayroll = employeePayrollService.addNew(requestDto);
		employeePayrollService.addNewEmployeePayroll(monthlyPayroll);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/monthly-payrolls/{id}")
	public ResponseEntity<?> fetchMonthlyPayrollDetail(
			@RequestParam(required = false) final Map<String, String> requestParams, @PathVariable("id") Integer id) {
		if (requestParams.get("sortColumn") == null) {
			requestParams.put("sortColumn", "payroll_start_date");
			requestParams.put("sortType", "desc");
		}
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		GetMonthlyPayrollDetailResponseDto responseDto = employeePayrollService.fetchMonthlyPayrollDetail(id,
				pageRequest);
		return ResponseEntity.ok(ResponseDto.ok(responseDto));
	}

	@PutMapping(value = "/monthly-payrolls/{id}")
	public ResponseEntity<?> updateMonthlyPayroll(@RequestBody GetMonthlyPayrollResponseDto requestDto,
			@PathVariable("id") Integer id) {
		employeePayrollService.updateMonthlyPayroll(id, requestDto);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/monthly-payrolls/{id}/confirm")
	public ResponseEntity<?> confirmMonthlyPayroll(@PathVariable("id") Integer id) {
		employeePayrollService.confirmMonthlyPayroll(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/employee-payrolls", params = { "month", "year" })
	public ResponseEntity<?> fetchByMonthInYear(
			@RequestParam(required = false) final Map<String, String> requestParams) {
		Integer month = Integer.valueOf(requestParams.get("month"));
		Integer year = Integer.valueOf(requestParams.get("year"));
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		Page<GetEmployeePayrollResponseDto> page = (employeePayrollService.fetchByMonthInYear(pageRequest, year,
				month));
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@PutMapping(value = "/employee-payrolls/{id}")
	public ResponseEntity<?> updateEmployeePayroll(@RequestBody GetEmployeePayrollResponseDto requestDto,
			@PathVariable("id") Integer id) {
		employeePayrollService.updateEmployeePayroll(id, requestDto);
		return ResponseEntity.noContent().build();
	}

}
