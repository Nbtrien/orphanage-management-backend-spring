package com.graduatebackend.service.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.graduatebackend.constant.AppointmentCode;
import com.graduatebackend.dto.appointment.AddNewAppointmentRequestDto;
import com.graduatebackend.dto.appointment.GetAppointmentCalendarResponseDto;
import com.graduatebackend.dto.appointment.GetAppointmentResponseDto;
import com.graduatebackend.email.EmailUtilsService;
import com.graduatebackend.entity.Account;
import com.graduatebackend.entity.Appointment;
import com.graduatebackend.exception.ResourceNotFoundException;
import com.graduatebackend.mapper.AppointmentMapper;
import com.graduatebackend.repository.AccountRepository;
import com.graduatebackend.repository.AppointmentRepository;
import com.graduatebackend.service.AppointmentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
	private final AppointmentRepository appointmentRepository;
	private final AccountRepository accountRepository;
	private final EmailUtilsService emailUtilsService;

	@Override
	public void addNew(Integer accountId, AddNewAppointmentRequestDto requestDto) {
		Account account = accountRepository.findByAccountIdAndIsDeleteIsFalse(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found."));

		Appointment appointment = AppointmentMapper.INSTANCE.toEntity(requestDto);
		appointment.setAppointmentStatus(AppointmentCode.AppointmentStatus.PENDING.getCode());
		appointment.setAccount(account);

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(appointment.getAppointmentStartDateTime().getTime());
		calendar.add(Calendar.HOUR_OF_DAY, -24);
		Timestamp deadline = new Timestamp(calendar.getTimeInMillis());
		appointment.setDeadlineDateTime(deadline);
		appointmentRepository.save(appointment);
	}

	@Override
	public Page<GetAppointmentResponseDto> fetch(PageRequest pageRequest, String keyword, Integer appointmentStatus,
			String fromDate, String toDate) {
		Date minDate = fromDate == null ? null : Date.valueOf(fromDate);
		Date maxDate = toDate == null ? null : Date.valueOf(toDate);
		Page<Appointment> appointments = appointmentRepository.findAll(pageRequest, keyword, appointmentStatus, minDate,
				maxDate);
		appointments.forEach(appointment -> {
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			if (appointment.getAppointmentStatus() == AppointmentCode.AppointmentStatus.PENDING.getCode()
					&& appointment.getDeadlineDateTime().before(currentTime)) {
				appointment.setAppointmentStatus(AppointmentCode.AppointmentStatus.EXPIRED.getCode());
				appointmentRepository.saveAndFlush(appointment);
			}
		});
		return appointments.map(appointment -> {
			GetAppointmentResponseDto responseDto = AppointmentMapper.INSTANCE.toGetAppointmentResponseDto(appointment);
			responseDto
					.setStatus(AppointmentCode.AppointmentStatus.of(appointment.getAppointmentStatus()).getDisplay());
			return responseDto;
		});
	}

	@Override
	public Page<GetAppointmentResponseDto> getByAccountId(PageRequest pageRequest, Integer accountId) {
		Account account = accountRepository.findByAccountIdAndIsDeleteIsFalse(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found."));
		Page<Appointment> appointments = appointmentRepository.findByAccount(pageRequest, account);
		appointments.forEach(appointment -> {
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			if (appointment.getAppointmentStatus() == AppointmentCode.AppointmentStatus.PENDING.getCode()
					&& appointment.getDeadlineDateTime().before(currentTime)) {
				appointment.setAppointmentStatus(AppointmentCode.AppointmentStatus.EXPIRED.getCode());
				appointmentRepository.saveAndFlush(appointment);
			}
		});
		return appointments.map(appointment -> {
			GetAppointmentResponseDto responseDto = AppointmentMapper.INSTANCE.toGetAppointmentResponseDto(appointment);
			responseDto
					.setStatus(AppointmentCode.AppointmentStatus.of(appointment.getAppointmentStatus()).getDisplay());
			return responseDto;
		});
	}

	@Override
	public List<GetAppointmentCalendarResponseDto> getAppointmentCalendar() {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTimeInMillis(now.getTime());
		startCalendar.add(Calendar.MONTH, -3);
		Timestamp startDate = new Timestamp(startCalendar.getTimeInMillis());
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTimeInMillis(now.getTime());
		endCalendar.add(Calendar.MONTH, 3);
		Timestamp endDate = new Timestamp(endCalendar.getTimeInMillis());

		List<Integer> status = new ArrayList<>();
		status.add(AppointmentCode.AppointmentStatus.PENDING.getCode());
		status.add(AppointmentCode.AppointmentStatus.APPROVED.getCode());
		status.add(AppointmentCode.AppointmentStatus.COMPLETED.getCode());

		List<Appointment> appointments = appointmentRepository.getAppointmentCalendar(startDate, endDate, status);

		return appointments.stream().map(AppointmentMapper.INSTANCE::toGetAppointmentCalendarResponseDto)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void updateStatus(Integer appointmentId, int status) {
		Appointment appointment = appointmentRepository.findByAppointmentIdAndIsDeleteIsFalse(appointmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Appointment not found."));
		appointment.setAppointmentStatus(status);
		appointmentRepository.save(appointment);
	}

	@Override
	public void updateStatus(List<Integer> ids, int status) {
		appointmentRepository.updateAppointmentsStatus(ids, status);
	}

	@Override
	@Async
	public CompletableFuture<Void> approveAppointments(List<Integer> ids) {
		appointmentRepository.updateAppointmentsStatus(ids, AppointmentCode.AppointmentStatus.APPROVED.getCode());
		List<Appointment> appointments = appointmentRepository.findByIds(ids);
		appointments.forEach(appointment -> emailUtilsService.sendApprovedAppointmentEmail(
				appointment.getAccount().getAccountMailAddress(),
				appointment.getAccount().getApplicant().getApplicantFullName(),
				appointment.getAppointmentStartDateTime(), appointment.getAppointmentEndDateTime(),
				appointment.getAttendees()));
		return CompletableFuture.completedFuture(null);
	}

	@Override
	@Async
	public CompletableFuture<Void> declineAppointments(List<Integer> ids) {
		appointmentRepository.updateAppointmentsStatus(ids, AppointmentCode.AppointmentStatus.DECLINED.getCode());
		List<Appointment> appointments = appointmentRepository.findByIds(ids);
		appointments.forEach(appointment -> emailUtilsService.sendDeclinedAppointmentEmail(
				appointment.getAccount().getAccountMailAddress(),
				appointment.getAccount().getApplicant().getApplicantFullName(),
				appointment.getAppointmentStartDateTime(), appointment.getAppointmentEndDateTime(),
				appointment.getAttendees()));
		return CompletableFuture.completedFuture(null);
	}
}
