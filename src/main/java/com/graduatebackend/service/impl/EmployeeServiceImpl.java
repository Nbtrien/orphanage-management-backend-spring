package com.graduatebackend.service.impl;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.graduatebackend.constant.ChildrenCode;
import com.graduatebackend.constant.EmployeeCode;
import com.graduatebackend.dto.employee.AddNewEmployeeRequestDto;
import com.graduatebackend.dto.employee.GetEmployeeDetailsDto;
import com.graduatebackend.dto.employee.GetEmployeeResponseDto;
import com.graduatebackend.dto.employee.GetEmployeeUpdateInfoResponseDto;
import com.graduatebackend.dto.employee.UpdateEmployeeRequestDto;
import com.graduatebackend.entity.District;
import com.graduatebackend.entity.Employee;
import com.graduatebackend.entity.EmployeePayroll;
import com.graduatebackend.entity.MonthlyPayroll;
import com.graduatebackend.entity.Mother;
import com.graduatebackend.entity.Province;
import com.graduatebackend.entity.Salary;
import com.graduatebackend.entity.Ward;
import com.graduatebackend.exception.ResourceNotFoundException;
import com.graduatebackend.mapper.EmployeeMapper;
import com.graduatebackend.repository.EmployeePayrollRepository;
import com.graduatebackend.repository.EmployeeRepository;
import com.graduatebackend.repository.MonthlyPayrollRepository;
import com.graduatebackend.repository.MotherRepository;
import com.graduatebackend.service.EmployeeService;
import com.graduatebackend.service.FileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
	private final EmployeeRepository employeeRepository;
	private final EmployeePayrollRepository employeePayrollRepository;
	private final MonthlyPayrollRepository monthlyPayrollRepository;
	private final FileService fileService;
	private final MotherRepository motherRepository;

	@Override
	public Page<GetEmployeeResponseDto> fetch(PageRequest pageRequest, String keyword, Integer positionId,
			String minDateOfAdmission, String maxDateOfAdmission) {
		Date minDOA = minDateOfAdmission == null ? null : Date.valueOf(minDateOfAdmission);
		Date maxDOA = maxDateOfAdmission == null ? null : Date.valueOf(maxDateOfAdmission);

		Page<Employee> employees = employeeRepository.findAll(pageRequest, keyword, positionId, minDOA, maxDOA);
		return employees.map(emp -> {
			GetEmployeeResponseDto responseDto = EmployeeMapper.INSTANCE.toGetEmployeeResponseDto(emp);
			responseDto.setEmployeeGender(ChildrenCode.Gender.of(emp.getEmployeeGender()).getDisplay());
			// Create s3 image url from image file path
			responseDto.setImageUrl(fileService.generateFileUrl(emp.getImage().getImageFilePath()));
			return responseDto;
		});
	}

	@Override
	public Page<GetEmployeeResponseDto> search(PageRequest pageRequest, String keyword) {
		Page<Employee> employees = employeeRepository.searchByKeyword(pageRequest, keyword);
		return employees.map(emp -> {
			GetEmployeeResponseDto responseDto = EmployeeMapper.INSTANCE.toGetEmployeeResponseDto(emp);
			responseDto.setEmployeeGender(ChildrenCode.Gender.of(emp.getEmployeeGender()).getDisplay());
			// Create s3 image url from image file path
			responseDto.setImageUrl(fileService.generateFileUrl(emp.getImage().getImageFilePath()));
			return responseDto;
		});
	}

	@Override
	public GetEmployeeDetailsDto getDetail(Integer id) {
		Employee employee = employeeRepository.findEmployeeByEmployeeIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found!"));

		GetEmployeeDetailsDto responseDto = EmployeeMapper.INSTANCE.toGetEmployeeDetailsDto(employee);
		responseDto.setEmployeeGender(ChildrenCode.Gender.of(employee.getEmployeeGender()).getDisplay());
		// Create s3 image url from image file path
		responseDto.setImageUrl(fileService.generateFileUrl(employee.getImage().getImageFilePath()));

		responseDto.getPayrolls().forEach(p -> {
			Integer pStatus = Integer.valueOf(p.getPayrollStatus());
			p.setPayrollStatus(EmployeeCode.PayrollStatus.of(pStatus).getDisplay());
		});

		return responseDto;
	}

	@Override
	public GetEmployeeUpdateInfoResponseDto getEmployeeUpdateInfo(Integer id) {
		Employee employee = employeeRepository.findEmployeeByEmployeeIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found!"));

		return EmployeeMapper.INSTANCE.toGetEmployeeUpdateInfoResponseDto(employee);
	}

	@Override
	@Transactional
	public void addNew(AddNewEmployeeRequestDto requestDto) {
		Employee employee = EmployeeMapper.INSTANCE.toEntity(requestDto);
		employee.setEmployeeFullName(employee.getEmployeeLastName() + " " + employee.getEmployeeFirstName());

		Salary salary = employee.getSalary();
		salary.setPosition(employee.getPosition());
		salary.setSalaryStartDate(employee.getHireDate());
		employee.setSalary(salary);
		salary.setEmployee(employee);

		Employee employeeSaved = employeeRepository.save(employee);

		if (employeeSaved.getPosition().getEmployeePositionId()
				.equals(EmployeeCode.EmployeePosition.MOTHER.getCode())) {
			Mother mother = Mother.builder().employee(employeeSaved).motherName(employeeSaved.getEmployeeFullName())
					.build();
			motherRepository.save(mother);
		}

		if (validateHireDate(employeeSaved.getHireDate())) {
			Optional<MonthlyPayroll> monthlyPayrollOptional = monthlyPayrollRepository
					.findByMonthAndYearAndIsDeleteIsFalse(getMonth(), getYear());
			if (monthlyPayrollOptional.isPresent()) {
				MonthlyPayroll monthlyPayroll = monthlyPayrollOptional.get();

				EmployeePayroll payroll = new EmployeePayroll();
				payroll.setEmployee(employeeSaved);
				payroll.setPayrollStartDate(employeeSaved.getHireDate());
				payroll.setPayrollEndDate(getLastDayOfMonth());
				payroll.setDateOfPayment(null);
				payroll.setPayrollStatus(0);
				payroll.setPayrollAmount((float) 0);
				payroll.setSalary(employeeSaved.getSalary());
				payroll.setEmployeePayrollMonth(getMonth());
				payroll.setEmployeePayrollYear(getYear());
				payroll.setSalary(employeeSaved.getSalary());
				payroll.setPayrollSalary(employeeSaved.getSalary().getSalaryAmount());

				payroll.setMonthlyPayroll(monthlyPayroll);
				employeePayrollRepository.save(payroll);
			}
		}
	}

	@Override
	public void updateEmployee(Integer id, UpdateEmployeeRequestDto requestDto) {
		Employee employee = employeeRepository.findEmployeeByEmployeeIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found!"));
		EmployeeMapper.INSTANCE.updateEmployeeEntity(employee, requestDto);

		employee.setEmployeeFullName(employee.getEmployeeLastName() + " " + employee.getEmployeeFirstName());
		employee.getAddress().setWard(Ward.builder().wardId(requestDto.getAddress().getWardId()).build());
		employee.getAddress()
				.setProvince(Province.builder().provinceId(requestDto.getAddress().getProvinceId()).build());
		employee.getAddress()
				.setDistrict(District.builder().districtId(requestDto.getAddress().getDistrictId()).build());
		employee.getAddress().setAddressDetail(requestDto.getAddress().getAddressDetail());

		if (requestDto.getImage() != null) {
			employee.getImage().setImageFileName(requestDto.getImage().getImageFileName());
			employee.getImage().setImageFilePath(requestDto.getImage().getImageFilePath());
		}

		employeeRepository.save(employee);
	}

	@Override
	public void delete(List<Integer> ids) {
		employeeRepository.softDeleteByIds(ids);
	}

	private boolean validateHireDate(Date hireDate) {
		Calendar calendar = Calendar.getInstance();
		int currentMonth = calendar.get(Calendar.MONTH);
		calendar.setTime(hireDate);
		int dateMonth = calendar.get(Calendar.MONTH);

		return dateMonth == currentMonth;
	}

	private int getMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH) + 1;
	}

	private int getYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	private Date getLastDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		long millis = calendar.getTimeInMillis();
		return new Date(millis);
	}
}
