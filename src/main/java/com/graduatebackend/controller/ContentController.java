package com.graduatebackend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graduatebackend.constant.ContentCode;
import com.graduatebackend.dto.ResponseDto;
import com.graduatebackend.dto.content.AddNewFaqQuestionRequestDto;
import com.graduatebackend.dto.content.AddNewInformationPageRequestDto;
import com.graduatebackend.dto.content.AddNewWebsiteContactRequestDto;
import com.graduatebackend.dto.content.GetFaqQuestionResponseDto;
import com.graduatebackend.dto.content.GetInformationPageTypeResponseDto;
import com.graduatebackend.dto.content.GetWebsiteContactResponseDto;
import com.graduatebackend.service.ContentService;
import com.graduatebackend.service.StatisticsService;
import com.graduatebackend.utils.PaginationUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class ContentController {
	private final ContentService contentService;
	private final StatisticsService statisticsService;

	@GetMapping(value = "/content/information-page/adoption-info")
	public ResponseEntity<?> getAdoptionInfoPage() {
		return ResponseEntity.ok(
				ResponseDto.ok(contentService.getInformationPageDetail(ContentCode.PageType.ADOPTION_INFO.getCode())));
	}

	@GetMapping(value = "/content/information-page/bank-info")
	public ResponseEntity<?> getBankInfoPage() {
		return ResponseEntity
				.ok(ResponseDto.ok(contentService.getInformationPageDetail(ContentCode.PageType.BANK_INFO.getCode())));
	}

	@GetMapping(value = "/content/faq")
	public ResponseEntity<?> getAllFaq() {
		return ResponseEntity.ok(ResponseDto.ok(contentService.getAllFaq()));
	}

	@GetMapping(value = "/content/statistics")
	public ResponseEntity<?> getHomeStatistics() {
		return ResponseEntity.ok(ResponseDto.ok(statisticsService.getStatisticsContent()));
	}

	@GetMapping(value = "/content/website-contact")
	public ResponseEntity<?> getOneWebsiteContact() {
		return ResponseEntity.ok(ResponseDto.ok(contentService.getOneWebsiteContact()));
	}

	@PostMapping(value = "/admin/content/faq")
	public ResponseEntity<?> addNewFaq(@RequestBody AddNewFaqQuestionRequestDto requestDto) {
		contentService.addNewFaq(requestDto);
		return ResponseEntity.noContent().build();
	}

	@PostMapping(value = "/admin/content/website-contact")
	public ResponseEntity<?> addNewWebsiteContact(@RequestBody AddNewWebsiteContactRequestDto requestDto) {
		contentService.addNewWebsiteContact(requestDto);
		return ResponseEntity.noContent().build();
	}

	@PostMapping(value = "/admin/content/information-page")
	public ResponseEntity<?> addNewWebsiteContact(@RequestBody AddNewInformationPageRequestDto requestDto) {
		contentService.addNewInformationPage(requestDto);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/admin/content/website-contact/{id}")
	public ResponseEntity<?> updateWebsiteContact(@PathVariable("id") Integer id,
			@RequestBody AddNewWebsiteContactRequestDto requestDto) {
		contentService.updateWebsiteContact(id, requestDto);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/admin/content/information-page-type/{id}")
	public ResponseEntity<?> getInformationPageTypeDetail(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(contentService.getInformationPageTypeDetail(id)));
	}

	@GetMapping(value = "/admin/content/information-page-type/{id}/information-page")
	public ResponseEntity<?> getInformationPageDetail(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(contentService.getInformationPageDetail(id)));
	}

	@GetMapping(value = "/admin/content/information-page-type")
	public ResponseEntity<?> fetchInformationPage(
			@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		String keyword = requestParams.get("keyword");
		Page<GetInformationPageTypeResponseDto> page = contentService.fetchInformationPage(pageRequest, keyword);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@GetMapping(value = "/admin/content/faq")
	public ResponseEntity<?> fetchFaq(@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		String keyword = requestParams.get("keyword");
		Page<GetFaqQuestionResponseDto> page = contentService.fetchFaq(pageRequest, keyword);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@GetMapping(value = "/admin/content/website-contact")
	public ResponseEntity<?> fetchWebsiteContact(
			@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		String keyword = requestParams.get("keyword");
		Page<GetWebsiteContactResponseDto> page = contentService.fetchWebsiteContact(pageRequest, keyword);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@DeleteMapping(value = "/admin/content/faq")
	public ResponseEntity<?> deleteFaqs(@RequestParam("ids") List<Integer> ids) {
		contentService.deleteFaqs(ids);
		return ResponseEntity.noContent().build();
	}
}
