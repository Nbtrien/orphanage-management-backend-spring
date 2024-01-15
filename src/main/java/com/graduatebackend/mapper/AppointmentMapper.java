package com.graduatebackend.mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.ReportingPolicy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.graduatebackend.dto.appointment.AddNewAppointmentRequestDto;
import com.graduatebackend.dto.appointment.GetAppointmentCalendarResponseDto;
import com.graduatebackend.dto.appointment.GetAppointmentResponseDto;
import com.graduatebackend.entity.Appointment;

@Mapper(unmappedTargetPolicy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface AppointmentMapper {
	AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);

	/**
	 * Convert AddNewAppointmentRequestDto to Appointment Entity
	 *
	 * @param requestDto
	 * @return
	 */
	Appointment toEntity(AddNewAppointmentRequestDto requestDto);

	/**
	 * Convert Appointment Entity to GetAppointmentResponseDto
	 *
	 * @param appointment
	 * @return
	 */
	@Mapping(source = "account.accountId", target = "accountId")
	@Mapping(source = "account.applicant.applicantMailAddress", target = "applicantMailAddress")
	@Mapping(source = "account.applicant.applicantPhoneNumber", target = "applicantPhoneNumber")
	@Mapping(source = "account.applicant.applicantFullName", target = "applicantFullName")
	@Mapping(target = "address", expression = "java(AddressMapper.INSTANCE.toAddressString(appointment.getAccount()"
			+ ".getApplicant().getAddress()))")
	GetAppointmentResponseDto toGetAppointmentResponseDto(Appointment appointment);

	/**
	 * Convert Appointment Entity to GetAppointmentCalendarResponseDto
	 *
	 * @param appointment
	 * @return
	 */
	@Mapping(source = "account.applicant.applicantFullName", target = "applicantFullName")
	GetAppointmentCalendarResponseDto toGetAppointmentCalendarResponseDto(Appointment appointment);
}
