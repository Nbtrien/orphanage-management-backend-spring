package com.graduatebackend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.graduatebackend.dto.content.AddNewFaqQuestionRequestDto;
import com.graduatebackend.dto.content.AddNewInformationPageRequestDto;
import com.graduatebackend.dto.content.AddNewWebsiteContactRequestDto;
import com.graduatebackend.dto.content.GetFaqQuestionResponseDto;
import com.graduatebackend.dto.content.GetInformationPageResponseDto;
import com.graduatebackend.dto.content.GetInformationPageTypeResponseDto;
import com.graduatebackend.dto.content.GetWebsiteContactResponseDto;

public interface ContentService {
	/**
	 * add new faq
	 *
	 * @param requestDto
	 */
	void addNewFaq(AddNewFaqQuestionRequestDto requestDto);

	/**
	 * add new WebsiteContact
	 *
	 * @param requestDto
	 */
	void addNewWebsiteContact(AddNewWebsiteContactRequestDto requestDto);

	/**
	 * add new InformationPage
	 *
	 * @param requestDto
	 */
	void addNewInformationPage(AddNewInformationPageRequestDto requestDto);

	/**
	 * update website contact
	 *
	 * @param requestDto
	 */
	void updateWebsiteContact(Integer id, AddNewWebsiteContactRequestDto requestDto);

	/**
	 * fetch WebsiteContact
	 *
	 * @param pageRequest
	 * @param keyword
	 * @return
	 */
	Page<GetWebsiteContactResponseDto> fetchWebsiteContact(PageRequest pageRequest, String keyword);

	/**
	 * get one WebsiteContact
	 *
	 * @return
	 */
	GetWebsiteContactResponseDto getOneWebsiteContact();

	/**
	 * get all faq
	 *
	 * @return
	 */
	List<GetFaqQuestionResponseDto> getAllFaq();

	/**
	 * fetch faq
	 *
	 * @param pageRequest
	 * @param keyword
	 * @return
	 */
	Page<GetFaqQuestionResponseDto> fetchFaq(PageRequest pageRequest, String keyword);

	/**
	 * get InformationPageType Detail
	 *
	 * @param id
	 * @return
	 */
	GetInformationPageTypeResponseDto getInformationPageTypeDetail(Integer id);

	/**
	 * get information page detail
	 *
	 * @return
	 */
	GetInformationPageResponseDto getInformationPageDetail(Integer id);

	/**
	 * fetch information page
	 *
	 * @param pageRequest
	 * @param keyword
	 * @return
	 */
	Page<GetInformationPageTypeResponseDto> fetchInformationPage(PageRequest pageRequest, String keyword);

	/**
	 * delete faqs
	 *
	 * @param ids
	 */
	void deleteFaqs(List<Integer> ids);
}
