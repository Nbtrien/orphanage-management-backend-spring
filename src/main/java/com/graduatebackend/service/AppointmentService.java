package com.graduatebackend.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.graduatebackend.dto.appointment.AddNewAppointmentRequestDto;
import com.graduatebackend.dto.appointment.GetAppointmentCalendarResponseDto;
import com.graduatebackend.dto.appointment.GetAppointmentResponseDto;

public interface AppointmentService {
	/**
	 * add new appointment
	 *
	 * @param accountId
	 * @param requestDto
	 */
	void addNew(Integer accountId, AddNewAppointmentRequestDto requestDto);

	/**
	 * fetch
	 *
	 * @param pageRequest
	 * @param keyword
	 * @param appointmentStatus
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	Page<GetAppointmentResponseDto> fetch(PageRequest pageRequest, String keyword, Integer appointmentStatus,
			String fromDate, String toDate);

	/**
	 * get by account
	 *
	 * @param pageRequest
	 * @param accountId
	 * @return
	 */
	Page<GetAppointmentResponseDto> getByAccountId(PageRequest pageRequest, Integer accountId);

	/**
	 * get appointment calendar
	 *
	 * @return
	 */
	List<GetAppointmentCalendarResponseDto> getAppointmentCalendar();

	/**
	 * update status
	 *
	 * @param appointmentId
	 * @param status
	 */
	void updateStatus(Integer appointmentId, int status);

	/**
	 * update status
	 *
	 * @param ids
	 * @return
	 */
	void updateStatus(List<Integer> ids, int status);

	/**
	 * approve appointments
	 *
	 * @param ids
	 * @return
	 */
	CompletableFuture<Void> approveAppointments(List<Integer> ids);

	/**
	 * decline event applications
	 *
	 * @param ids
	 * @return
	 */
	CompletableFuture<Void> declineAppointments(List<Integer> ids);
}
