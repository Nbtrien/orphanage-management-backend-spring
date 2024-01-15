package com.graduatebackend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.graduatebackend.dto.employee.AddNewPositionRequestDto;
import com.graduatebackend.dto.employee.GetEmployeePositionResponseDto;
import com.graduatebackend.dto.employee.GetPositionListResponseDto;

public interface EmployeePositionService {
	/**
	 * fetch all positions
	 *
	 * @return
	 */
	List<GetEmployeePositionResponseDto> fetchAll();

	/**
	 * fetch positions page
	 *
	 * @return
	 */
	Page<GetPositionListResponseDto> fetch(PageRequest pageRequest, String keyword);

	/**
	 * add new position
	 * 
	 * @param requestDto
	 */
	void addNew(AddNewPositionRequestDto requestDto);
}
