package com.graduatebackend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
import com.graduatebackend.dto.volunteer.AddNewEventRequestDto;
import com.graduatebackend.dto.volunteer.AddNewVolunteerRequestDto;
import com.graduatebackend.dto.volunteer.GetEventByVolunteerResponseDto;
import com.graduatebackend.dto.volunteer.GetEventPostResponseDto;
import com.graduatebackend.dto.volunteer.GetEventResponseDto;
import com.graduatebackend.dto.volunteer.GetVolunteerApplyResponseDto;
import com.graduatebackend.dto.volunteer.GetVolunteerResponseDto;
import com.graduatebackend.dto.volunteer.MemberApplyEventRequestDto;
import com.graduatebackend.entity.Event;
import com.graduatebackend.service.DialogflowService;
import com.graduatebackend.service.VolunteerService;
import com.graduatebackend.utils.PaginationUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@CrossOrigin
@RequiredArgsConstructor
public class VolunteerController {
	private final VolunteerService volunteerService;
	private final DialogflowService dialogflowService;

	@GetMapping("/volunteers/events")
	public ResponseEntity<?> fetchEventPosts(@RequestParam(required = false) final Map<String, String> requestParams) {
		Integer accountId = requestParams.get("accountId") == null ? null
				: Integer.valueOf(requestParams.get("accountId"));
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		Page<GetEventPostResponseDto> page = volunteerService.getAllPost(accountId, pageRequest);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@GetMapping("/volunteers/events/{id}")
	public ResponseEntity<?> getEventPostDetail(@PathVariable("id") Integer id,
			@RequestParam(value = "accountId", required = false) final Integer accountId) {
		return ResponseEntity.ok(ResponseDto.ok(volunteerService.getEventPostDetail(id, accountId)));
	}

	@PostMapping("/volunteers/event-apply")
	public ResponseEntity<?> addNewVolunteer(@RequestBody AddNewVolunteerRequestDto requestDto) {
		volunteerService.addNewVolunteer(requestDto);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/member/volunteers/event-apply")
	public ResponseEntity<?> memberApplyEvent(@RequestBody MemberApplyEventRequestDto requestDto) {
		volunteerService.memberApplyEvent(requestDto);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/admin/events")
	public ResponseEntity<?> addNewEvent(@RequestBody AddNewEventRequestDto requestDto, Model model) {
		Event event = volunteerService.addNewEvent(requestDto);
		dialogflowService.createEventIntent(model, event);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/admin/events")
	public ResponseEntity<?> fetchEvents(@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		Integer eventState = requestParams.get("eventState") == null ? 0
				: Integer.parseInt(requestParams.get("eventState"));
		String keyword = requestParams.get("keyword");
		Page<GetEventResponseDto> page = volunteerService.fetchEvents(pageRequest, eventState, keyword);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@GetMapping("/admin/events/{id}")
	public ResponseEntity<?> getEventDetail(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(volunteerService.getEventDetail(id)));
	}

	@GetMapping("/admin/events/{id}/volunteers")
	public ResponseEntity<?> getVolunteersByEvent(@PathVariable("id") Integer id,
			@RequestParam(required = false) final Map<String, String> requestParams) {

		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		Integer applyStatus = requestParams.get("applicationStatus") == null ? null
				: Integer.parseInt(requestParams.get("applicationStatus"));
		Boolean memberStatus = requestParams.get("memberStatus") == null ? null
				: requestParams.get("memberStatus").equals("1");
		String keyword = requestParams.get("keyword");
		Page<GetVolunteerApplyResponseDto> page = volunteerService.getVolunteersByEvent(pageRequest, id, applyStatus,
				memberStatus, keyword);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@GetMapping("/admin/volunteers")
	public ResponseEntity<?> fetchVolunteers(@RequestParam(required = false) final Map<String, String> requestParams) {

		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		Boolean memberStatus = requestParams.get("memberStatus") == null ? null
				: requestParams.get("memberStatus").equals("1");
		String keyword = requestParams.get("keyword");
		Page<GetVolunteerResponseDto> page = volunteerService.fetchVolunteers(pageRequest, memberStatus, keyword);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@GetMapping("/admin/volunteers/{id}")
	public ResponseEntity<?> getVolunteerDetail(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(volunteerService.getVolunteerDetail(id)));
	}

	@GetMapping("/admin/volunteers/{id}/events")
	public ResponseEntity<?> getEventsByVolunteer(@PathVariable("id") Integer id,
			@RequestParam(required = false) final Map<String, String> requestParams) {

		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		Integer applyStatus = requestParams.get("applicationStatus") == null ? null
				: Integer.parseInt(requestParams.get("applicationStatus"));
		Integer eventState = requestParams.get("eventState") == null ? null
				: Integer.parseInt(requestParams.get("eventState"));
		String keyword = requestParams.get("keyword");
		Page<GetEventByVolunteerResponseDto> page = volunteerService.getEventsByVolunteer(pageRequest, id, eventState,
				applyStatus, keyword);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@PatchMapping("/admin/events/{id}/volunteers/approve")
	public ResponseEntity<?> approveVolunteerApplications(@PathVariable("id") Integer id,
			@RequestParam("ids") List<Integer> volunteersId) {
		volunteerService.approveVolunteerApplications(id, volunteersId);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/admin/events/{id}/volunteers/decline")
	public ResponseEntity<?> declineVolunteerApplications(@PathVariable("id") Integer id,
			@RequestParam("ids") List<Integer> volunteersId) {
		volunteerService.declineVolunteerApplications(id, volunteersId);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/admin/events/{id}/volunteers/not-attended")
	public ResponseEntity<?> notAttendVolunteerApplications(@PathVariable("id") Integer id,
			@RequestParam("ids") List<Integer> volunteersId) {
		volunteerService.notAttendedApplications(id, volunteersId);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/admin/volunteers/{id}/events/approve")
	public ResponseEntity<?> approveEventApplications(@PathVariable("id") Integer id,
			@RequestParam("ids") List<Integer> eventsId) {
		volunteerService.approveEventApplications(id, eventsId);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/admin/volunteers/{id}/events/decline")
	public ResponseEntity<?> declineEventApplications(@PathVariable("id") Integer id,
			@RequestParam("ids") List<Integer> eventsId) {
		volunteerService.declineEventApplications(id, eventsId);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/admin/volunteers/{volunteerId}/events/{eventId}/update-status")
	public ResponseEntity<?> notAttendEventApplications(@PathVariable("volunteerId") Integer volunteerId,
			@PathVariable("eventId") Integer eventId, @RequestParam("applicationStatus") Integer applicationStatus) {
		volunteerService.updateApplicationStatus(eventId, volunteerId, applicationStatus);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/admin/events/calendar")
	public ResponseEntity<?> getEventCalendar() {
		return ResponseEntity.ok(ResponseDto.ok(volunteerService.getEventCalendar()));
	}
}
