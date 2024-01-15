package com.graduatebackend.mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.ReportingPolicy.IGNORE;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.graduatebackend.dto.children.ChildrenStatusDto;
import com.graduatebackend.dto.children.GetStatusHistoryResponseDto;
import com.graduatebackend.entity.ChildrenStatus;
import com.graduatebackend.entity.ChildrenStatusHistory;

@Mapper(unmappedTargetPolicy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface ChildrenStatusMapper {
	ChildrenStatusMapper INSTANCE = Mappers.getMapper(ChildrenStatusMapper.class);

	/**
	 * Convert ChildrenStatus entity to ChildrenStatusDto
	 *
	 * @param childrenStatus
	 * @return
	 */
	@Named("toDto")
	ChildrenStatusDto toDto(ChildrenStatus childrenStatus);

	/**
	 * Convert ChildrenStatus entity list to ChildrenStatusDto list
	 *
	 * @param childrenStatuses
	 * @return
	 */
	@IterableMapping(qualifiedByName = "toDto")
	List<ChildrenStatusDto> toDtoList(List<ChildrenStatus> childrenStatuses);

	/**
	 * Convert ChildrenStatusHistory Entity to GetStatusHistoryResponseDto
	 *
	 * @param childrenStatusHistory
	 * @return
	 */
	@Mapping(target = "childrenStatusId", source = "childrenStatus.childrenStatusId")
	@Mapping(target = "childrenStatusName", source = "childrenStatus.childrenStatusName")
	@Mapping(target = "description", source = "childrenStatus.description")
	GetStatusHistoryResponseDto toGetStatusHistoryResponseDto(ChildrenStatusHistory childrenStatusHistory);
}
