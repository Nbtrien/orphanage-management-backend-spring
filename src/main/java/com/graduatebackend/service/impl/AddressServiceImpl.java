package com.graduatebackend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.graduatebackend.dto.address.ProvinceDto;
import com.graduatebackend.entity.Address;
import com.graduatebackend.entity.Province;
import com.graduatebackend.mapper.ProvinceMapper;
import com.graduatebackend.repository.AddressRepository;
import com.graduatebackend.repository.ProvinceRepository;
import com.graduatebackend.service.AddressService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
	private final AddressRepository addressRepository;

	private final ProvinceRepository provinceRepository;

	@Override
	public Address getAddressDetail(int id) {
		return addressRepository.findById(id).orElseThrow(null);
	}

	@Override
	public List<ProvinceDto> fetchAllProvinces() {
		List<Province> provinces = provinceRepository.findAll();
		return ProvinceMapper.INSTANCE.toDtoList(provinces);
	}
}
