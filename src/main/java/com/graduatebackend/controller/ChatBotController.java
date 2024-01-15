package com.graduatebackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.graduatebackend.dto.ResponseDto;
import com.graduatebackend.service.DialogflowService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@CrossOrigin
@RequiredArgsConstructor
public class ChatBotController {
	private final DialogflowService dialogflowService;

	@PostMapping("/chat-bot/response")
	public ResponseEntity<?> handleUserInput(@RequestBody String message) {
		return ResponseEntity.ok(ResponseDto.ok(dialogflowService.detectIntent(message)));
	}

	@PostMapping("/admin/events/{id}/intents")
	public ResponseEntity<?> createEventIntent(@PathVariable("id") Integer id, Model model) {
		dialogflowService.createEventIntent(model, id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/admin/donation-purposes/{id}/intents")
	public ResponseEntity<?> createDonationPurposeIntent(@PathVariable("id") Integer id, Model model) {
		dialogflowService.createDonationProgramIntent(model, id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/admin/families/{id}/intents")
	public ResponseEntity<?> createFamilyDonationIntent(@PathVariable("id") Integer id, Model model) {
		dialogflowService.createFamilyDonationIntent(model, id);
		return ResponseEntity.noContent().build();
	}
}
