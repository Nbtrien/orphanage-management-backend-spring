package com.graduatebackend.mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.ReportingPolicy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.graduatebackend.dto.children.AddNewRelativeRequestDto;
import com.graduatebackend.dto.children.GetRelativeResponseDto;
import com.graduatebackend.entity.Relative;

@Mapper(unmappedTargetPolicy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface RelativeMapper {
	RelativeMapper INSTANCE = Mappers.getMapper(RelativeMapper.class);

	/**
	 * Convert AddNewRelativeRequestDto to Relative entity
	 *
	 * @param requestDto
	 * @return
	 */
	@Mapping(target = "address", expression = "java(AddressMapper.INSTANCE.toEntity(requestDto.getAddress()))")
	Relative toEntity(AddNewRelativeRequestDto requestDto);

	/**
	 * Convert Relative entity to GetRelativeResponseDto
	 *
	 * @param relative
	 * @return
	 */
	@Mapping(target = "address", expression = "java(AddressMapper.INSTANCE.toAddressString(relative.getAddress()))")
	@Mapping(target = "relationship", source = "")
	GetRelativeResponseDto toGetRelativeResponseDto(Relative relative);
}
