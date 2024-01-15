package com.graduatebackend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.graduatebackend.exception.ResourceNotFoundException;
import com.graduatebackend.mapper.ContentMapper;
import com.graduatebackend.repository.FaqQuestionRepository;
import com.graduatebackend.repository.InformationPageRepository;
import com.graduatebackend.repository.InformationPageTypeRepository;
import com.graduatebackend.repository.WebsiteContactRepository;
import com.graduatebackend.service.ContentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {
	private final FaqQuestionRepository faqQuestionRepository;
	private final WebsiteContactRepository websiteContactRepository;
	private final InformationPageRepository informationPageRepository;
	private final InformationPageTypeRepository pageTypeRepository;

	@Override
	public void addNewFaq(AddNewFaqQuestionRequestDto requestDto) {
		FaqQuestion faqQuestion = ContentMapper.INSTANCE.toFaqQuestionEntity(requestDto);
		faqQuestionRepository.save(faqQuestion);
	}

	@Override
	@Transactional
	public void addNewWebsiteContact(AddNewWebsiteContactRequestDto requestDto) {
		List<WebsiteContact> websiteContacts = websiteContactRepository.findAllByIsDeleteIsFalse();
		websiteContacts.forEach(contact -> {
			contact.setDelete(true);
			websiteContactRepository.save(contact);
		});
		WebsiteContact websiteContact = ContentMapper.INSTANCE.toWebsiteContactEntity(requestDto);
		websiteContactRepository.save(websiteContact);
	}

	@Override
	public void addNewInformationPage(AddNewInformationPageRequestDto requestDto) {
		InformationPageType pageType = pageTypeRepository.findByPageTypeIdAndIsDeleteIsFalse(requestDto.getPageTypeId())
				.orElseThrow(() -> new ResourceNotFoundException("Page type not found."));
		if (pageType.getPage() == null) {
			InformationPage informationPage = ContentMapper.INSTANCE.toInformationPageEntity(requestDto);
			informationPage.setPageType(pageType);
			informationPageRepository.save(informationPage);
		} else {
			InformationPage informationPage = pageType.getPage();
			ContentMapper.INSTANCE.updateInformationPage(informationPage, requestDto);
			informationPageRepository.save(informationPage);
		}
	}

	@Override
	@Transactional
	public void updateWebsiteContact(Integer id, AddNewWebsiteContactRequestDto requestDto) {
		WebsiteContact websiteContact = websiteContactRepository.findOne()
				.orElseThrow(() -> new ResourceNotFoundException("WebsiteContact not found."));
		ContentMapper.INSTANCE.updateWebsiteContactEntity(websiteContact, requestDto);
	}

	@Override
	public List<GetFaqQuestionResponseDto> getAllFaq() {
		List<FaqQuestion> faqQuestions = faqQuestionRepository.findAllByIsDeleteIsFalse();
		return faqQuestions.stream().map(ContentMapper.INSTANCE::toGetFaqQuestionResponseDto)
				.collect(Collectors.toList());
	}

	@Override
	public Page<GetFaqQuestionResponseDto> fetchFaq(PageRequest pageRequest, String keyword) {
		Page<FaqQuestion> faqQuestions = faqQuestionRepository.findAll(pageRequest, keyword);
		return faqQuestions.map(ContentMapper.INSTANCE::toGetFaqQuestionResponseDto);
	}

	@Override
	public Page<GetWebsiteContactResponseDto> fetchWebsiteContact(PageRequest pageRequest, String keyword) {
		Page<WebsiteContact> websiteContacts = websiteContactRepository.findAll(pageRequest);
		return websiteContacts.map(ContentMapper.INSTANCE::toGetWebsiteContactResponseDto);
	}

	@Override
	public GetWebsiteContactResponseDto getOneWebsiteContact() {
		WebsiteContact websiteContact = websiteContactRepository.findOne()
				.orElseThrow(() -> new ResourceNotFoundException("WebsiteContact not found."));
		return ContentMapper.INSTANCE.toGetWebsiteContactResponseDto(websiteContact);
	}

	@Override
	public GetInformationPageTypeResponseDto getInformationPageTypeDetail(Integer id) {
		InformationPageType pageType = pageTypeRepository.findByPageTypeIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Page type not found."));
		GetInformationPageTypeResponseDto responseDto = ContentMapper.INSTANCE
				.toGetInformationPageTypeResponseDto(pageType);
		responseDto.setUpdate(pageType.getPage() != null);

		return responseDto;
	}

	@Override
	public GetInformationPageResponseDto getInformationPageDetail(Integer id) {
		InformationPage informationPage = informationPageRepository.findByPageType(id)
				.orElseThrow(() -> new ResourceNotFoundException("Information Page not found."));
		return ContentMapper.INSTANCE.toGetInformationPageResponseDto(informationPage);
	}

	@Override
	public Page<GetInformationPageTypeResponseDto> fetchInformationPage(PageRequest pageRequest, String keyword) {
		Page<InformationPageType> informationPageTypes = pageTypeRepository.findAll(pageRequest, keyword);
		Page<GetInformationPageTypeResponseDto> responseDtoPage = informationPageTypes.map(pageType -> {
			GetInformationPageTypeResponseDto responseDto = ContentMapper.INSTANCE
					.toGetInformationPageTypeResponseDto(pageType);
			responseDto.setUpdate(pageType.getPage() != null);

			return responseDto;
		});
		return responseDtoPage;
	}

	@Override
	public void deleteFaqs(List<Integer> ids) {
		faqQuestionRepository.softDeleteByIds(ids);
	}
}
