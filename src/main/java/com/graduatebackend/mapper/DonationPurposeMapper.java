package com.graduatebackend.mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.ReportingPolicy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.graduatebackend.dto.articles.AddNewDonationPurposePostRequestDto;
import com.graduatebackend.dto.articles.GetDonationProgramResponseDto;
import com.graduatebackend.dto.articles.GetDonationPurposePostResponseDto;
import com.graduatebackend.dto.articles.GetDonationPurposePostUpdateResponseDto;
import com.graduatebackend.dto.donation.GetDonationPurposeDetailResponseDto;
import com.graduatebackend.entity.DonationPurpose;
import com.graduatebackend.entity.DonationPurposePost;

@Mapper(unmappedTargetPolicy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface DonationPurposeMapper {
	DonationPurposeMapper INSTANCE = Mappers.getMapper(DonationPurposeMapper.class);

	/**
	 * Convert AddNewDonationPurposePostRequestDto to DonationPurposePost entity
	 *
	 * @param requestDto AddNewDonationPurposePostRequestDto
	 * @return DonationPurposePost
	 */
	DonationPurposePost toDonationPurposePost(AddNewDonationPurposePostRequestDto requestDto);

	/**
	 * Convert DonationPurpose entity to GetDonationPurposeDetailResponseDto
	 *
	 * @param donationPurpose DonationPurpose
	 * @return GetDonationPurposeDetailResponseDto
	 */
	GetDonationPurposeDetailResponseDto toGetDonationPurposeDetailResponseDto(DonationPurpose donationPurpose);

	/**
	 * Convert DonationPurposePost to GetDonationPurposePostResponseDto
	 *
	 * @param post
	 * @return
	 */
	GetDonationPurposePostResponseDto toGetDonationPurposePostResponseDto(DonationPurposePost post);

	/**
	 * Convert from DonationPurpose to GetDonationProgramResponseDto
	 *
	 * @param donationPurpose
	 * @return
	 */
	@Mapping(target = "post", expression = "java(toGetDonationPurposePostResponseDto(donationPurpose.getPost()))")
	GetDonationProgramResponseDto toGetDonationProgramResponseDto(DonationPurpose donationPurpose);

	/**
	 * Convert DonationPurposePost Entity to GetDonationPurposePostUpdateResponseDto
	 *
	 * @param donationPurposePost
	 * @return
	 */
	@Mapping(target = "donationPurposeId", source = "donationPurpose.donationPurposeId")
	@Mapping(target = "purpose", source = "donationPurpose.purpose")
	GetDonationPurposePostUpdateResponseDto toGetDonationPurposePostUpdateResponseDto(
			DonationPurposePost donationPurposePost);

	/**
	 * update DonationPurposePost Entity
	 *
	 * @param donationPurposePost
	 * @param requestDto
	 */
	@Mapping(target = "imageFileName", ignore = true)
	@Mapping(target = "imageFilePath", ignore = true)
	@Mapping(target = "bannerImageFilePath", ignore = true)
	@Mapping(target = "active", ignore = true)
	void updateDonationPurposePostEntity(@MappingTarget DonationPurposePost donationPurposePost,
			AddNewDonationPurposePostRequestDto requestDto);
}
