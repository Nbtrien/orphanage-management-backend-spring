package com.graduatebackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.graduatebackend.dto.adoption.AddAdoptionHistoryFromApplicationRequestDto;
import com.graduatebackend.dto.adoption.AddNewAdoptionHistoryRequestDto;
import com.graduatebackend.dto.adoption.GetAdoptionApplicationResponseDto;
import com.graduatebackend.dto.adoption.GetAdoptionHistoryDetailResponseDto;
import com.graduatebackend.dto.adoption.GetAdoptionHistoryResponseDto;

public interface AdoptionHistoryService {
	/**
	 * add new
	 *
	 * @param requestDto
	 */
	void addNew(AddNewAdoptionHistoryRequestDto requestDto);

	/**
	 * add from Adoption Application
	 *
	 * @param applicationId
	 * @param requestDto
	 */
	void addNewFromApplication(Integer applicationId, AddAdoptionHistoryFromApplicationRequestDto requestDto);

	/**
	 * fetch all history
	 *
	 * @param pageRequest
	 * @return
	 */
	Page<GetAdoptionHistoryResponseDto> fetch(PageRequest pageRequest, String keyword, String fromDate, String toDate);

	/**
	 * get details
	 *
	 * @param id
	 * @return
	 */
	GetAdoptionHistoryDetailResponseDto getDetail(Integer id);

	/**
	 * get application for history
	 *
	 * @param id
	 * @return
	 */
	GetAdoptionApplicationResponseDto getApplicationForHistory(Integer id);
}
