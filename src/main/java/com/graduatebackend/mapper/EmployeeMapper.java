package com.graduatebackend.mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.ReportingPolicy.IGNORE;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.graduatebackend.dto.employee.AddNewEmployeeRequestDto;
import com.graduatebackend.dto.employee.AddNewPositionRequestDto;
import com.graduatebackend.dto.employee.GetEmployeeDetailsDto;
import com.graduatebackend.dto.employee.GetEmployeePositionResponseDto;
import com.graduatebackend.dto.employee.GetEmployeeResponseDto;
import com.graduatebackend.dto.employee.GetEmployeeUpdateInfoResponseDto;
import com.graduatebackend.dto.employee.GetPositionListResponseDto;
import com.graduatebackend.dto.employee.UpdateEmployeeRequestDto;
import com.graduatebackend.entity.Employee;
import com.graduatebackend.entity.EmployeePosition;

@Mapper(unmappedTargetPolicy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface EmployeeMapper {
	EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

	/**
	 * Convert EmployeePosition entity to GetEmployeePositionResponseDto
	 *
	 * @param employeePosition
	 * @return
	 */
	@Named("toGetEmployeePositionResponseDto")
	GetEmployeePositionResponseDto toGetEmployeePositionResponseDto(EmployeePosition employeePosition);

	/**
	 * Convert EmployeePosition entity to GetPositionListResponseDto
	 *
	 * @param employeePosition
	 * @return
	 */
	GetPositionListResponseDto toGetPositionListResponseDto(EmployeePosition employeePosition);

	/**
	 * Convert Employee entity to GetEmployeeResponseDto
	 *
	 * @param employee
	 * @return
	 */
	@Mapping(target = "position", source = "position.employeePositionTitle")
	GetEmployeeResponseDto toGetEmployeeResponseDto(Employee employee);

	/**
	 * Convert EmployeePosition entity list to GetEmployeePositionResponseDto list
	 *
	 * @param employeePositions
	 * @return
	 */
	@IterableMapping(qualifiedByName = "toGetEmployeePositionResponseDto")
	List<GetEmployeePositionResponseDto> toGetEmployeePositionResponseDtoList(List<EmployeePosition> employeePositions);

	/**
	 * Convert AddNewEmployeeRequestDto to Employee entity
	 *
	 * @param requestDto
	 * @return
	 */
	@Mapping(target = "salary.salaryAmount", source = "salary")
	@Mapping(target = "position.employeePositionId", source = "positionId")
	@Mapping(target = "address", expression = "java(AddressMapper.INSTANCE.toEntity(requestDto.getAddress()))")
	Employee toEntity(AddNewEmployeeRequestDto requestDto);

	/**
	 * Convert Employee entity to GetEmployeeDetailsDto
	 *
	 * @param employee
	 * @return
	 */
	@Mapping(target = "position", source = "position.employeePositionTitle")
	@Mapping(source = "salary.salaryAmount", target = "salary")
	@Mapping(target = "address", expression = "java(AddressMapper.INSTANCE.toAddressString(employee.getAddress()))")
	GetEmployeeDetailsDto toGetEmployeeDetailsDto(Employee employee);

	/**
	 * Convert Employee Entity to GetEmployeeUpdateInfoResponseDto
	 *
	 * @param employee
	 * @return
	 */
	@Mapping(source = "position.employeePositionId", target = "positionId")
	@Mapping(source = "salary.salaryAmount", target = "salary")
	@Mapping(target = "address", expression = "java(AddressMapper.INSTANCE.toAddNewAddressDto(employee.getAddress()))")
	GetEmployeeUpdateInfoResponseDto toGetEmployeeUpdateInfoResponseDto(Employee employee);

	/**
	 * update employee entity
	 *
	 * @param employee
	 * @param requestDto
	 */
	@Mapping(target = "address", ignore = true)
	@Mapping(target = "image", ignore = true)
	@Mapping(target = "salary.salaryAmount", source = "salary")
	void updateEmployeeEntity(@MappingTarget Employee employee, UpdateEmployeeRequestDto requestDto);

	/**
	 * Convert AddNewPositionRequestDto to EmployeePosition Entity
	 *
	 * @param requestDto
	 * @return
	 */
	EmployeePosition toEmployeePositionEntity(AddNewPositionRequestDto requestDto);
}
