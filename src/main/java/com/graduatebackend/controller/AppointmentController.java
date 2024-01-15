package com.graduatebackend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graduatebackend.dto.ResponseDto;
import com.graduatebackend.dto.appointment.AddNewAppointmentRequestDto;
import com.graduatebackend.dto.appointment.GetAppointmentResponseDto;
import com.graduatebackend.service.AppointmentService;
import com.graduatebackend.utils.PaginationUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@CrossOrigin
@RequiredArgsConstructor
public class AppointmentController {
	private final AppointmentService appointmentService;

	@PostMapping("/member/{id}/appointments")
	public ResponseEntity<?> addNew(@PathVariable("id") Integer id,
			@RequestBody AddNewAppointmentRequestDto requestDto) {
		appointmentService.addNew(id, requestDto);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/member/{id}/appointments")
	public ResponseEntity<?> getByAccount(@PathVariable("id") Integer id,
			@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		Page<GetAppointmentResponseDto> page = appointmentService.getByAccountId(pageRequest, id);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@GetMapping("/admin/appointments")
	public ResponseEntity<?> fetch(@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		String keyword = requestParams.get("keyword");
		Integer status = requestParams.get("status") == null ? null : Integer.valueOf(requestParams.get("status"));
		String fromDate = requestParams.get("fromDate");
		String toDate = requestParams.get("toDate");
		Page<GetAppointmentResponseDto> page = appointmentService.fetch(pageRequest, keyword, status, fromDate, toDate);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@GetMapping("/admin/appointments/calendar")
	public ResponseEntity<?> fetch() {
		return ResponseEntity.ok(ResponseDto.ok(appointmentService.getAppointmentCalendar()));
	}

	@PatchMapping("/admin/appointments/approve")
	public ResponseEntity<?> approveAppointments(@RequestParam("ids") List<Integer> ids) {
		appointmentService.approveAppointments(ids);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/admin/appointments/decline")
	public ResponseEntity<?> declineAppointments(@RequestParam("ids") List<Integer> ids) {
		appointmentService.declineAppointments(ids);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/admin/appointments/update-status/{status}")
	public ResponseEntity<?> updateStatus(@RequestParam("ids") List<Integer> ids,
			@PathVariable("status") Integer status) {
		appointmentService.updateStatus(ids, status);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/admin/appointments/{id}/update-status/{status}")
	public ResponseEntity<?> updateStatus(@PathVariable("id") Integer id, @PathVariable("status") Integer status) {
		appointmentService.updateStatus(id, status);
		return ResponseEntity.noContent().build();
	}
}
