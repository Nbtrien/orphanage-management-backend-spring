package com.graduatebackend.mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.ReportingPolicy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.graduatebackend.dto.address.AddNewAddressDto;
import com.graduatebackend.entity.Address;

@Mapper(unmappedTargetPolicy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface AddressMapper {
	AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

	/**
	 * Convert Address entity to String
	 *
	 * @param address
	 * @return
	 */
	@Named("toAddressString")
	default String toAddressString(Address address) {
		return address.getAddressDetail() + ", " + address.getWard().getWardFullName() + ", "
				+ address.getDistrict().getDistrictFullName() + ", " + address.getProvince().getProvinceFullName();
	}

	/**
	 * Convert Address entity to AddNewAddressDto
	 *
	 * @param address
	 * @return
	 */
	@Mapping(target = "provinceId", source = "province.provinceId")
	@Mapping(target = "districtId", source = "district.districtId")
	@Mapping(target = "wardId", source = "ward.wardId")
	@Named("toAddNewAddressDto")
	AddNewAddressDto toAddNewAddressDto(Address address);

	/**
	 * Convert AddNewAddressDto to Address entity
	 *
	 * @param addressDto
	 * @return
	 */
	@Mapping(target = "province.provinceId", source = "provinceId")
	@Mapping(target = "district.districtId", source = "districtId")
	@Mapping(target = "ward.wardId", source = "wardId")
	@Named("toEntity")
	Address toEntity(AddNewAddressDto addressDto);
}
