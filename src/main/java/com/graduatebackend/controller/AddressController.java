package com.graduatebackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.graduatebackend.dto.ResponseDto;
import com.graduatebackend.service.AddressService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@CrossOrigin
@RequiredArgsConstructor
public class AddressController {
	private final AddressService addressService;

	@GetMapping("/address/{id}")
	public ResponseEntity<?> getDetail(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(addressService.getAddressDetail(id));
	}

	@GetMapping("/provinces")
	public ResponseEntity<?> fetchAllProvinces() {
		return ResponseEntity.ok(ResponseDto.ok(addressService.fetchAllProvinces()));
	}
}
