package com.graduatebackend.mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.ReportingPolicy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.graduatebackend.dto.content.AddNewFaqQuestionRequestDto;
import com.graduatebackend.dto.content.AddNewInformationPageRequestDto;
import com.graduatebackend.dto.content.AddNewWebsiteContactRequestDto;
import com.graduatebackend.dto.content.GetFaqQuestionResponseDto;
import com.graduatebackend.dto.content.GetInformationPageResponseDto;
import com.graduatebackend.dto.content.GetInformationPageTypeResponseDto;
import com.graduatebackend.dto.content.GetWebsiteContactResponseDto;
import com.graduatebackend.entity.FaqQuestion;
import com.graduatebackend.entity.InformationPage;
import com.graduatebackend.entity.InformationPageType;
import com.graduatebackend.entity.WebsiteContact;

@Mapper(unmappedTargetPolicy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface ContentMapper {
	ContentMapper INSTANCE = Mappers.getMapper(ContentMapper.class);

	/**
	 * Convert AddNewFaqQuestionRequestDto to FaqQuestion Entity
	 *
	 * @param requestDto
	 * @return
	 */
	FaqQuestion toFaqQuestionEntity(AddNewFaqQuestionRequestDto requestDto);

	/**
	 * Convert FaqQuestion to GetFaqQuestionResponseDto
	 *
	 * @param faqQuestion
	 * @return
	 */
	GetFaqQuestionResponseDto toGetFaqQuestionResponseDto(FaqQuestion faqQuestion);

	/**
	 * Convert AddNewWebsiteContactRequestDto to WebsiteContact
	 *
	 * @param requestDto
	 * @return
	 */
	WebsiteContact toWebsiteContactEntity(AddNewWebsiteContactRequestDto requestDto);

	/**
	 * update website contact
	 *
	 * @param websiteContact
	 * @param requestDto
	 */
	void updateWebsiteContactEntity(@MappingTarget WebsiteContact websiteContact,
			AddNewWebsiteContactRequestDto requestDto);

	/**
	 * Convert WebsiteContact to GetWebsiteContactResponseDto
	 *
	 * @param websiteContact
	 * @return
	 */
	GetWebsiteContactResponseDto toGetWebsiteContactResponseDto(WebsiteContact websiteContact);

	/**
	 * Convert AddNewInformationPageRequestDto to InformationPage Entity
	 *
	 * @param requestDto
	 * @return
	 */
	InformationPage toInformationPageEntity(AddNewInformationPageRequestDto requestDto);

	/**
	 * update InformationPage
	 *
	 * @param informationPage
	 * @param requestDto
	 */
	void updateInformationPage(@MappingTarget InformationPage informationPage,
			AddNewInformationPageRequestDto requestDto);

	/**
	 * Convert InformationPageType Entity to GetInformationPageTypeResponseDto
	 *
	 * @param informationPageType
	 * @return
	 */
	GetInformationPageTypeResponseDto toGetInformationPageTypeResponseDto(InformationPageType informationPageType);

	/**
	 * Convert InformationPage Entity to GetInformationPageResponseDto
	 *
	 * @param informationPage
	 * @return
	 */
	@Mapping(target = "pageType", source = "informationPage.pageType.pageType")
	@Mapping(target = "description", source = "informationPage.pageType.description")
	GetInformationPageResponseDto toGetInformationPageResponseDto(InformationPage informationPage);
}
