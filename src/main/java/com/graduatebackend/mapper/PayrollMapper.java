package com.graduatebackend.mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.ReportingPolicy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.graduatebackend.dto.employee.AddNewMonthlyPayrollRequestDto;
import com.graduatebackend.dto.employee.GetEmployeePayrollResponseDto;
import com.graduatebackend.dto.employee.GetMonthlyPayrollDetailResponseDto;
import com.graduatebackend.dto.employee.GetMonthlyPayrollResponseDto;
import com.graduatebackend.entity.EmployeePayroll;
import com.graduatebackend.entity.MonthlyPayroll;

@Mapper(unmappedTargetPolicy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface PayrollMapper {
	PayrollMapper INSTANCE = Mappers.getMapper(PayrollMapper.class);

	/**
	 * Convert MonthlyPayroll entity to GetMonthlyPayrollResponseDto
	 *
	 * @param monthlyPayroll
	 * @return
	 */
	GetMonthlyPayrollResponseDto toGetMonthlyPayrollResponseDto(MonthlyPayroll monthlyPayroll);

	/**
	 * Convert MonthlyPayroll entity to GetMonthlyPayrollResponseDto
	 * GetMonthlyPayrollDetailResponseDto
	 *
	 * @param monthlyPayroll
	 * @return
	 */
	GetMonthlyPayrollDetailResponseDto toGetMonthlyPayrollDetailResponseDto(MonthlyPayroll monthlyPayroll);

	/**
	 * Convert EmployeePayroll entity to GetEmployeePayrollResponseDto
	 *
	 * @param employeePayroll
	 * @return
	 */
	@Mapping(target = "employeeId", source = "employee.employeeId")
	@Mapping(target = "employeeFullName", source = "employee.employeeFullName")
	@Mapping(target = "employeeMailAddress", source = "employee.employeeMailAddress")
	GetEmployeePayrollResponseDto toGetEmployeePayrollResponseDto(EmployeePayroll employeePayroll);

	/**
	 * Convert GetEmployeePayrollResponseDto to EmployeePayroll dto
	 *
	 * @param dto
	 * @return
	 */
	EmployeePayroll toEmployeePayrollEntity(GetEmployeePayrollResponseDto dto);

	/**
	 * Update EmployeePayroll Entity
	 *
	 * @param employeePayroll
	 * @param dto
	 */
	@Mapping(target = "payrollStatus", ignore = true)
	void updateEmployeePayrollEntity(@MappingTarget EmployeePayroll employeePayroll, GetEmployeePayrollResponseDto dto);

	/**
	 * Update MonthlyPayroll Entity
	 *
	 * @param monthlyPayroll
	 * @param dto
	 */
	@Mapping(target = "payrollStatus", ignore = true)
	void updateMonthlyPayrollEntity(@MappingTarget MonthlyPayroll monthlyPayroll, GetMonthlyPayrollResponseDto dto);

	/**
	 * Convert AddNewMonthlyPayrollRequestDto to MonthlyPayroll Entity
	 *
	 * @param requestDto
	 * @return
	 */
	MonthlyPayroll toMonthlyPayrollEntity(AddNewMonthlyPayrollRequestDto requestDto);
}
