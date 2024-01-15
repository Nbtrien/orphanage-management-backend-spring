package com.graduatebackend.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.graduatebackend.dto.donation.AddNewDonationRequestDto;
import com.graduatebackend.dto.donation.GetDonationByPurposeResponseDto;
import com.graduatebackend.dto.donation.GetDonationDetailResponseDto;
import com.graduatebackend.dto.donation.GetDonationMonthlyResponseDto;
import com.graduatebackend.dto.donation.GetDonationResponseDto;
import com.graduatebackend.dto.donation.GetDonationStatResponseDto;
import com.graduatebackend.dto.donation.GetDonationTrackingResponseDto;
import com.graduatebackend.dto.donation.VnpayResponseDto;

public interface DonationService {
	/**
	 * generate vnpay url payment
	 *
	 * @param requestDto AddNewDonationRequestDto
	 * @return String
	 */
	String addNew(AddNewDonationRequestDto requestDto, String ipAddress);

	/**
	 * account add new donation
	 *
	 * @param requestDto
	 * @param ipAddress
	 * @return
	 */
	String accountAddNewDonation(Integer accountId, AddNewDonationRequestDto requestDto, String ipAddress);

	/**
	 * existed donor do donation
	 *
	 * @param donorId    Integer
	 * @param requestDto AddNewDonationRequestDto
	 * @param ipAddress  String
	 * @return String
	 */
	String donorDoDonate(Integer donorId, AddNewDonationRequestDto requestDto, String ipAddress);

	/**
	 * update donation from vnpay return
	 *
	 * @param requestParams Map<String, String>
	 * @return VnpayResponseDto
	 */
	VnpayResponseDto update(Map<String, String> requestParams);

	/**
	 * fetch all donations
	 *
	 * @param pageRequest PageRequest
	 * @param keyword     String
	 * @param purposeId   Integer
	 * @param fromDate    String
	 * @param toDate      String
	 * @return Page<GetDonationResponseDto>
	 */
	Page<GetDonationResponseDto> fetch(PageRequest pageRequest, String keyword, Integer purposeId, String fromDate,
			String toDate);

	/**
	 * get donation detail
	 *
	 * @param id Integer
	 * @return GetDonationDetailResponseDto
	 */
	GetDonationDetailResponseDto getDetail(Integer id);

	/**
	 * get donation stat
	 *
	 * @return GetDonationStatResponseDto
	 */
	GetDonationStatResponseDto getDonationStats();

	/**
	 * fetch by purpose id
	 *
	 * @param pageRequest PageRequest
	 * @param purposeId   Integer
	 * @return GetDonationByPurposeResponseDto
	 */
	GetDonationByPurposeResponseDto fetchByPurpose(PageRequest pageRequest, Integer purposeId);

	/**
	 * get donations by donor token
	 *
	 * @param donorToken
	 * @return
	 */
	List<GetDonationTrackingResponseDto> getDonationsByDonorToken(String donorToken);

	/**
	 * get donation by donation hash
	 *
	 * @param donationHash
	 * @return
	 */
	GetDonationTrackingResponseDto getDonationTrackingByDonationHash(String donationHash);

	/**
	 * get monthly donation
	 *
	 * @param pageRequest
	 * @param month
	 * @param year
	 * @return
	 */
	Page<GetDonationMonthlyResponseDto> getMonthlyDonations(PageRequest pageRequest, int year, int month);

//	void mockDonations();
}
