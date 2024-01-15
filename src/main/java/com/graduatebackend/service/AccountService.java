package com.graduatebackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.graduatebackend.dto.account.ActiveAccountRequestDto;
import com.graduatebackend.dto.account.AddNewAccountRequestDto;
import com.graduatebackend.dto.account.GetAccountDetailResponseDto;
import com.graduatebackend.dto.account.GetAccountResponseDto;
import com.graduatebackend.dto.account.VerifyAccountRequestDto;
import com.graduatebackend.dto.account.VerifyAccountResponseDto;
import com.graduatebackend.dto.adoption.GetAccountFistAdoptionResponseDto;
import com.graduatebackend.dto.authentication.AdminLoginRequestDto;
import com.graduatebackend.dto.donation.GetAccountDonorResponseDto;
import com.graduatebackend.entity.Token;

public interface AccountService {

	/**
	 * login
	 *
	 * @param requestDto
	 * @return
	 */
	Object authenticate(AdminLoginRequestDto requestDto);

	/**
	 * Create new account from Applicant entity
	 *
	 * @param requestDto
	 */
	Token applicantRegister(AddNewAccountRequestDto requestDto);

	/**
	 * Create new account from AddNewAccountRequestDto
	 *
	 * @param requestDto
	 */
	Token addNew(AddNewAccountRequestDto requestDto);

	/**
	 * Verify account
	 *
	 * @param requestDto
	 * @return
	 */
	VerifyAccountResponseDto verifyAccount(VerifyAccountRequestDto requestDto);

	/**
	 * verify applicant account
	 *
	 * @param requestDto
	 */
	void verifyApplicant(VerifyAccountRequestDto requestDto);

	/**
	 * active account
	 *
	 * @param requestDto
	 * @return
	 */
	Object activeAccount(ActiveAccountRequestDto requestDto);

	/**
	 * get account detail
	 *
	 * @param id
	 * @return
	 */
	GetAccountDetailResponseDto getAccountDetail(Integer id);

	/**
	 * get account application
	 *
	 * @param id
	 * @return
	 */
	GetAccountFistAdoptionResponseDto getAccountAdoptionInfo(Integer id);

	/**
	 * get account donor info
	 *
	 * @param id
	 * @return
	 */
	GetAccountDonorResponseDto getAccountDonorInfo(Integer id);

	/**
	 * fetch accounts
	 *
	 * @param pageRequest
	 * @param keyword
	 * @param accountType
	 * @param accountStatus
	 * @return
	 */
	Page<GetAccountResponseDto> fetchAccounts(PageRequest pageRequest, String keyword, Integer accountType,
			Integer accountStatus);

	/**
	 * update account status
	 *
	 * @param accountId
	 * @param status
	 */
	void updateAccountStatus(Integer accountId, Integer status);
}
