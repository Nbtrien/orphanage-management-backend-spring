package com.graduatebackend.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.graduatebackend.constant.EmployeeCode;
import com.graduatebackend.dto.employee.AddNewMonthlyPayrollRequestDto;
import com.graduatebackend.dto.employee.GetEmployeePayrollResponseDto;
import com.graduatebackend.dto.employee.GetMonthlyPayrollDetailResponseDto;
import com.graduatebackend.dto.employee.GetMonthlyPayrollResponseDto;
import com.graduatebackend.entity.Employee;
import com.graduatebackend.entity.EmployeePayroll;
import com.graduatebackend.entity.MonthlyPayroll;
import com.graduatebackend.exception.ResourceExistedException;
import com.graduatebackend.exception.ResourceNotFoundException;
import com.graduatebackend.mapper.PayrollMapper;
import com.graduatebackend.repository.EmployeePayrollRepository;
import com.graduatebackend.repository.EmployeeRepository;
import com.graduatebackend.repository.MonthlyPayrollRepository;
import com.graduatebackend.service.EmployeePayrollService;
import com.graduatebackend.utils.ConvertUtils;
import com.graduatebackend.utils.PaginationUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeePayrollServiceImpl implements EmployeePayrollService {
	private final EmployeePayrollRepository payrollRepository;
	private final MonthlyPayrollRepository monthlyPayrollRepository;
	private final EmployeeRepository employeeRepository;

	@Override
	public Page<GetMonthlyPayrollResponseDto> fetchMonthlyPayrolls(PageRequest pageRequest, Integer status, Date fromDate, Date toDate) {
		Page<MonthlyPayroll> monthlyPayrolls = monthlyPayrollRepository.findAll(pageRequest, status, fromDate, toDate);
		return monthlyPayrolls.map(monthlyPayroll -> {
			GetMonthlyPayrollResponseDto responseDto = PayrollMapper.INSTANCE
					.toGetMonthlyPayrollResponseDto(monthlyPayroll);
			responseDto.setPayrollStatus(EmployeeCode.PayrollStatus.of(monthlyPayroll.getPayrollStatus()).getDisplay());
			return responseDto;
		});
	}

	@Override
	public GetMonthlyPayrollDetailResponseDto fetchMonthlyPayrollDetail(Integer id, PageRequest pageRequest) {
		MonthlyPayroll monthlyPayroll = monthlyPayrollRepository.findByMonthlyPayrollIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Payroll not found!"));
		GetMonthlyPayrollDetailResponseDto responseDto = PayrollMapper.INSTANCE
				.toGetMonthlyPayrollDetailResponseDto(monthlyPayroll);
		responseDto.setPayrollStatus(EmployeeCode.PayrollStatus.of(monthlyPayroll.getPayrollStatus()).getDisplay());

		Page<EmployeePayroll> employeePayrolls = payrollRepository.findByMonthlyPayrollId(pageRequest, id);
		Page<GetEmployeePayrollResponseDto> employeePayrollResponseDtoPage = employeePayrolls.map(employeePayroll -> {
			GetEmployeePayrollResponseDto employeePayrollResponseDto = PayrollMapper.INSTANCE
					.toGetEmployeePayrollResponseDto(employeePayroll);
			employeePayrollResponseDto
					.setPayrollStatus(EmployeeCode.PayrollStatus.of(employeePayroll.getPayrollStatus()).getDisplay());
			return employeePayrollResponseDto;
		});

		responseDto.setEmployeePayrolls(PaginationUtils.buildPageRes(employeePayrollResponseDtoPage));
		return responseDto;
	}

	@Override
	public Page<GetEmployeePayrollResponseDto> fetchByMonthInYear(PageRequest pageRequest, int year, int month) {
		Page<EmployeePayroll> employeePayrolls = payrollRepository.findByMonthInYear(pageRequest, month, year);
		return employeePayrolls.map(employeePayroll -> {
			GetEmployeePayrollResponseDto responseDto = PayrollMapper.INSTANCE
					.toGetEmployeePayrollResponseDto(employeePayroll);
			responseDto
					.setPayrollStatus(EmployeeCode.PayrollStatus.of(employeePayroll.getPayrollStatus()).getDisplay());
			return responseDto;
		});
	}

	@Override
	public MonthlyPayroll addNew(AddNewMonthlyPayrollRequestDto requestDto) {
		Optional<MonthlyPayroll> monthlyPayrollOptional = monthlyPayrollRepository
				.findByMonthAndYearAndIsDeleteIsFalse(requestDto.getMonth(), requestDto.getYear());
		if (monthlyPayrollOptional.isPresent()) {
			throw new ResourceExistedException("Monthly Payroll Existed.");
		}

		MonthlyPayroll monthlyPayroll = PayrollMapper.INSTANCE.toMonthlyPayrollEntity(requestDto);
		monthlyPayroll.setStartDate(ConvertUtils.getFirstDayOfMonth(requestDto.getMonth(), requestDto.getYear()));
		monthlyPayroll.setEndDate(ConvertUtils.getLastDayOfMonth(requestDto.getMonth(), requestDto.getYear()));
		monthlyPayroll.setPayrollStatus(0);

		return monthlyPayrollRepository.save(monthlyPayroll);
	}

	@Override
	@Async
	public CompletableFuture<Void> addNewEmployeePayroll(MonthlyPayroll monthlyPayroll) {
		List<Employee> employees = employeeRepository.findByHireDateBeforeMonth(monthlyPayroll.getYear(),
				monthlyPayroll.getMonth());

		employees.forEach(employee -> {
			EmployeePayroll payroll = new EmployeePayroll();
			payroll.setEmployee(employee);
			payroll.setMonthlyPayroll(monthlyPayroll);

			if (ConvertUtils.isHireDateAfterCurrentDate(monthlyPayroll.getMonth(), monthlyPayroll.getYear(),
					employee.getHireDate())) {
				payroll.setPayrollStartDate(employee.getHireDate());
			} else {
				payroll.setPayrollStartDate(monthlyPayroll.getStartDate());
			}

			payroll.setPayrollEndDate(monthlyPayroll.getEndDate());
			payroll.setDateOfPayment(null);
			payroll.setPayrollStatus(0);
			payroll.setSalary(employee.getSalary());
			payroll.setPayrollSalary(employee.getSalary().getSalaryAmount());
			payroll.setEmployeePayrollMonth(monthlyPayroll.getMonth());
			payroll.setEmployeePayrollYear(monthlyPayroll.getYear());
			payroll.setPayrollAmount((float) 0);
			payrollRepository.save(payroll);
		});
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public void updateEmployeePayroll(Integer id, GetEmployeePayrollResponseDto requestDto) {
		EmployeePayroll employeePayroll = payrollRepository.findByEmployeePayrollIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Payroll not found!"));
		PayrollMapper.INSTANCE.updateEmployeePayrollEntity(employeePayroll, requestDto);
		employeePayroll.setPayrollStatus(EmployeeCode.PayrollStatus.of(requestDto.getPayrollStatus()).getCode());

		payrollRepository.save(employeePayroll);
	}

	@Override
	public void updateMonthlyPayroll(Integer id, GetMonthlyPayrollResponseDto requestDto) {
		MonthlyPayroll monthlyPayroll = monthlyPayrollRepository.findByMonthlyPayrollIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Payroll not found!"));
		PayrollMapper.INSTANCE.updateMonthlyPayrollEntity(monthlyPayroll, requestDto);
		monthlyPayroll.setPayrollStatus(EmployeeCode.PayrollStatus.of(requestDto.getPayrollStatus()).getCode());

		monthlyPayrollRepository.save(monthlyPayroll);
	}

	@Override
	@Transactional
	public void confirmMonthlyPayroll(Integer id) {
		MonthlyPayroll monthlyPayroll = monthlyPayrollRepository.findByMonthlyPayrollIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Payroll not found!"));

		monthlyPayroll.getPayrolls().forEach(employeePayroll -> {
			if (!employeePayroll.isDelete()) {
				if (employeePayroll.getPayrollStatus() != EmployeeCode.PayrollStatus.DONE.getCode()) {
					employeePayroll.setPayrollStatus(EmployeeCode.PayrollStatus.DONE.getCode());
					employeePayroll.setDateOfPayment(new Date(System.currentTimeMillis()));
					payrollRepository.save(employeePayroll);
				}

			}
		});

		monthlyPayroll.setPayrollStatus(EmployeeCode.PayrollStatus.DONE.getCode());
		monthlyPayroll.setDateOfPayment(new Date(System.currentTimeMillis()));
		monthlyPayrollRepository.save(monthlyPayroll);
	}
}
