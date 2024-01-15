package com.graduatebackend.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.graduatebackend.dto.ResponseDto;
import com.graduatebackend.dto.children.AddNewRelativeRequestDto;
import com.graduatebackend.service.RelativeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class RelativeController {
	private final RelativeService relativeService;

	@GetMapping("/children/{id}/relatives")
	public ResponseEntity<?> fetchByChildrenId(@PathVariable("id") Integer childrenId) {
		return ResponseEntity.ok(ResponseDto.ok(relativeService.fetchByChildrenId(childrenId)));
	}

	@PostMapping("/children/{id}/relatives")
	public ResponseEntity<?> addNew(@PathVariable("id") Integer childrenId,
			@Valid @RequestBody AddNewRelativeRequestDto requestDto) {
		relativeService.addNew(childrenId, requestDto);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/children/{childrenId}/relatives/{relativeId}/delete-relative")
	public ResponseEntity<?> deleteRelative(@PathVariable("childrenId") Integer childrenId,
			@PathVariable("relativeId") Integer relativeId) {
		relativeService.deleteRelativeFromChildren(childrenId, relativeId);
		return ResponseEntity.noContent().build();
	}
}
