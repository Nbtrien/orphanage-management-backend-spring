package com.graduatebackend.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.graduatebackend.dto.children.OrphanTypeDto;
import com.graduatebackend.entity.OrphanType;

@Mapper
public interface OrphanTypeMapper {
	OrphanTypeMapper INSTANCE = Mappers.getMapper(OrphanTypeMapper.class);

	/**
	 * Convert OrphanType entity to OrphanType dto
	 *
	 * @param orphanType
	 * @return
	 */
	@Named("toDto")
	OrphanTypeDto toDto(OrphanType orphanType);

	/**
	 * Convert OrphanType entity list to OrphanType dto list
	 *
	 * @param orphanTypes
	 * @return
	 */
	@IterableMapping(qualifiedByName = "toDto")
	List<OrphanTypeDto> toDtoList(List<OrphanType> orphanTypes);
}
