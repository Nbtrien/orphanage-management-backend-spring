package com.graduatebackend.service;

import java.util.List;

import com.graduatebackend.dto.content.GetStatisticsContentResponseDto;
import com.graduatebackend.dto.statistics.GetChildrenStatisticsByAgeResponseDto;
import com.graduatebackend.dto.statistics.GetChildrenStatisticsByFamilyResponseDto;
import com.graduatebackend.dto.statistics.GetChildrenStatisticsByGenderResponseDto;
import com.graduatebackend.dto.statistics.GetChildrenStatisticsByStatusResponseDto;
import com.graduatebackend.dto.statistics.GetDonationStatisticsByFamilyResponseDto;
import com.graduatebackend.dto.statistics.GetDonationStatisticsByMonthResponseDto;
import com.graduatebackend.dto.statistics.GetDonationStatisticsByPurposeResponseDto;
import com.graduatebackend.dto.statistics.GetTopDonorResponseDto;
import com.graduatebackend.dto.statistics.SummaryStatisticsResponseDto;

public interface StatisticsService {
	/**
	 * get summary statistics
	 *
	 * @return
	 */
	SummaryStatisticsResponseDto getSummaryStatistics();

	/**
	 * get Children Statistics By Gender
	 *
	 * @return
	 */
	GetChildrenStatisticsByGenderResponseDto getChildrenStatisticsByGender();

	/**
	 * get Children Statistics By Age
	 *
	 * @return
	 */
	List<GetChildrenStatisticsByAgeResponseDto> getChildrenStatisticsByAge();

	/**
	 * get Children Statistics By status
	 *
	 * @return
	 */
	List<GetChildrenStatisticsByStatusResponseDto> getChildrenStatisticsByStatus();

	/**
	 * get children statistics by family
	 *
	 * @return
	 */
	List<GetChildrenStatisticsByFamilyResponseDto> getChildrenStatisticsByFamily();

	/**
	 * get donation statistics by month
	 *
	 * @return
	 */
	List<GetDonationStatisticsByMonthResponseDto> getDonationStatisticsByMonth();

	/**
	 * get Donation Statistics By Purpose
	 *
	 * @return
	 */
	List<GetDonationStatisticsByPurposeResponseDto> getDonationStatisticsByPurpose(Integer year);

	/**
	 * get Donation Statistics By Purpose
	 *
	 * @return
	 */
	List<GetDonationStatisticsByFamilyResponseDto> getDonationStatisticsByFamily(Integer year);

	/**
	 * get top donor
	 *
	 * @return
	 */
	List<GetTopDonorResponseDto> getTopDonor();

	/**
	 * get StatisticsContent
	 *
	 * @return
	 */
	GetStatisticsContentResponseDto getStatisticsContent();
}
