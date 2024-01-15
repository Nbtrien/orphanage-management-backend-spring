package com.graduatebackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.graduatebackend.dto.donation.GetDonorDetailResponseDto;
import com.graduatebackend.dto.donation.GetDonorListResponseDto;
import com.graduatebackend.dto.donation.GetDonorResponseDto;

public interface DonorService {
	/**
	 * get donor by token
	 *
	 * @param token String
	 * @return GetDonorResponseDto
	 */
	GetDonorResponseDto getDonorByToken(String token);

	/**
	 * get detail
	 *
	 * @param id Integer
	 * @return GetDonorDetailResponseDto
	 */
	GetDonorDetailResponseDto getDetail(Integer id);

	/**
	 * fetch donors
	 *
	 * @param pageRequest String
	 * @param name        String
	 * @param email       String
	 * @param phoneNumber String
	 * @return Page<GetDonorListResponseDto>
	 */
	Page<GetDonorListResponseDto> fetch(PageRequest pageRequest, String name, String email, String phoneNumber);
}
