package com.graduatebackend.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graduatebackend.dto.ResponseDto;
import com.graduatebackend.dto.adoption.AddNewAdoptionApplicationRequestDto;
import com.graduatebackend.dto.adoption.GetAdoptionApplicationResponseDto;
import com.graduatebackend.entity.AdoptionApplication;
import com.graduatebackend.service.AdoptionApplicationService;
import com.graduatebackend.utils.PaginationUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@CrossOrigin
@RequiredArgsConstructor
public class AdoptionApplicationController {
	private final AdoptionApplicationService adoptionApplicationService;

	@PostMapping("/adoption-application")
	public ResponseEntity<?> addNew(Model model, @Valid @RequestBody AddNewAdoptionApplicationRequestDto requestDto) {
		AdoptionApplication application = adoptionApplicationService.addNew(requestDto);
		adoptionApplicationService.saveApplicationDocumentAndSendEmail(model, application);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/admin/adoption-application")
	public ResponseEntity<?> fetch(@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		String applicationStatus = requestParams.get("status");
		Page<GetAdoptionApplicationResponseDto> page = adoptionApplicationService.fetch(pageRequest, applicationStatus);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@GetMapping("/admin/adoption-application/{id}")
	public ResponseEntity<?> getDetail(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(adoptionApplicationService.getDetail(id)));
	}

	@PutMapping("/admin/adoption-application/{id}/confirm")
	public ResponseEntity<?> confirmApplication(Model model, @PathVariable("id") Integer id) {
		adoptionApplicationService.confirmApplication(id);
		adoptionApplicationService.generatePdfAndSendEmailConfirm(model, id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/admin/adoption-application/{id}/decline")
	public ResponseEntity<?> declineApplication(@PathVariable("id") Integer id) {
		adoptionApplicationService.declineApplication(id);
		adoptionApplicationService.sendEmailDecline(id);
		return ResponseEntity.noContent().build();
	}
}
