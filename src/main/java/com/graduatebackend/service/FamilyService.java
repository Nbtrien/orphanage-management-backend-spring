package com.graduatebackend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.graduatebackend.dto.articles.AddNewFamilyPostRequestDto;
import com.graduatebackend.dto.articles.GetFamilyPostDetailResponseDto;
import com.graduatebackend.dto.articles.GetFamilyPostResponseDto;
import com.graduatebackend.dto.articles.GetFamilyPostUpdateResponseDto;
import com.graduatebackend.dto.children.GetChildrenFamilyHistoryResponseDto;
import com.graduatebackend.dto.family.AddNewFamilyConditionRequestDto;
import com.graduatebackend.dto.family.AddNewFamilyRequestDto;
import com.graduatebackend.dto.family.GetFamilyConditionResponseDto;
import com.graduatebackend.dto.family.GetFamilyDonationDetailResponseDto;
import com.graduatebackend.dto.family.GetFamilyDonationStatsResponseDto;
import com.graduatebackend.dto.family.GetFamilyForDonateResponseDto;
import com.graduatebackend.dto.family.GetFamilyResponseDto;
import com.graduatebackend.dto.family.GetMotherResponseDto;
import com.graduatebackend.entity.FamilyPost;

public interface FamilyService {
	/**
	 * fetch all family condition
	 *
	 * @return
	 */
	List<GetFamilyConditionResponseDto> fetchAllFamilyConditions();

	/**
	 * fetch all mothers available
	 *
	 * @return
	 */
	List<GetMotherResponseDto> fetchAllMothersAvailable();

	/**
	 * fetch conditions
	 *
	 * @param pageRequest
	 * @return
	 */
	Page<GetFamilyConditionResponseDto> fetchConditions(PageRequest pageRequest);

	/**
	 * fetch all families
	 *
	 * @param pageRequest
	 * @return
	 */
	Page<GetFamilyResponseDto> fetchFamilies(PageRequest pageRequest);

	/**
	 * search families
	 *
	 * @param pageRequest
	 * @param keyword
	 * @param familyConditionId
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	Page<GetFamilyResponseDto> searchFamilies(PageRequest pageRequest, String keyword, Integer familyConditionId,
			String fromDate, String toDate);

	/**
	 * fetch all families suitable for children
	 *
	 * @param childrenId
	 * @return
	 */
	List<GetFamilyResponseDto> fetchFamiliesForChildren(Integer childrenId);

	/**
	 * fetch all name and id
	 *
	 * @return
	 */
	List<GetFamilyForDonateResponseDto> fetchAllFamiliesForDonate();

	/**
	 * get family details
	 *
	 * @param id
	 * @return
	 */
	GetFamilyResponseDto getDetail(Integer id);

	/**
	 * get families donation stats
	 *
	 * @param pageRequest
	 * @return
	 */
	Page<GetFamilyDonationStatsResponseDto> getFamilyDonationStats(PageRequest pageRequest, String keyword);

	/**
	 * get family with donation statistics detail
	 *
	 * @param id Integer
	 * @return GetFamilyDonationDetailResponseDto
	 */
	GetFamilyDonationDetailResponseDto getFamilyDonationStatsDetail(Integer id);

	/**
	 * get family update info
	 *
	 * @param familyId
	 * @return
	 */
	GetFamilyPostUpdateResponseDto getFamilyPostUpdate(Integer familyId);

	/**
	 * add new family condition
	 *
	 * @param requestDto
	 */
	void addNewFamilyCondition(AddNewFamilyConditionRequestDto requestDto);

	/**
	 * add new family
	 *
	 * @param requestDto
	 */
	void addNewFamily(AddNewFamilyRequestDto requestDto);

	/**
	 * add new family post
	 *
	 * @param requestDto
	 */
	FamilyPost addNewFamilyPost(Integer familyId, AddNewFamilyPostRequestDto requestDto);

	/**
	 * get all family post
	 *
	 * @return
	 */
	List<GetFamilyPostResponseDto> fetchFamilyPost();

	/**
	 * get family post detail
	 *
	 * @param id
	 * @return
	 */
	GetFamilyPostDetailResponseDto getFamilyPostDetail(Integer id);

	/**
	 * get children history
	 *
	 * @param id
	 * @return
	 */
	List<GetChildrenFamilyHistoryResponseDto> getChildrenFamilyHistory(Integer id);

	/**
	 * update mother for family
	 *
	 * @param familyId
	 * @param motherId
	 */
	void updateMotherForFamily(Integer familyId, Integer motherId);

	/**
	 * Delete families
	 *
	 * @param ids
	 */
	void delete(List<Integer> ids);

	/**
	 * Delete children
	 *
	 * @param ids
	 */
	void deleteChildren(Integer familyId, List<Integer> ids);

}
