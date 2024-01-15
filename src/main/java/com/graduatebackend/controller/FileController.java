package com.graduatebackend.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graduatebackend.dto.ResponseDto;
import com.graduatebackend.dto.file.PresignedUrlRequestDto;
import com.graduatebackend.service.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@CrossOrigin
@RequiredArgsConstructor
public class FileController {
	private final FileService fileService;

	@PostMapping("/admin/files")
	public ResponseEntity<?> generatePresignedUrl(@Valid @RequestBody PresignedUrlRequestDto requestDto) {
		String fileName = requestDto.getFileName();
		String folderPath = requestDto.getFolderPath();
		return ResponseEntity.ok(ResponseDto.ok(fileService.generateUrl(fileName, folderPath)));
	}

	@PostMapping("/files")
	public ResponseEntity<?> genPresignedUrl(@Valid @RequestBody PresignedUrlRequestDto requestDto) {
		String fileName = requestDto.getFileName();
		String folderPath = requestDto.getFolderPath();
		return ResponseEntity.ok(ResponseDto.ok(fileService.generateUrl(fileName, folderPath)));
	}

	@GetMapping("/admin/files")
	public ResponseEntity<?> generateFileUrl(@RequestParam String fileName) {
		return ResponseEntity.ok(fileService.generateFileUrl(fileName));
	}
}
