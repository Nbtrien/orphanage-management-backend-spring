package com.graduatebackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.graduatebackend.constant.Role;
import com.graduatebackend.dto.ResponseDto;
import com.graduatebackend.service.OrphanTypeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/orphan-types")
@RequiredArgsConstructor
public class OrphanTypeController {
	private final OrphanTypeService orphanTypeService;

	@GetMapping()
	@Secured({ Role.Code.SUPER_ADMIN })
	public ResponseEntity<?> fetchAll() {
		return ResponseEntity.ok(ResponseDto.ok(orphanTypeService.fetchAll()));
	}
}
