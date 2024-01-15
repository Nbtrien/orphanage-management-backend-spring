package com.graduatebackend.mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.ReportingPolicy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.graduatebackend.dto.account.AddNewAccountRequestDto;
import com.graduatebackend.dto.account.GetAccountDetailResponseDto;
import com.graduatebackend.dto.account.VerifyAccountResponseDto;
import com.graduatebackend.entity.Account;

@Mapper(unmappedTargetPolicy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface AccountMapper {
	AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

	/**
	 * Convert AddNewAccountRequestDto to Account Entity
	 *
	 * @param requestDto
	 * @return
	 */
	@Mapping(target = "applicant.applicantMailAddress", source = "applicantMailAddress")
	@Mapping(target = "applicant.applicantFirstName", source = "applicantFirstName")
	@Mapping(target = "applicant.applicantLastName", source = "applicantLastName")
	@Mapping(target = "applicant.applicantDateOfBirth", source = "applicantDateOfBirth")
	@Mapping(target = "applicant.applicantPhoneNumber", source = "applicantPhoneNumber")
	Account toEntity(AddNewAccountRequestDto requestDto);

	/**
	 * Convert Account entity to VerifyAccountRequestDto
	 *
	 * @param account
	 * @return
	 */
	@Mapping(target = "applicantPhoneNumber", source = "applicant.applicantPhoneNumber")
	@Mapping(target = "applicantDateOfBirth", source = "applicant.applicantDateOfBirth")
	@Mapping(target = "applicantFirstName", source = "applicant.applicantFirstName")
	@Mapping(target = "applicantLastName", source = "applicant.applicantLastName")
	VerifyAccountResponseDto toVerifyAccountResponseDto(Account account);

	@Mapping(target = "applicantPhoneNumber", source = "applicant.applicantPhoneNumber")
	@Mapping(target = "applicantDateOfBirth", source = "applicant.applicantDateOfBirth")
	@Mapping(target = "applicantFirstName", source = "applicant.applicantFirstName")
	@Mapping(target = "applicantLastName", source = "applicant.applicantLastName")
	@Mapping(target = "applicantFullName", source = "applicant.applicantFullName")
	@Mapping(target = "applicantEthnicity", source = "applicant.applicantEthnicity")
	@Mapping(target = "applicantNationality", source = "applicant.applicantNationality")
	@Mapping(target = "applicantReligion", source = "applicant.applicantReligion")
	@Mapping(target = "applicantMailAddress", source = "accountMailAddress")
	@Mapping(target = "address", expression = "java(AddressMapper.INSTANCE.toAddressString(account"
			+ ".getApplicant().getAddress()))")
	GetAccountDetailResponseDto toGetAccountDetailResponseDto(Account account);
}
