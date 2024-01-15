package com.graduatebackend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.graduatebackend.dto.children.OrphanTypeDto;
import com.graduatebackend.entity.OrphanType;
import com.graduatebackend.mapper.OrphanTypeMapper;
import com.graduatebackend.repository.OrphanTypeRepository;
import com.graduatebackend.service.OrphanTypeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrphanTypeServiceImpl implements OrphanTypeService {
	private final OrphanTypeRepository orphanTypeRepository;

	@Override
	public List<OrphanTypeDto> fetchAll() {

		List<OrphanType> orphanTypes = orphanTypeRepository.findAll();
		return OrphanTypeMapper.INSTANCE.toDtoList(orphanTypes);
	}
}
