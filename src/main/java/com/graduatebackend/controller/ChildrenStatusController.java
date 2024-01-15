package com.graduatebackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.graduatebackend.dto.ResponseDto;
import com.graduatebackend.service.ChildrenStatusService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class ChildrenStatusController {
	private final ChildrenStatusService childrenStatusService;

	@GetMapping("/children-status")
	public ResponseEntity<?> fetchAll() {
		return ResponseEntity.ok(ResponseDto.ok(childrenStatusService.fetchAll()));
	}

	@GetMapping("/children/{id}/children-status/options")
	public ResponseEntity<?> fetchStatusOptions(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(childrenStatusService.fetchStatusOptions(id)));
	}

	@GetMapping("/children/{id}/status-history")
	public ResponseEntity<?> fetchStatusHistory(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(childrenStatusService.getChildrenStatusHistory(id)));
	}
}
