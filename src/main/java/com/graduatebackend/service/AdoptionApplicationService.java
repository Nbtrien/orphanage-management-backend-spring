package com.graduatebackend.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;

import com.graduatebackend.dto.adoption.AccountAddAdoptionApplicationRequestDto;
import com.graduatebackend.dto.adoption.AccountAddNewAdoptionApplicationRequestDto;
import com.graduatebackend.dto.adoption.AddNewAdoptionApplicationRequestDto;
import com.graduatebackend.dto.adoption.GetAdoptionApplicationDetailResponseDto;
import com.graduatebackend.dto.adoption.GetAdoptionApplicationHistoryResponseDto;
import com.graduatebackend.dto.adoption.GetAdoptionApplicationResponseDto;
import com.graduatebackend.entity.AdoptionApplication;

public interface AdoptionApplicationService {
	/**
	 * fetch all AdoptionApplication
	 *
	 * @param pageRequest
	 * @param applicationStatus
	 * @return
	 */
	Page<GetAdoptionApplicationResponseDto> fetch(PageRequest pageRequest, String applicationStatus);

	/**
	 * add new AdoptionApplication
	 *
	 * @param requestDto
	 */
	AdoptionApplication addNew(AddNewAdoptionApplicationRequestDto requestDto);

	/**
	 * account add new
	 *
	 * @param requestDto
	 * @return
	 */
	void accountAddNew(Model model, Integer accountId, AccountAddNewAdoptionApplicationRequestDto requestDto);

	/**
	 * account add Application
	 *
	 * @param model
	 * @param accountId
	 * @param requestDto
	 */
	void accountAddApplication(Model model, Integer accountId, AccountAddAdoptionApplicationRequestDto requestDto);

	/**
	 * get detail
	 *
	 * @param id
	 * @return
	 */
	GetAdoptionApplicationDetailResponseDto getDetail(Integer id);

	/**
	 * confirm adoption application
	 *
	 * @param id
	 * @return
	 */
	void confirmApplication(Integer id);

	/**
	 * decline adoption application
	 *
	 * @param id
	 * @return
	 */
	void declineApplication(Integer id);

	/**
	 * Generate pdf and send email confirm
	 *
	 * @param model
	 * @param id
	 * @return
	 */
	CompletableFuture<Void> generatePdfAndSendEmailConfirm(Model model, Integer id);

	/**
	 * send email declined
	 *
	 * @param id
	 * @return
	 */
	CompletableFuture<Void> sendEmailDecline(Integer id);

	/**
	 * save ApplicationDocument and send notification Email
	 *
	 * @param model
	 * @param application
	 * @return
	 */
	CompletableFuture<Void> saveApplicationDocumentAndSendEmail(Model model, AdoptionApplication application);

	/**
	 * get applications by account
	 *
	 * @param accountId
	 * @return
	 */
	List<GetAdoptionApplicationHistoryResponseDto> getAdoptionApplicationByAccount(Integer accountId);
}
