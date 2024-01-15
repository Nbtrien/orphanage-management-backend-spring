package com.graduatebackend.service;

import java.util.List;

import com.graduatebackend.dto.address.ProvinceDto;
import com.graduatebackend.entity.Address;

public interface AddressService {

	Address getAddressDetail(int id);

	/**
	 * get all provinces
	 *
	 * @return
	 */
	List<ProvinceDto> fetchAllProvinces();
}
