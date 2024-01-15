package com.graduatebackend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.graduatebackend.dto.children.AddNewRelativeRequestDto;
import com.graduatebackend.dto.children.GetChildrenRelativesResponseDto;
import com.graduatebackend.dto.children.GetRelativeResponseDto;
import com.graduatebackend.entity.Children;
import com.graduatebackend.entity.ChildrenRelativeRelationship;
import com.graduatebackend.entity.Relative;
import com.graduatebackend.exception.ResourceNotFoundException;
import com.graduatebackend.mapper.ChildrenMapper;
import com.graduatebackend.mapper.RelativeMapper;
import com.graduatebackend.repository.ChildrenRelativeRepository;
import com.graduatebackend.repository.ChildrenRepository;
import com.graduatebackend.repository.RelativeRepository;
import com.graduatebackend.service.RelativeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RelativeServiceImpl implements RelativeService {
	private final RelativeRepository relativeRepository;
	private final ChildrenRepository childrenRepository;
	private final ChildrenRelativeRepository childrenRelativeRepository;

	@Override
	public GetChildrenRelativesResponseDto fetchByChildrenId(Integer childrenId) {
		Children children = childrenRepository.findByChildrenIdAndIsDeleteIsFalse(childrenId)
				.orElseThrow(() -> new ResourceNotFoundException("Children not found!"));
		GetChildrenRelativesResponseDto responseDto = ChildrenMapper.INSTANCE
				.toGetChildrenRelativesResponseDto(children);
		List<GetRelativeResponseDto> relativeResponseDto = children.getRelativeRelationships().stream()
				.filter(r -> !r.isDelete() && !r.getRelative().isDelete()).map(r -> {
					GetRelativeResponseDto relativeDto = RelativeMapper.INSTANCE
							.toGetRelativeResponseDto(r.getRelative());
					relativeDto.setRelationship(r.getRelationship());
					return relativeDto;
				}).collect(Collectors.toList());
		responseDto.setRelatives(relativeResponseDto);
		return responseDto;
	}

	@Override
	@Transactional
	public void addNew(Integer childrenId, AddNewRelativeRequestDto requestDto) {
		Children children = childrenRepository.findByChildrenIdAndIsDeleteIsFalse(childrenId)
				.orElseThrow(() -> new ResourceNotFoundException("Children not found!"));

		Relative relative = RelativeMapper.INSTANCE.toEntity(requestDto);
		relative.setRelativeFullName(relative.getRelativeLastName() + " " + relative.getRelativeFirstName());

		ChildrenRelativeRelationship relationship = ChildrenRelativeRelationship.builder().relative(relative)
				.children(children).relationship(requestDto.getRelationship()).build();
		relative.getRelativeRelationships().add(relationship);

		relativeRepository.save(relative);
	}

	@Override
	public void deleteRelativeFromChildren(Integer childrenId, Integer relativeId) {
		childrenRelativeRepository.delete(childrenId, relativeId);
	}
}
