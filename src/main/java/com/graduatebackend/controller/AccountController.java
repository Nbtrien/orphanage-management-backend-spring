package com.graduatebackend.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graduatebackend.dto.ResponseDto;
import com.graduatebackend.dto.account.ActiveAccountRequestDto;
import com.graduatebackend.dto.account.AddNewAccountRequestDto;
import com.graduatebackend.dto.account.GetAccountResponseDto;
import com.graduatebackend.dto.account.VerifyAccountRequestDto;
import com.graduatebackend.dto.adoption.AccountAddAdoptionApplicationRequestDto;
import com.graduatebackend.dto.adoption.AccountAddNewAdoptionApplicationRequestDto;
import com.graduatebackend.dto.authentication.AdminLoginRequestDto;
import com.graduatebackend.dto.donation.AddNewDonationRequestDto;
import com.graduatebackend.email.EmailUtilsService;
import com.graduatebackend.entity.Token;
import com.graduatebackend.service.AccountService;
import com.graduatebackend.service.AdoptionApplicationService;
import com.graduatebackend.service.DonationService;
import com.graduatebackend.service.VolunteerService;
import com.graduatebackend.utils.PaginationUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@CrossOrigin
@RequiredArgsConstructor
public class AccountController {
	private final AccountService accountService;
	private final AdoptionApplicationService adoptionApplicationService;
	private final VolunteerService volunteerService;
	private final DonationService donationService;
	private final EmailUtilsService emailUtilsService;

	@PostMapping(value = "/login")
	private ResponseEntity<?> login(@Valid @RequestBody AdminLoginRequestDto requestDto) {
		return ResponseEntity.ok(ResponseDto.ok(accountService.authenticate(requestDto)));
	}

	@PostMapping(value = "/register")
	private ResponseEntity<?> addNew(@Valid @RequestBody AddNewAccountRequestDto requestDto) {
		Token token = accountService.addNew(requestDto);
		emailUtilsService.sendVerifyAccountEmail(token.getAccount().getAccountMailAddress(), token.getToken());
		return ResponseEntity.noContent().build();
	}

	@PostMapping(value = "/applicant/register")
	private ResponseEntity<?> applicantRegister(@Valid @RequestBody AddNewAccountRequestDto requestDto) {
		Token token = accountService.applicantRegister(requestDto);
		emailUtilsService.sendApplicantVerifyAccountEmail(token.getAccount().getAccountMailAddress(), token.getToken());
		return ResponseEntity.noContent().build();
	}

	@PostMapping(value = "/applicant/account/verify")
	private ResponseEntity<?> verifyApplicantAccount(@Valid @RequestBody VerifyAccountRequestDto requestDto) {
		accountService.verifyApplicant(requestDto);
		return ResponseEntity.ok(requestDto);
	}

	@PostMapping(value = "/account/verify")
	private ResponseEntity<?> verifyAccount(@Valid @RequestBody VerifyAccountRequestDto requestDto) {
		return ResponseEntity.ok(ResponseDto.ok(accountService.verifyAccount(requestDto)));
	}

	@PutMapping(value = "/account/verify")
	private ResponseEntity<?> verifyAccount(@RequestBody ActiveAccountRequestDto requestDto) {
		accountService.activeAccount(requestDto);
		return ResponseEntity.ok(requestDto);
	}

	@GetMapping(value = "/member/account/{id}")
	private ResponseEntity<?> getAccountDetail(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(accountService.getAccountDetail(id)));
	}

	@GetMapping(value = "/member/account/{id}/adoption-history")
	private ResponseEntity<?> getAdoptionHistory(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(adoptionApplicationService.getAdoptionApplicationByAccount(id)));
	}

	@GetMapping(value = "/member/account/{id}/volunteer-history")
	private ResponseEntity<?> getVolunteerEventHistory(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(volunteerService.getEventByAccount(id)));
	}

	@GetMapping(value = "/member/account/{id}/donor-info")
	private ResponseEntity<?> getAccountDonorInfo(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(accountService.getAccountDonorInfo(id)));
	}

	@GetMapping(value = "/member/account/{id}/adoption-info")
	private ResponseEntity<?> getAccountAdoptionInfo(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(accountService.getAccountAdoptionInfo(id)));
	}

	@PostMapping("/member/account/{id}/adoption-application/first-time")
	public ResponseEntity<?> accountAddNewAdoptionApplication(Model model, @PathVariable("id") Integer id,
			@Valid @RequestBody AccountAddNewAdoptionApplicationRequestDto requestDto) {
		adoptionApplicationService.accountAddNew(model, id, requestDto);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/member/account/{id}/adoption-application")
	public ResponseEntity<?> accountAddApplication(Model model, @PathVariable("id") Integer id,
			@Valid @RequestBody AccountAddAdoptionApplicationRequestDto requestDto) {
		adoptionApplicationService.accountAddApplication(model, id, requestDto);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/member/account/{id}/donations/first-time")
	public ResponseEntity<?> accountAddNewDonation(@PathVariable("id") Integer id, HttpServletRequest request,
			@RequestBody AddNewDonationRequestDto requestDto) {
		String ipAddress = getClientIP(request);
		return ResponseEntity.ok(ResponseDto.ok(donationService.accountAddNewDonation(id, requestDto, ipAddress)));
	}

	@GetMapping(value = "/admin/accounts")
	private ResponseEntity<?> fetchAccount(@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		String keyword = requestParams.get("keyword");
		Integer accountType = requestParams.get("accountType") == null ? null
				: Integer.valueOf(requestParams.get("accountType"));
		Integer accountStatus = requestParams.get("accountStatus") == null ? null
				: Integer.valueOf(requestParams.get("accountStatus"));
		Page<GetAccountResponseDto> page = accountService.fetchAccounts(pageRequest, keyword, accountType,
				accountStatus);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@PatchMapping(value = "/admin/accounts/{accountId}/update-status/{status}")
	private ResponseEntity<?> updateAccountStatus(@PathVariable("accountId") Integer accountId,
			@PathVariable("status") Integer status) {
		accountService.updateAccountStatus(accountId, status);
		return ResponseEntity.noContent().build();
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
