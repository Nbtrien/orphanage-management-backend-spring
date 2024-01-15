package com.graduatebackend.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.graduatebackend.dto.volunteer.AddNewEventRequestDto;
import com.graduatebackend.dto.volunteer.AddNewVolunteerRequestDto;
import com.graduatebackend.dto.volunteer.GetEventByVolunteerResponseDto;
import com.graduatebackend.dto.volunteer.GetEventCalendarResponseDto;
import com.graduatebackend.dto.volunteer.GetEventPostDetailResponseDto;
import com.graduatebackend.dto.volunteer.GetEventPostResponseDto;
import com.graduatebackend.dto.volunteer.GetEventResponseDto;
import com.graduatebackend.dto.volunteer.GetVolunteerApplyResponseDto;
import com.graduatebackend.dto.volunteer.GetVolunteerEventHistoryResponseDto;
import com.graduatebackend.dto.volunteer.GetVolunteerResponseDto;
import com.graduatebackend.dto.volunteer.MemberApplyEventRequestDto;
import com.graduatebackend.entity.Event;

public interface VolunteerService {
	/**
	 * add new event
	 *
	 * @param requestDto AddNewEventRequestDto
	 */
	Event addNewEvent(AddNewEventRequestDto requestDto);

	/**
	 * get all post with account id
	 *
	 * @param accountId
	 * @param pageRequest
	 * @return
	 */
	Page<GetEventPostResponseDto> getAllPost(Integer accountId, PageRequest pageRequest);

	/**
	 * get event post detail
	 *
	 * @param id
	 * @return
	 */
	GetEventPostDetailResponseDto getEventPostDetail(Integer id, Integer accountId);

	/**
	 * fetch events
	 *
	 * @param pageRequest
	 * @return
	 */
	Page<GetEventResponseDto> fetchEvents(PageRequest pageRequest, int eventState, String keyword);

	/**
	 * get event detail
	 *
	 * @param id
	 * @return
	 */
	GetEventResponseDto getEventDetail(Integer id);

	/**
	 * get volunteers by event id
	 *
	 * @param pageRequest
	 * @param id
	 * @return
	 */
	Page<GetVolunteerApplyResponseDto> getVolunteersByEvent(PageRequest pageRequest, Integer id,
			Integer applicationStatus, Boolean isMember, String keyword);

	/**
	 * get volunteers by event
	 *
	 * @param pageRequest
	 * @param id
	 * @param eventState
	 * @param applicationStatus
	 * @param keyword
	 * @return
	 */
	Page<GetEventByVolunteerResponseDto> getEventsByVolunteer(PageRequest pageRequest, Integer id, Integer eventState,
			Integer applicationStatus, String keyword);

	/**
	 * fetch volunteers
	 *
	 * @param pageRequest
	 * @param isMember
	 * @param keyword
	 * @return
	 */
	Page<GetVolunteerResponseDto> fetchVolunteers(PageRequest pageRequest, Boolean isMember, String keyword);

	/**
	 * get volunteer detail
	 *
	 * @param id
	 * @return
	 */
	GetVolunteerResponseDto getVolunteerDetail(Integer id);

	/**
	 * member apply event
	 *
	 * @param requestDto
	 */
	void addNewVolunteer(AddNewVolunteerRequestDto requestDto);

	/**
	 * member apply event
	 *
	 * @param requestDto
	 */
	void memberApplyEvent(MemberApplyEventRequestDto requestDto);

	/**
	 * approve applications
	 *
	 * @param eventId
	 * @param volunteersId
	 * @return
	 */
	CompletableFuture<Void> approveVolunteerApplications(Integer eventId, List<Integer> volunteersId);

	/**
	 * decline applications
	 *
	 * @param eventId
	 * @param volunteersId
	 * @return
	 */
	CompletableFuture<Void> declineVolunteerApplications(Integer eventId, List<Integer> volunteersId);

	/**
	 * confirm not attend
	 *
	 * @param eventId
	 * @param volunteersId
	 */
	void notAttendedApplications(Integer eventId, List<Integer> volunteersId);

	/**
	 * confirm not attended
	 *
	 * @param eventId
	 * @param volunteerId
	 */
	void updateApplicationStatus(Integer eventId, Integer volunteerId, Integer applicationStatus);

	/**
	 * approve event applications
	 *
	 * @param volunteerId
	 * @param eventsId
	 * @return
	 */
	CompletableFuture<Void> approveEventApplications(Integer volunteerId, List<Integer> eventsId);

	/**
	 * decline event applications
	 *
	 * @param volunteerId
	 * @param eventsId
	 * @return
	 */
	CompletableFuture<Void> declineEventApplications(Integer volunteerId, List<Integer> eventsId);

	/**
	 * get events by account
	 *
	 * @param accountId
	 * @return
	 */
	List<GetVolunteerEventHistoryResponseDto> getEventByAccount(Integer accountId);

	/**
	 * get event calendar
	 *
	 * @return
	 */
	List<GetEventCalendarResponseDto> getEventCalendar();
}
