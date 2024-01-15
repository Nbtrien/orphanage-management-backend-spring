package com.graduatebackend.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.graduatebackend.blockchain.BlockChainService;
import com.graduatebackend.blockchain.GetDonationStatsResponseDto;
import com.graduatebackend.constant.ChildrenCode;
import com.graduatebackend.dto.donation.GetDonorDetailResponseDto;
import com.graduatebackend.dto.donation.GetDonorListResponseDto;
import com.graduatebackend.dto.donation.GetDonorResponseDto;
import com.graduatebackend.entity.Donor;
import com.graduatebackend.exception.ResourceNotFoundException;
import com.graduatebackend.mapper.DonationMapper;
import com.graduatebackend.repository.DonorRepository;
import com.graduatebackend.service.DonorService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DonorServiceImpl implements DonorService {
	private final DonorRepository donorRepository;
	private final BlockChainService blockChainService;

	@Override
	public GetDonorResponseDto getDonorByToken(String token) {
		Donor donor = donorRepository.findByDonorTokenAndIsDeleteIsFalse(token)
				.orElseThrow(() -> new ResourceNotFoundException("Donor not found!"));
		return DonationMapper.INSTANCE.toGetDonorResponseDto(donor);
	}

	@Override
	public GetDonorDetailResponseDto getDetail(Integer id) {
		Donor donor = donorRepository.findByDonorIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Donor not found."));

		GetDonorDetailResponseDto donorDetailResponseDto = DonationMapper.INSTANCE.toGetDonorDetailResponseDto(donor);
		donorDetailResponseDto.setDonorGender(ChildrenCode.Gender.of(donor.getDonorGender()).getDisplay());
		donorDetailResponseDto.setStats(blockChainService.getDonorStats(donor.getDonorToken()));

		return donorDetailResponseDto;
	}

	@Override
	public Page<GetDonorListResponseDto> fetch(PageRequest pageRequest, String name, String email, String phoneNumber) {
		Page<Donor> donors = donorRepository.findAllDonors(pageRequest, name, email, phoneNumber);
		return donors.map(donor -> {
			GetDonorListResponseDto responseDto = DonationMapper.INSTANCE.toGetDonorListResponseDto(donor);
			GetDonationStatsResponseDto donationStats = blockChainService.getDonorStats(donor.getDonorToken());
			responseDto.setStats(donationStats);
			return responseDto;
		});
	}
}
