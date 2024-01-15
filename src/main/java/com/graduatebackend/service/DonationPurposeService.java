package com.graduatebackend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.graduatebackend.dto.articles.AddNewDonationPurposePostRequestDto;
import com.graduatebackend.dto.articles.GetDonationProgramResponseDto;
import com.graduatebackend.dto.articles.GetDonationPurposePostUpdateResponseDto;
import com.graduatebackend.dto.donation.AddNewDonationPurposeRequestDto;
import com.graduatebackend.dto.donation.GetDonationPurposeDetailResponseDto;
import com.graduatebackend.dto.donation.GetDonationPurposeResponseDto;
import com.graduatebackend.entity.DonationPurposePost;

public interface DonationPurposeService {
	/**
	 * fetch all DonationPurpose
	 *
	 * @return List<GetDonationPurposeResponseDto>
	 */
	List<GetDonationPurposeResponseDto> fetchAllDonationPurpose();

	/**
	 * add new donation purpose post
	 *
	 * @param requestDto AddNewDonationPurposePostRequestDto
	 */
	DonationPurposePost addNewPost(Integer donationPurposeId, AddNewDonationPurposePostRequestDto requestDto);

	/**
	 * fetch donation purpose
	 *
	 * @return Page<GetDonationPurposeDetailResponseDto>
	 */
	Page<GetDonationPurposeDetailResponseDto> fetch(PageRequest pageRequest, Boolean isActive, String keyword);

	/**
	 * get detail
	 *
	 * @param id
	 * @return
	 */
	GetDonationPurposeDetailResponseDto getDetail(Integer id);

	/**
	 * fetchDonationPrograms
	 *
	 * @return
	 */
	List<GetDonationProgramResponseDto> fetchDonationPrograms();

	/**
	 * get DonationProgramDetail
	 *
	 * @param id
	 * @return
	 */
	GetDonationProgramResponseDto getDonationProgramDetail(Integer id);

	/**
	 * get donation purpose post update
	 *
	 * @param id
	 * @return
	 */
	GetDonationPurposePostUpdateResponseDto getDonationPurposePostUpdate(Integer donationPurposeId);

	/**
	 * add new donation purpose
	 *
	 * @param requestDto
	 */
	void addNewPurpose(AddNewDonationPurposeRequestDto requestDto);

	/**
	 * update donation purpose
	 *
	 * @param id
	 */
	void updatePurposeStatus(Integer id);
}
