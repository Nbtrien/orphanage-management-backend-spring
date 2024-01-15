package com.graduatebackend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.graduatebackend.dto.children.ChildrenStatusDto;
import com.graduatebackend.dto.children.GetStatusHistoryResponseDto;
import com.graduatebackend.entity.Children;
import com.graduatebackend.entity.ChildrenStatus;
import com.graduatebackend.entity.ChildrenStatusHistory;
import com.graduatebackend.entity.StatusTransactionOption;
import com.graduatebackend.exception.ResourceNotFoundException;
import com.graduatebackend.mapper.ChildrenStatusMapper;
import com.graduatebackend.repository.ChildrenRepository;
import com.graduatebackend.repository.ChildrenStatusRepository;
import com.graduatebackend.repository.StatusHistoryRepository;
import com.graduatebackend.service.ChildrenStatusService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChildrenStatusServiceImpl implements ChildrenStatusService {
	private final ChildrenStatusRepository childrenStatusRepository;
	private final ChildrenRepository childrenRepository;
	private final StatusHistoryRepository statusHistoryRepository;

	@Override
	public List<ChildrenStatusDto> fetchAll() {
		List<ChildrenStatus> childrenStatuses = childrenStatusRepository.findByIsDeleteIsFalse();
		return ChildrenStatusMapper.INSTANCE.toDtoList(childrenStatuses);
	}

	@Override
	public List<ChildrenStatusDto> fetchStatusOptions(Integer id) {
		Children children = childrenRepository.findByChildrenIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Children not found"));
		List<ChildrenStatus> statusOptions = children.getChildrenStatus().getTransactionOptions().stream()
				.map(StatusTransactionOption::getToStatus).collect(Collectors.toList());
		return ChildrenStatusMapper.INSTANCE.toDtoList(statusOptions);
	}

	@Override
	public List<GetStatusHistoryResponseDto> getChildrenStatusHistory(Integer id) {
		Children children = childrenRepository.findByChildrenIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Children not found"));
		List<ChildrenStatusHistory> childrenStatusHistories = statusHistoryRepository.getAllByChildren(children);
		return childrenStatusHistories.stream().map(ChildrenStatusMapper.INSTANCE::toGetStatusHistoryResponseDto)
				.collect(Collectors.toList());
	}
}
