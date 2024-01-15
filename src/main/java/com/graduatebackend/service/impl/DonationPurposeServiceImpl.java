package com.graduatebackend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.graduatebackend.blockchain.BlockChainService;
import com.graduatebackend.dto.articles.AddNewDonationPurposePostRequestDto;
import com.graduatebackend.dto.articles.GetDonationProgramResponseDto;
import com.graduatebackend.dto.articles.GetDonationPurposePostUpdateResponseDto;
import com.graduatebackend.dto.donation.AddNewDonationPurposeRequestDto;
import com.graduatebackend.dto.donation.GetDonationPurposeDetailResponseDto;
import com.graduatebackend.dto.donation.GetDonationPurposeResponseDto;
import com.graduatebackend.entity.DonationPurpose;
import com.graduatebackend.entity.DonationPurposePost;
import com.graduatebackend.exception.ResourceNotFoundException;
import com.graduatebackend.mapper.DonationMapper;
import com.graduatebackend.mapper.DonationPurposeMapper;
import com.graduatebackend.repository.DonationPurposePostRepository;
import com.graduatebackend.repository.DonationPurposeRepository;
import com.graduatebackend.service.DonationPurposeService;
import com.graduatebackend.service.FileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DonationPurposeServiceImpl implements DonationPurposeService {
	private final DonationPurposeRepository donationPurposeRepository;
	private final DonationPurposePostRepository donationPurposePostRepository;
	private final BlockChainService blockChainService;
	private final FileService fileService;

	@Override
	public List<GetDonationPurposeResponseDto> fetchAllDonationPurpose() {
		List<DonationPurpose> donationPurposes = donationPurposeRepository.findByIsDeleteIsFalse();
		return donationPurposes.stream().map(DonationMapper.INSTANCE::toGetDonationPurposeResponseDto)
				.collect(Collectors.toList());
	}

	@Override
	public DonationPurposePost addNewPost(Integer donationPurposeId, AddNewDonationPurposePostRequestDto requestDto) {
		DonationPurpose donationPurpose = donationPurposeRepository
				.findByDonationPurposeIdAndIsDeleteIsFalse(donationPurposeId)
				.orElseThrow(() -> new ResourceNotFoundException("Donation purpose not found."));
		if (donationPurpose.getPost() != null) {
			DonationPurposePost post = donationPurpose.getPost();
			DonationPurposeMapper.INSTANCE.updateDonationPurposePostEntity(post, requestDto);
			if (requestDto.getImageFileName() != null) {
				post.setImageFileName(requestDto.getImageFileName());
				post.setImageFilePath(requestDto.getImageFilePath());
			}
			if (requestDto.getBannerImageFilePath() != null) {
				post.setBannerImageFilePath(requestDto.getBannerImageFilePath());
			}
			return donationPurposePostRepository.save(post);
		} else {
			DonationPurposePost donationPurposePost = DonationPurposeMapper.INSTANCE.toDonationPurposePost(requestDto);
			donationPurposePost.setDonationPurpose(donationPurpose);
			donationPurposePost.setActive(true);
			return donationPurposePostRepository.save(donationPurposePost);
		}
	}

	@Override
	public Page<GetDonationPurposeDetailResponseDto> fetch(PageRequest pageRequest, Boolean isActive, String keyword) {
		Page<DonationPurpose> donationPurposes = donationPurposeRepository.findAll(pageRequest, keyword, isActive);
		Page<GetDonationPurposeDetailResponseDto> responseDtoPage = donationPurposes.map(donationPurpose -> {
			GetDonationPurposeDetailResponseDto responseDto = DonationPurposeMapper.INSTANCE
					.toGetDonationPurposeDetailResponseDto(donationPurpose);
			responseDto.setStats(blockChainService.getPurposeStats(donationPurpose.getDonationPurposeId()));
			responseDto.setPosted(donationPurpose.getPost() != null);
			return responseDto;
		});
		return responseDtoPage;
	}

	@Override
	public GetDonationPurposeDetailResponseDto getDetail(Integer id) {
		DonationPurpose donationPurpose = donationPurposeRepository.findByDonationPurposeIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Donation purpose not found."));
		return DonationPurposeMapper.INSTANCE.toGetDonationPurposeDetailResponseDto(donationPurpose);
	}

	@Override
	public List<GetDonationProgramResponseDto> fetchDonationPrograms() {
		List<DonationPurpose> donationPurposes = donationPurposeRepository.getDonationPrograms();
		List<GetDonationProgramResponseDto> responseDtoList = donationPurposes.stream().map(donationPurpose -> {
			GetDonationProgramResponseDto responseDto = DonationPurposeMapper.INSTANCE
					.toGetDonationProgramResponseDto(donationPurpose);
			responseDto.setStats(blockChainService.getPurposeStats(donationPurpose.getDonationPurposeId()));
			responseDto.getPost()
					.setImageUrl(fileService.generateFileUrl(donationPurpose.getPost().getImageFilePath()));
			return responseDto;
		}).collect(Collectors.toList());
		return responseDtoList;
	}

	@Override
	public GetDonationProgramResponseDto getDonationProgramDetail(Integer id) {
		DonationPurpose donationPurpose = donationPurposeRepository.findByDonationPurposeIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Donation purpose not found."));
		GetDonationProgramResponseDto responseDto = DonationPurposeMapper.INSTANCE
				.toGetDonationProgramResponseDto(donationPurpose);
		responseDto.setStats(blockChainService.getPurposeStats(donationPurpose.getDonationPurposeId()));
		responseDto.getPost().setImageUrl(fileService.generateFileUrl(donationPurpose.getPost().getImageFilePath()));
		responseDto.getPost()
				.setBannerImageUrl(fileService.generateFileUrl(donationPurpose.getPost().getBannerImageFilePath()));
		return responseDto;
	}

	@Override
	public GetDonationPurposePostUpdateResponseDto getDonationPurposePostUpdate(Integer donationPurposeId) {
		DonationPurpose donationPurpose = donationPurposeRepository
				.findByDonationPurposeIdAndIsDeleteIsFalse(donationPurposeId)
				.orElseThrow(() -> new ResourceNotFoundException("Donation purpose not found."));
		if (donationPurpose.getPost() != null) {
			return DonationPurposeMapper.INSTANCE.toGetDonationPurposePostUpdateResponseDto(donationPurpose.getPost());
		} else {
			return GetDonationPurposePostUpdateResponseDto.builder()
					.donationPurposeId(donationPurpose.getDonationPurposeId()).purpose(donationPurpose.getPurpose())
					.build();
		}
	}

	@Override
	public void addNewPurpose(AddNewDonationPurposeRequestDto requestDto) {
		DonationPurpose donationPurpose = DonationMapper.INSTANCE.toDonationPurposeEntity(requestDto);
		donationPurpose.setActive(requestDto.getIsActive() == 1);
		donationPurposeRepository.save(donationPurpose);
	}

	@Override
	public void updatePurposeStatus(Integer id) {
		DonationPurpose donationPurpose = donationPurposeRepository.findByDonationPurposeIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Donation purpose not found."));
		donationPurpose.setActive(!donationPurpose.isActive());
		donationPurposeRepository.save(donationPurpose);
	}
}
