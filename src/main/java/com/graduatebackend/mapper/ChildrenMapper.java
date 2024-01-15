package com.graduatebackend.mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.ReportingPolicy.IGNORE;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.graduatebackend.dto.children.AddNewChildrenRequestDto;
import com.graduatebackend.dto.children.DocumentDto;
import com.graduatebackend.dto.children.DocumentTypeDto;
import com.graduatebackend.dto.children.GetChildrenDetailResponseDto;
import com.graduatebackend.dto.children.GetChildrenDocumentDto;
import com.graduatebackend.dto.children.GetChildrenForAdoptionHistoryResponseDto;
import com.graduatebackend.dto.children.GetChildrenRecordResponseDto;
import com.graduatebackend.dto.children.GetChildrenRelativesResponseDto;
import com.graduatebackend.dto.children.GetChildrenResponseDto;
import com.graduatebackend.dto.children.GetChildrenStatusResponseDto;
import com.graduatebackend.dto.children.GetChildrenUpdateInfoResponseDto;
import com.graduatebackend.dto.children.UpdateChildrenInfoRequestDto;
import com.graduatebackend.dto.medical.GetChildMedicalRecordResponseDto;
import com.graduatebackend.entity.Children;
import com.graduatebackend.entity.ChildrenDocument;
import com.graduatebackend.entity.DocumentType;

@Mapper(unmappedTargetPolicy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface ChildrenMapper {
	ChildrenMapper INSTANCE = Mappers.getMapper(ChildrenMapper.class);

	/**
	 * Convert Children Dto to Children entity
	 *
	 * @param childrenDto
	 * @return
	 */
	@Named("toEntity")
	@Mapping(target = "documents", source = "documents", qualifiedByName = "toChildrenDocuments")
	@Mapping(target = "address", expression = "java(AddressMapper.INSTANCE.toEntity(requestDto.getAddress()))")
	Children toEntity(AddNewChildrenRequestDto requestDto);

	/**
	 * @param documentDto
	 * @return
	 */
	@Named("toChildrenDocument")
	@Mapping(target = "documentType", source = "documentTypeId", qualifiedByName = "toDocumentType")
	ChildrenDocument toChildrenDocument(DocumentDto documentDto);

	/**
	 * Convert from document type id to document type
	 *
	 * @param documentTypeId
	 * @return
	 */
	@Named("toDocumentType")
	default DocumentType toDocumentType(Integer documentTypeId) {
		return DocumentType.builder().documentTypeId(documentTypeId).build();
	}

	/**
	 * Convert document dto list to document entity list
	 *
	 * @param documentDtoList
	 * @return
	 */
	@Named("toChildrenDocuments")
	default List<ChildrenDocument> toChildrenDocuments(List<DocumentDto> documentDtoList) {
		if (documentDtoList == null) {
			return null;
		}
		return documentDtoList.stream().map(this::toChildrenDocument).collect(Collectors.toList());
	}

	/**
	 * Convert Children entity to GetChildrenResponseDto
	 *
	 * @param children
	 * @return
	 */
	@Mapping(target = "address", expression = "java(AddressMapper.INSTANCE.toAddressString(children.getAddress()))")
	@Mapping(target = "orphanType", source = "orphanType.orphanTypeName")
	@Mapping(target = "childrenStatus", source = "childrenStatus.childrenStatusName")
	@Mapping(target = "familyId", source = "family.familyId")
	GetChildrenResponseDto toGetChildrenResponseDto(Children children);

	@Mapping(target = "orphanType", source = "orphanType.orphanTypeName")
	@Mapping(target = "childrenStatus", source = "childrenStatus.childrenStatusName")
	@Mapping(target = "childrenStatusId", source = "childrenStatus.childrenStatusId")
	@Mapping(target = "familyId", source = "family.familyId")
	@Mapping(target = "familyName", source = "family.familyName")
	@Mapping(target = "motherId", source = "family.mother.motherId")
	@Mapping(target = "motherName", source = "family.mother.motherName")
	GetChildrenStatusResponseDto toGetChildrenStatusResponseDto(Children children);

	@Mapping(target = "orphanType", source = "orphanType.orphanTypeName")
	@Mapping(target = "documents", ignore = true)
	@Mapping(target = "childrenStatus", source = "childrenStatus.childrenStatusName")
	@Mapping(target = "medicalRecords", expression = "java(MedicalMapper.INSTANCE.toMedicalRecordResponseDtoList"
			+ "(children.getMedicalRecords()))")
	@Mapping(target = "vaccinationRecords", expression = "java(MedicalMapper.INSTANCE"
			+ ".toVaccinationRecordResponseDtoList" + "(children.getVaccinationRecords()))")
	GetChildrenRecordResponseDto toGetChildrenRecordResponseDto(Children children);

	/**
	 * Convert Children entity to GetDetailChildrenResponseDto
	 *
	 * @param children
	 * @return
	 */
	@Mapping(target = "address", expression = "java(AddressMapper.INSTANCE.toAddressString(children.getAddress()))")
	@Mapping(target = "orphanType", source = "orphanType.orphanTypeName")
	@Mapping(target = "documents", ignore = true)
	@Mapping(target = "childrenStatus", source = "childrenStatus.childrenStatusName")
	@Mapping(target = "childrenStatusId", source = "childrenStatus.childrenStatusId")
	GetChildrenDetailResponseDto toGetChildrenDetailResponseDto(Children children);

	/**
	 * Convert Children entity to GetChildMedicalRecordResponseDto
	 *
	 * @param children
	 * @return
	 */
	@Mapping(target = "medicalRecords", expression = "java(MedicalMapper.INSTANCE.toMedicalRecordResponseDtoList"
			+ "(children.getMedicalRecords()))")
	@Mapping(target = "vaccinationRecords", expression = "java(MedicalMapper.INSTANCE"
			+ ".toVaccinationRecordResponseDtoList" + "(children.getVaccinationRecords()))")
	GetChildMedicalRecordResponseDto toGetChildMedicalRecordResponseDto(Children children);

	GetChildrenRelativesResponseDto toGetChildrenRelativesResponseDto(Children children);

	/**
	 * Convert Children entity to GetChildrenUpdateInfoResponseDto
	 *
	 * @param children
	 * @return
	 */
	@Mapping(target = "childrenStatusId", source = "childrenStatus.childrenStatusId")
	@Mapping(target = "orphanTypeId", source = "orphanType.orphanTypeId")
	@Mapping(target = "address", expression = "java(AddressMapper.INSTANCE.toAddNewAddressDto(children.getAddress()))")
	GetChildrenUpdateInfoResponseDto toGetChildrenUpdateInfoResponseDto(Children children);

	/**
	 * Convert Children Entity to GetChildrenForAdoptionHistoryResponseDto
	 *
	 * @param children
	 * @return
	 */
	GetChildrenForAdoptionHistoryResponseDto toGetChildrenForAdoptionHistoryResponseDto(Children children);

	/**
	 * update children entity
	 *
	 * @param children
	 * @param requestDto
	 */
	@Mapping(target = "address", ignore = true)
	@Mapping(target = "image", ignore = true)
	@Mapping(target = "waitingAdoption", ignore = true)
	@Mapping(target = "childrenStatus", ignore = true)
	@Mapping(target = "family", ignore = true)
	void updateChildrenEntity(@MappingTarget Children children, UpdateChildrenInfoRequestDto requestDto);

	/**
	 * Convert ChildrenDocument list to GetChildrenDocumentDto list
	 *
	 * @param documents
	 * @return
	 */
	@Named("toDocumentsDto")
	default List<GetChildrenDocumentDto> toDocumentsDto(List<ChildrenDocument> documents) {
		if (documents == null) {
			return null;
		}
		return documents.stream().map(this::toGetChildrenDocumentDto).collect(Collectors.toList());
	}

	/**
	 * Convert ChildrenDocument entity to GetChildrenDocumentDto
	 *
	 * @param document
	 * @return
	 */
	@Named("toGetChildrenDocumentDto")
	default GetChildrenDocumentDto toGetChildrenDocumentDto(ChildrenDocument document) {

		return GetChildrenDocumentDto.builder().documentFileName(document.getDocumentFileName())
				.documentFilePath(document.getDocumentFilePath())
				.documentType(DocumentTypeDto.builder().documentTypeId(document.getDocumentType().getDocumentTypeId())
						.documentTypeName(document.getDocumentType().getDocumentTypeName()).build())
				.build();
	}
}