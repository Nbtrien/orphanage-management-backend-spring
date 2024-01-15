package com.graduatebackend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.graduatebackend.dto.employee.AddNewEmployeeRequestDto;
import com.graduatebackend.dto.employee.GetEmployeeDetailsDto;
import com.graduatebackend.dto.employee.GetEmployeeResponseDto;
import com.graduatebackend.dto.employee.GetEmployeeUpdateInfoResponseDto;
import com.graduatebackend.dto.employee.UpdateEmployeeRequestDto;

public interface EmployeeService {
	/**
	 * fetch employees
	 * @param pageRequest
	 * @param keyword
	 * @param positionId
	 * @param minDateOfAdmission
	 * @param maxDateOfAdmission
	 * @return
	 */
	Page<GetEmployeeResponseDto> fetch(PageRequest pageRequest, String keyword, Integer positionId, String minDateOfAdmission, String maxDateOfAdmission);

	/**
	 * search employees
	 *
	 * @param pageRequest
	 * @param keyword
	 * @return
	 */
	Page<GetEmployeeResponseDto> search(PageRequest pageRequest, String keyword);

	/**
	 * get employee details
	 *
	 * @param id
	 * @return
	 */
	GetEmployeeDetailsDto getDetail(Integer id);

	/**
	 * get employee update info
	 *
	 * @param id
	 * @return
	 */
	GetEmployeeUpdateInfoResponseDto getEmployeeUpdateInfo(Integer id);

	/**
	 * Add new Employee
	 *
	 * @param requestDto
	 * @return
	 */
	void addNew(AddNewEmployeeRequestDto requestDto);

	/**
	 * update employee
	 *
	 * @param id
	 * @param requestDto
	 */
	void updateEmployee(Integer id, UpdateEmployeeRequestDto requestDto);

	/**
	 * Delete employees
	 *
	 * @param ids
	 */
	void delete(List<Integer> ids);
}
