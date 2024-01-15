package com.graduatebackend.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.graduatebackend.dto.authentication.AdminLoginRequestDto;
import com.graduatebackend.dto.authentication.RegisterRequestDto;
import com.graduatebackend.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AuthenticationController {
	private final AuthenticationService authenticationService;

	@PostMapping(value = "/api/admin/login")
	private ResponseEntity<?> adminLogin(@Valid @RequestBody AdminLoginRequestDto loginRequestDto) {
		return ResponseEntity.ok(authenticationService.authenticateAdmin(loginRequestDto));
	}

	@PostMapping(value = "/api/admin/register")
	private ResponseEntity<?> adminRegister(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
		return ResponseEntity.ok(authenticationService.registerAdmin(registerRequestDto));
	}
}
