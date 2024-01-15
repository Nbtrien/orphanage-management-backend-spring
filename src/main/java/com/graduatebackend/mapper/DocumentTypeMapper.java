package com.graduatebackend.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.graduatebackend.dto.children.DocumentTypeDto;
import com.graduatebackend.entity.DocumentType;

@Mapper
public interface DocumentTypeMapper {
	DocumentTypeMapper INSTANCE = Mappers.getMapper(DocumentTypeMapper.class);

	/**
	 * Convert DocumentType entity to DocumentType dto
	 *
	 * @param documentType
	 * @return
	 */
	@Named("toDto")
	DocumentTypeDto toDto(DocumentType documentType);

	/**
	 * Convert DocumentType entity list to DocumentType dto list
	 *
	 * @param documentTypes
	 * @return
	 */
	@IterableMapping(qualifiedByName = "toDto")
	List<DocumentTypeDto> toDtoList(List<DocumentType> documentTypes);
}
