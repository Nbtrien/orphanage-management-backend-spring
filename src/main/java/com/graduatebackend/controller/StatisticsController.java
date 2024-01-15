package com.graduatebackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graduatebackend.dto.ResponseDto;
import com.graduatebackend.service.StatisticsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/statistics")
@CrossOrigin
@RequiredArgsConstructor
public class StatisticsController {
	private final StatisticsService statisticsService;

	@GetMapping("/summary")
	public ResponseEntity<?> getSummaryStatistics() {
		return ResponseEntity.ok(ResponseDto.ok(statisticsService.getSummaryStatistics()));
	}

	@GetMapping("/children/count-by-gender")
	public ResponseEntity<?> getChildrenStatisticsByGender() {
		return ResponseEntity.ok(ResponseDto.ok(statisticsService.getChildrenStatisticsByGender()));
	}

	@GetMapping("/children/count-by-age")
	public ResponseEntity<?> getChildrenStatisticsByAge() {
		return ResponseEntity.ok(ResponseDto.ok(statisticsService.getChildrenStatisticsByAge()));
	}

	@GetMapping("/children/count-by-status")
	public ResponseEntity<?> getChildrenStatisticsByStatus() {
		return ResponseEntity.ok(ResponseDto.ok(statisticsService.getChildrenStatisticsByStatus()));
	}

	@GetMapping("/children/count-by-family")
	public ResponseEntity<?> getChildrenStatisticsByFamily() {
		return ResponseEntity.ok(ResponseDto.ok(statisticsService.getChildrenStatisticsByFamily()));
	}

	@GetMapping("/donation/count-by-month")
	public ResponseEntity<?> getDonationStatisticsByMonthInYear() {
		return ResponseEntity.ok(ResponseDto.ok(statisticsService.getDonationStatisticsByMonth()));
	}

	@GetMapping("/donation/count-by-purpose")
	public ResponseEntity<?> getDonationStatisticsByPurpose(
			@RequestParam(value = "year", required = false) Integer year) {
		return ResponseEntity.ok(ResponseDto.ok(statisticsService.getDonationStatisticsByPurpose(year)));
	}

	@GetMapping("/donation/count-by-family")
	public ResponseEntity<?> getDonationStatisticsByFamily(
			@RequestParam(value = "year", required = false) Integer year) {
		return ResponseEntity.ok(ResponseDto.ok(statisticsService.getDonationStatisticsByFamily(year)));
	}

	@GetMapping("/donation/donor/top")
	public ResponseEntity<?> getTopDonor() {
		return ResponseEntity.ok(ResponseDto.ok(statisticsService.getTopDonor()));
	}
}
