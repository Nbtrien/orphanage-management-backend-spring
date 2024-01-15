package com.graduatebackend.service;

import java.sql.Date;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.graduatebackend.dto.employee.AddNewMonthlyPayrollRequestDto;
import com.graduatebackend.dto.employee.GetEmployeePayrollResponseDto;
import com.graduatebackend.dto.employee.GetMonthlyPayrollDetailResponseDto;
import com.graduatebackend.dto.employee.GetMonthlyPayrollResponseDto;
import com.graduatebackend.entity.MonthlyPayroll;

public interface EmployeePayrollService {

	/**
	 * fetch monthly payrolls
	 *
	 * @param pageRequest
	 * @return
	 */
	Page<GetMonthlyPayrollResponseDto> fetchMonthlyPayrolls(PageRequest pageRequest, Integer status, Date fromDate, Date toDate);

	/**
	 * fetch MonthlyPayroll by id
	 *
	 * @param id
	 * @return
	 */
	GetMonthlyPayrollDetailResponseDto fetchMonthlyPayrollDetail(Integer id, PageRequest pageRequest);

	/**
	 * fetch EmployeePayroll by month in year
	 *
	 * @param pageRequest
	 * @param year
	 * @param month
	 * @return
	 */
	Page<GetEmployeePayrollResponseDto> fetchByMonthInYear(PageRequest pageRequest, int year, int month);

	/**
	 * add new
	 *
	 * @param requestDto
	 */
	MonthlyPayroll addNew(AddNewMonthlyPayrollRequestDto requestDto);

	/**
	 * add New Employee Payroll
	 *
	 * @param monthlyPayroll
	 * @return
	 */
	CompletableFuture<Void> addNewEmployeePayroll(MonthlyPayroll monthlyPayroll);

	/**
	 * update EmployeePayroll
	 *
	 * @param id
	 * @param requestDto
	 */
	void updateEmployeePayroll(Integer id, GetEmployeePayrollResponseDto requestDto);

	/**
	 * Update MonthlyPayroll
	 *
	 * @param id
	 * @param requestDto
	 */
	void updateMonthlyPayroll(Integer id, GetMonthlyPayrollResponseDto requestDto);

	/**
	 * Confirm MonthlyPayroll
	 *
	 * @param id
	 */
	void confirmMonthlyPayroll(Integer id);

}
