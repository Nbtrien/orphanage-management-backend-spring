package com.graduatebackend.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.graduatebackend.dto.address.ProvinceDto;
import com.graduatebackend.entity.Province;

@Mapper
public interface ProvinceMapper {
	ProvinceMapper INSTANCE = Mappers.getMapper(ProvinceMapper.class);

	/**
	 * Convert Province entity to Province entity
	 *
	 * @param province
	 * @return
	 */
	@Named("toDto")
	ProvinceDto toDto(Province province);

	/**
	 * Convert Province entity list to Province dto list
	 *
	 * @param provinces
	 * @return
	 */
	@IterableMapping(qualifiedByName = "toDto")
	List<ProvinceDto> toDtoList(List<Province> provinces);
}
