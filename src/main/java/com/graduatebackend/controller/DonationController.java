package com.graduatebackend.controller;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graduatebackend.dto.ResponseDto;
import com.graduatebackend.dto.donation.AddNewDonationRequestDto;
import com.graduatebackend.dto.donation.GetDonationMonthlyResponseDto;
import com.graduatebackend.dto.donation.GetDonationResponseDto;
import com.graduatebackend.dto.donation.GetDonorListResponseDto;
import com.graduatebackend.dto.donation.GetFundingUsageResponseDto;
import com.graduatebackend.dto.donation.UseDonationByPurposeRequestDto;
import com.graduatebackend.dto.donation.VnpayResponseDto;
import com.graduatebackend.entity.FundingUsage;
import com.graduatebackend.service.DonationService;
import com.graduatebackend.service.DonationUsageService;
import com.graduatebackend.service.DonorService;
import com.graduatebackend.service.ExcelFileService;
import com.graduatebackend.service.FamilyService;
import com.graduatebackend.utils.PaginationUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Log4j2
@RequiredArgsConstructor
public class DonationController {
	private final DonationService donationService;
	private final FamilyService familyService;
	private final DonationUsageService donationUsageService;
	private final DonorService donorService;
	private final ExcelFileService excelFileService;

	@PostMapping("/donations")
	public ResponseEntity<?> addNew(HttpServletRequest request, @RequestBody AddNewDonationRequestDto requestDto) {
		String ipAddress = getClientIP(request);
		return ResponseEntity.ok(ResponseDto.ok(donationService.addNew(requestDto, ipAddress)));
	}

	@PostMapping("/donors/{id}/donations")
	public ResponseEntity<?> doDonation(HttpServletRequest request, @PathVariable("id") Integer id,
			@RequestBody AddNewDonationRequestDto requestDto) {
		String ipAddress = getClientIP(request);
		return ResponseEntity.ok(ResponseDto.ok(donationService.donorDoDonate(id, requestDto, ipAddress)));
	}

	@GetMapping("/donations/callback")
	public ResponseEntity<VnpayResponseDto> handleCallback(
			@RequestParam(required = false) final Map<String, String> requestParams) {
		log.info("callback from vnpay.");
		return new ResponseEntity<>(donationService.update(requestParams), HttpStatus.OK);
	}

	@GetMapping(value = "/donations", params = "donationHash")
	public ResponseEntity<?> getByDonationHash(@RequestParam(value = "donationHash") String donationHash) {
		return ResponseEntity.ok(ResponseDto.ok(donationService.getDonationTrackingByDonationHash(donationHash)));
	}

	@GetMapping(value = "/donations", params = "donorToken")
	public ResponseEntity<?> getByDonorToken(@RequestParam(value = "donorToken") String donorToken) {
		return ResponseEntity.ok(ResponseDto.ok(donationService.getDonationsByDonorToken(donorToken)));
	}

	@GetMapping(value = "/donations/monthly")
	public ResponseEntity<?> getByDonorToken(@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		Integer year = Integer.valueOf(requestParams.get("year"));
		Integer month = Integer.valueOf(requestParams.get("month"));
		Page<GetDonationMonthlyResponseDto> page = donationService.getMonthlyDonations(pageRequest, year, month);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@GetMapping(value = "/donors", params = "token")
	public ResponseEntity<?> getDonorByToken(@RequestParam("token") String token) {
		return ResponseEntity.ok(ResponseDto.ok(donorService.getDonorByToken(token)));
	}

	@GetMapping(value = "/donations/families")
	public ResponseEntity<?> fetchAllFamiliesForDonate() {
		return ResponseEntity.ok(ResponseDto.ok(familyService.fetchAllFamiliesForDonate()));
	}

	@GetMapping(value = "/admin/donations")
	public ResponseEntity<?> fetch(@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		Integer purposeId = requestParams.get("purposeId") == null ? null
				: Integer.valueOf(requestParams.get("purposeId"));
		String keyword = requestParams.get("keyword");
		String fromDate = requestParams.get("fromDate");
		String toDate = requestParams.get("toDate");

		Page<GetDonationResponseDto> page = donationService.fetch(pageRequest, keyword, purposeId, fromDate, toDate);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@GetMapping(value = "/admin/donations/{id}")
	public ResponseEntity<?> getDetail(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(donationService.getDetail(id)));
	}

	@GetMapping(value = "/admin/donations/stats")
	public ResponseEntity<?> fetchDonationStats() {
		return ResponseEntity.ok(ResponseDto.ok(donationService.getDonationStats()));
	}

	@GetMapping(value = "/admin/donations/funding-usage")
	public ResponseEntity<?> fetchDonationUsage(
			@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		Integer purposeId = requestParams.get("purposeId") == null ? null
				: Integer.valueOf(requestParams.get("purposeId"));
		String keyword = requestParams.get("keyword");
		String fromDate = requestParams.get("fromDate");
		String toDate = requestParams.get("toDate");
		Page<GetFundingUsageResponseDto> page = donationUsageService.fetchFundingUsage(pageRequest, keyword, purposeId,
				fromDate, toDate);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@GetMapping(value = "/admin/donations/funding-usage/{id}")
	public ResponseEntity<?> getFundingUsageDetail(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(donationUsageService.getFundingUsageDetail(id)));
	}

	@GetMapping(value = "/admin/donors")
	public ResponseEntity<?> fetchDonors(@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		String name = requestParams.get("name");
		String email = requestParams.get("email");
		String phoneNumber = requestParams.get("phoneNumber");
		Page<GetDonorListResponseDto> page = donorService.fetch(pageRequest, name, email, phoneNumber);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@GetMapping(value = "/admin/donors/{id}")
	public ResponseEntity<?> getDonorDetail(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(donorService.getDetail(id)));
	}

	@GetMapping(value = "/admin/purposes/{id}/donations/usable")
	public ResponseEntity<?> fetchByPurpose(@RequestParam(required = false) final Map<String, String> requestParams,
			@PathVariable(value = "id") Integer purposeId) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		return ResponseEntity.ok(ResponseDto.ok(donationService.fetchByPurpose(pageRequest, purposeId)));
	}

	@PostMapping(value = "/admin/donations/purpose/{id}/use")
	public ResponseEntity<?> useDonationByPurpose(@PathVariable("id") Integer purposeId,
			@RequestBody UseDonationByPurposeRequestDto requestDto) {
		FundingUsage fundingUsage = donationUsageService.useDonationByPurpose(purposeId, requestDto);
		donationUsageService.sendFundingUsageEmail(fundingUsage);
		return ResponseEntity.noContent().build();
	}

	@PostMapping(value = "/admin/donations/{id}/use")
	public ResponseEntity<?> useDonationById(@PathVariable("id") Integer donationId,
			@RequestBody UseDonationByPurposeRequestDto requestDto) {
		FundingUsage fundingUsage = donationUsageService.useDonationById(donationId, requestDto);
		donationUsageService.sendFundingUsageEmail(fundingUsage);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/admin/donations/funding-usage/export-to-excel")
	public ResponseEntity<?> exportFundingUsageToExcel() {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		String fileName = UUID.randomUUID() + "-donations.xlsx";
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
		return ResponseEntity.ok().headers(header)
				.body(new InputStreamResource(excelFileService.exportDonationsUsageExcelFile()));
	}

	@GetMapping(value = "/admin/donations/export-to-excel")
	public ResponseEntity<?> exportDonationsToExcel() {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		String fileName = UUID.randomUUID() + "-donations.xlsx";
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
		return ResponseEntity.ok().headers(header)
				.body(new InputStreamResource(excelFileService.exportDonationsExcelFile()));
	}

	@GetMapping(value = "/admin/donors/export-to-excel")
	public ResponseEntity<?> exportDonorsToExcel() {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		String fileName = UUID.randomUUID() + "-donors.xlsx";
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
		return ResponseEntity.ok().headers(header)
				.body(new InputStreamResource(excelFileService.exportDonorsExcelFile()));
	}

	private String getClientIP(HttpServletRequest request) {
		String ipAddress = request.getHeader("X-Forwarded-For");
		if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress;
	}
}
