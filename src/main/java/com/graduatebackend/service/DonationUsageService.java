package com.graduatebackend.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.graduatebackend.dto.donation.GetFundingUsageDetailResponseDto;
import com.graduatebackend.dto.donation.GetFundingUsageResponseDto;
import com.graduatebackend.dto.donation.UseDonationByPurposeRequestDto;
import com.graduatebackend.entity.FundingUsage;

public interface DonationUsageService {
	GetFundingUsageDetailResponseDto getFundingUsageDetail(Integer id);

	/**
	 * fetch funding usage
	 *
	 * @param pageRequest
	 * @return GetFundingUsageResponseDto
	 */
	Page<GetFundingUsageResponseDto> fetchFundingUsage(PageRequest pageRequest, String keyword, Integer purposeId,
			String fromDate, String toDate);

	/**
	 * use donations with purpose
	 *
	 * @param requestDto
	 * @return
	 */
	FundingUsage useDonationByPurpose(Integer purposeId, UseDonationByPurposeRequestDto requestDto);

	/**
	 * use donation with donation id
	 *
	 * @param id
	 * @param requestDto
	 * @return
	 */
	FundingUsage useDonationById(Integer id, UseDonationByPurposeRequestDto requestDto);
	
	/**
	 * send funding usage email
	 * @param fundingUsage
	 * @return
	 */
	CompletableFuture<Void> sendFundingUsageEmail(FundingUsage fundingUsage);
}
