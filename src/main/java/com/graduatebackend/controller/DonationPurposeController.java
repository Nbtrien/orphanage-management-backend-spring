package com.graduatebackend.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graduatebackend.dto.ResponseDto;
import com.graduatebackend.dto.articles.AddNewDonationPurposePostRequestDto;
import com.graduatebackend.dto.donation.AddNewDonationPurposeRequestDto;
import com.graduatebackend.dto.donation.GetDonationPurposeDetailResponseDto;
import com.graduatebackend.entity.DonationPurposePost;
import com.graduatebackend.service.DialogflowService;
import com.graduatebackend.service.DonationPurposeService;
import com.graduatebackend.utils.PaginationUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@CrossOrigin
@RequiredArgsConstructor
public class DonationPurposeController {
	private final DonationPurposeService donationPurposeService;
	private final DialogflowService dialogflowService;

	@GetMapping(value = "/donation-purposes")
	public ResponseEntity<?> fetchDonationPurposes() {
		return ResponseEntity.ok(ResponseDto.ok(donationPurposeService.fetchAllDonationPurpose()));
	}

	@GetMapping(value = "/donation-programs")
	public ResponseEntity<?> fetchDonationPrograms() {
		return ResponseEntity.ok(ResponseDto.ok(donationPurposeService.fetchDonationPrograms()));
	}

	@GetMapping(value = "/donation-programs/{id}")
	public ResponseEntity<?> getDonationProgramDetail(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(donationPurposeService.getDonationProgramDetail(id)));
	}

	@GetMapping(value = "/admin/donation-purposes")
	public ResponseEntity<?> fetch(@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		String keyword = requestParams.get("keyword");
		Boolean isActive = requestParams.get("isActive") == null ? null : requestParams.get("isActive").equals("1");
		Page<GetDonationPurposeDetailResponseDto> page = donationPurposeService.fetch(pageRequest, isActive, keyword);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@GetMapping(value = "/admin/donation-purposes/{id}")
	public ResponseEntity<?> getDetail(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(donationPurposeService.getDetail(id)));
	}

	@GetMapping(value = "/admin/donation-purposes/{id}/posts/update-info")
	public ResponseEntity<?> getDonationPurposePostUpdateInfo(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(donationPurposeService.getDonationPurposePostUpdate(id)));
	}

	@PostMapping(value = "/admin/donation-purposes/{id}/posts")
	public ResponseEntity<?> addNewDonationPurposePost(@PathVariable("id") Integer id,
			@RequestBody AddNewDonationPurposePostRequestDto requestDto, Model model) {
		DonationPurposePost post = donationPurposeService.addNewPost(id, requestDto);
		dialogflowService.createDonationProgramIntent(model, post);
		return ResponseEntity.noContent().build();
	}

	@PostMapping(value = "/admin/donation-purposes")
	public ResponseEntity<?> addNew(@RequestBody AddNewDonationPurposeRequestDto requestDto) {
		donationPurposeService.addNewPurpose(requestDto);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/admin/donation-purposes/{id}/update-status")
	public ResponseEntity<?> updateStatus(@PathVariable("id") Integer id) {
		donationPurposeService.updatePurposeStatus(id);
		return ResponseEntity.noContent().build();
	}
}
