package com.graduatebackend.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.graduatebackend.dto.employee.AddNewPositionRequestDto;
import com.graduatebackend.dto.employee.GetEmployeePositionResponseDto;
import com.graduatebackend.dto.employee.GetPositionListResponseDto;
import com.graduatebackend.entity.EmployeePosition;
import com.graduatebackend.mapper.EmployeeMapper;
import com.graduatebackend.repository.EmployeePositionRepository;
import com.graduatebackend.service.EmployeePositionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeePositionServiceImpl implements EmployeePositionService {
	private final EmployeePositionRepository positionRepository;

	@Override
	public List<GetEmployeePositionResponseDto> fetchAll() {
		List<EmployeePosition> employeePositions = positionRepository.findByIsDeleteIsFalse();
		return EmployeeMapper.INSTANCE.toGetEmployeePositionResponseDtoList(employeePositions);
	}

	@Override
	public Page<GetPositionListResponseDto> fetch(PageRequest pageRequest, String keyword) {
		Page<EmployeePosition> page = positionRepository.findAll(pageRequest, keyword);
		Set<Integer> uniquePositionIds = new HashSet<>();
		List<GetPositionListResponseDto> responseDtoList = new ArrayList<>();

		for (EmployeePosition position : page.getContent()) {
			Integer positionId = position.getEmployeePositionId();
			if (!uniquePositionIds.contains(positionId)) {
				uniquePositionIds.add(positionId);
				GetPositionListResponseDto responseDto = EmployeeMapper.INSTANCE.toGetPositionListResponseDto(position);
				long count = position.getEmployees().stream().filter(e -> !e.isDelete()).count();
				responseDto.setNoOfEmployees(Math.toIntExact(count));
				responseDtoList.add(responseDto);
			}
		}

		return new PageImpl<>(responseDtoList, page.getPageable(), page.getTotalElements());
	}

	@Override
	public void addNew(AddNewPositionRequestDto requestDto) {
		EmployeePosition position = EmployeeMapper.INSTANCE.toEmployeePositionEntity(requestDto);
		positionRepository.save(position);
	}
}
