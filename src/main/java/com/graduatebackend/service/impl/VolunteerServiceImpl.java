package com.graduatebackend.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.graduatebackend.constant.ChildrenCode;
import com.graduatebackend.constant.VolunteerCode;
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
import com.graduatebackend.email.EmailUtilsService;
import com.graduatebackend.entity.Account;
import com.graduatebackend.entity.Event;
import com.graduatebackend.entity.EventApplication;
import com.graduatebackend.entity.EventDetail;
import com.graduatebackend.entity.Volunteer;
import com.graduatebackend.exception.ApplyEventException;
import com.graduatebackend.exception.NonVolunteerApplyException;
import com.graduatebackend.exception.ResourceNotFoundException;
import com.graduatebackend.mapper.VolunteerMapper;
import com.graduatebackend.repository.AccountRepository;
import com.graduatebackend.repository.EventApplicationRepository;
import com.graduatebackend.repository.EventRepository;
import com.graduatebackend.repository.VolunteerRepository;
import com.graduatebackend.service.FileService;
import com.graduatebackend.service.VolunteerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VolunteerServiceImpl implements VolunteerService {
	private final EventRepository eventRepository;
	private final FileService fileService;
	private final AccountRepository accountRepository;
	private final EventApplicationRepository eventApplicationRepository;
	private final VolunteerRepository volunteerRepository;
	private final EmailUtilsService emailUtilsService;

	@Override
	@Transactional
	public Event addNewEvent(AddNewEventRequestDto requestDto) {
		Event event = VolunteerMapper.INSTANCE.toEventEntity(requestDto);
		int i = 1;
		for (EventDetail eventDetail : event.getEventDetails()) {
			eventDetail.setDisplayOrder(i);
			i++;
		}
		event.prePersist();
		return eventRepository.save(event);
	}

	@Override
	public Page<GetEventPostResponseDto> getAllPost(Integer accountId, PageRequest pageRequest) {
		Page<Event> events = eventRepository.findAllEventPost(pageRequest);
		Account account = null;
		if (accountId != null)
			account = accountRepository.findByAccountIdAndIsDeleteIsFalse(accountId).orElse(null);
		Account finalAccount = account;
		Page<GetEventPostResponseDto> responseDtoPage = events.map(event -> {
			GetEventPostResponseDto eventPost = VolunteerMapper.INSTANCE.toGetEventPostResponseDto(event);
			// set application status if application over max participant
			Long volunteerCount = event.getApplications().stream()
					.filter(application -> application.getApplicationStatus()
							.equals(VolunteerCode.ApplicationStatus.APPROVED.getCode()) && !application.isDelete())
					.count();
			eventPost.setAccepting(event.getEventMaximumParticipant() > volunteerCount);
			// set image url from s3
			List<String> images = new ArrayList<>();
			images.add(fileService.generateFileUrl(event.getImage().getImageFilePath()));
			event.getEventDetails().forEach(
					eventDetail -> images.add(fileService.generateFileUrl(eventDetail.getImage().getImageFilePath())));
			eventPost.setImages(images);
			// check if member is applied event
			if (finalAccount != null && finalAccount.getVolunteer() != null) {
				long memberCount = event.getApplications().stream()
						.filter(application -> application.getVolunteer().equals(finalAccount.getVolunteer())).count();
				eventPost.setApplied(memberCount == 1);
			}
			return eventPost;
		});
		return responseDtoPage;
	}

	@Override
	public GetEventPostDetailResponseDto getEventPostDetail(Integer id, Integer accountId) {
		Event event = eventRepository.findByEventIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Event not found."));
		GetEventPostDetailResponseDto responseDto = VolunteerMapper.INSTANCE.toGetEventPostDetailResponseDto(event);
		Long volunteerCount = event.getApplications().stream().filter(application -> application.getApplicationStatus()
				.equals(VolunteerCode.ApplicationStatus.APPROVED.getCode()) && !application.isDelete()).count();
		responseDto.setAccepting(event.getEventMaximumParticipant() > volunteerCount);
		responseDto.setImage(fileService.generateFileUrl(responseDto.getImage()));
		responseDto.setBannerImage(fileService.generateFileUrl(responseDto.getBannerImage()));
		responseDto.getEventDetails()
				.forEach(eventDetail -> eventDetail.setImage(fileService.generateFileUrl(eventDetail.getImage())));

		Account account = accountRepository.findByAccountIdAndIsDeleteIsFalse(accountId).orElse(null);
		if (account != null && account.getVolunteer() != null) {
			long memberCount = event.getApplications().stream()
					.filter(application -> application.getVolunteer().equals(account.getVolunteer())).count();
			responseDto.setApplied(memberCount == 1);
		}
		return responseDto;
	}

	@Override
	public Page<GetEventResponseDto> fetchEvents(PageRequest pageRequest, int eventState, String keyword) {
		Page<Event> events = null;
		switch (eventState) {
		case 0:
			events = eventRepository.findAllEvent(pageRequest, keyword);
			break;
		case 1:
			events = eventRepository.findAllOccurredEvent(pageRequest, keyword);
			break;
		case 2:
			events = eventRepository.findAllInProgressEvent(pageRequest, keyword);
			break;
		case 3:
			events = eventRepository.findAllNotOccurredEvent(pageRequest, keyword);
			break;
		}
		if (events == null)
			throw new ApplyEventException("events not found.");
		return events.map(this::convertEventToGetEventResponseDto);
	}

	@Override
	public GetEventResponseDto getEventDetail(Integer id) {
		Event event = eventRepository.findByEventIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Event not found."));
		return convertEventToGetEventResponseDto(event);
	}

	@Override
	public Page<GetVolunteerApplyResponseDto> getVolunteersByEvent(PageRequest pageRequest, Integer id,
			Integer applicationStatus, Boolean isMember, String keyword) {
		Event event = eventRepository.findByEventIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Event not found."));
		Page<Volunteer> volunteers = volunteerRepository.findAllByEvent(pageRequest, event, applicationStatus, isMember,
				keyword);
		Page<GetVolunteerApplyResponseDto> responseDtoPage = volunteers.map(volunteer -> {
			GetVolunteerApplyResponseDto volunteerResponse = VolunteerMapper.INSTANCE
					.toGetVolunteerApplyResponseDto(volunteer);
			volunteerResponse.setFullName(volunteer.getApplicant().getApplicantLastName() + " "
					+ volunteer.getApplicant().getApplicantFirstName());
			EventApplication eventApplication = volunteer.getApplications().stream()
					.filter(application -> application.getEvent().equals(event)).findFirst().orElse(null);
			volunteerResponse
					.setGender(ChildrenCode.Gender.of(volunteer.getApplicant().getApplicantGender()).getDisplay());
			volunteerResponse
					.setApplicationStatus(eventApplication == null ? null : eventApplication.getApplicationStatus());
			volunteerResponse.setApplyStatus(
					VolunteerCode.ApplicationStatus.of(eventApplication.getApplicationStatus()).getDisplay());
			return volunteerResponse;
		});
		return responseDtoPage;
	}

	@Override
	public Page<GetEventByVolunteerResponseDto> getEventsByVolunteer(PageRequest pageRequest, Integer id,
			Integer eventState, Integer applicationStatus, String keyword) {
		Volunteer volunteer = volunteerRepository.findByVolunteerIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Volunteer not found."));
		Page<EventApplication> applications = eventApplicationRepository.findAllByVolunteer(pageRequest, volunteer,
				eventState, applicationStatus, keyword);

		Page<GetEventByVolunteerResponseDto> responseDtoPage = applications.map(application -> {
			GetEventByVolunteerResponseDto responseDto = VolunteerMapper.INSTANCE
					.toGetEventByVolunteerResponseDto(application.getEvent());
			// set event state
			VolunteerCode.EventState state = generateEventStateByEventDate(application.getEvent().getEventStartDate(),
					application.getEvent().getEventEndDate());
			responseDto.setEventState(state.getCode());
			responseDto.setEventStatus(state.getDisplay());
			// set application status
			responseDto.setApplyStatus(
					VolunteerCode.ApplicationStatus.of(application.getApplicationStatus()).getDisplay());
			responseDto.setApplicationStatus(application.getApplicationStatus());
			// set event count
			long applicationCount = application.getEvent().getApplications().stream().filter(app -> !app.isDelete())
					.count();
			responseDto.setApplicationCount(applicationCount);
			// count approved application
			long approvedCount = application.getEvent().getApplications().stream()
					.filter(app -> app.getApplicationStatus().equals(VolunteerCode.ApplicationStatus.APPROVED.getCode())
							&& !app.isDelete())
					.count();
			responseDto.setApprovedApplicationCount(approvedCount);

			return responseDto;
		});
		return responseDtoPage;
	}

	@Override
	public List<GetVolunteerEventHistoryResponseDto> getEventByAccount(Integer accountId) {
		Account account = accountRepository.findByAccountIdAndIsDeleteIsFalse(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found."));
		List<EventApplication> applications = eventApplicationRepository.findByAccount(account);
		List<GetVolunteerEventHistoryResponseDto> responseDtoList = applications.stream().map(application -> {
			GetVolunteerEventHistoryResponseDto responseDto = GetVolunteerEventHistoryResponseDto.builder()
					.registerTime(Timestamp.from(application.getCreateDateTime()))
					.eventStartDate(application.getEvent().getEventStartDate())
					.eventEndDate(application.getEvent().getEventEndDate()).title(application.getEvent().getTitle())
					.summary(application.getEvent().getSummary()).build();
			// set event state
			VolunteerCode.EventState state = generateEventStateByEventDate(application.getEvent().getEventStartDate(),
					application.getEvent().getEventEndDate());
			responseDto.setEventStatus(state.getDisplay());
			// set application status
			responseDto.setApplyStatus(
					VolunteerCode.ApplicationStatus.of(application.getApplicationStatus()).getDisplay());
			responseDto.setImage(fileService.generateFileUrl(application.getEvent().getImage().getImageFilePath()));
			return responseDto;
		}).collect(Collectors.toList());
		return responseDtoList;
	}

	@Override
	public List<GetEventCalendarResponseDto> getEventCalendar() {
		List<Event> events = eventRepository.getAllEvent();
		return events.stream().map(VolunteerMapper.INSTANCE::toGetEventCalendarResponseDto)
				.collect(Collectors.toList());
	}

	@Override
	public Page<GetVolunteerResponseDto> fetchVolunteers(PageRequest pageRequest, Boolean isMember, String keyword) {
		Page<Volunteer> volunteers = volunteerRepository.findAll(pageRequest, isMember, keyword);
		Page<GetVolunteerResponseDto> responseDtoPage = volunteers.map(volunteer -> {
			GetVolunteerResponseDto volunteerResponse = VolunteerMapper.INSTANCE.toGetVolunteerResponseDto(volunteer);
			volunteerResponse.setFullName(volunteer.getApplicant().getApplicantLastName() + " "
					+ volunteer.getApplicant().getApplicantFirstName());
			volunteerResponse
					.setGender(ChildrenCode.Gender.of(volunteer.getApplicant().getApplicantGender()).getDisplay());
			List<EventApplication> applications = volunteer.getApplications();
			if (applications != null) {
				volunteerResponse.setApplicationCount(
						applications.stream().filter(application -> !application.isDelete()).count());
				volunteerResponse.setAttendedCount(applications.stream()
						.filter(application -> application.getApplicationStatus()
								.equals(VolunteerCode.ApplicationStatus.ATTENDED.getCode()) && !application.isDelete())
						.count());
				volunteerResponse.setNotAttendedCount(applications.stream()
						.filter(application -> application.getApplicationStatus().equals(
								VolunteerCode.ApplicationStatus.NOT_ATTENDED.getCode()) && !application.isDelete())
						.count());
				volunteerResponse.setApprovedCount(applications.stream()
						.filter(application -> application.getApplicationStatus()
								.equals(VolunteerCode.ApplicationStatus.APPROVED.getCode()) && !application.isDelete())
						.count());
			}
			return volunteerResponse;
		});
		return responseDtoPage;
	}

	@Override
	public GetVolunteerResponseDto getVolunteerDetail(Integer id) {
		Volunteer volunteer = volunteerRepository.findByVolunteerIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Volunteer not found."));
		GetVolunteerResponseDto volunteerResponse = VolunteerMapper.INSTANCE.toGetVolunteerResponseDto(volunteer);
		volunteerResponse.setFullName(volunteer.getApplicant().getApplicantLastName() + " "
				+ volunteer.getApplicant().getApplicantFirstName());
		volunteerResponse.setGender(ChildrenCode.Gender.of(volunteer.getApplicant().getApplicantGender()).getDisplay());
		List<EventApplication> applications = volunteer.getApplications();
		if (applications != null) {
			volunteerResponse
					.setApplicationCount(applications.stream().filter(application -> !application.isDelete()).count());
			volunteerResponse.setAttendedCount(applications.stream()
					.filter(application -> application.getApplicationStatus()
							.equals(VolunteerCode.ApplicationStatus.ATTENDED.getCode()) && !application.isDelete())
					.count());
			volunteerResponse.setNotAttendedCount(applications.stream()
					.filter(application -> application.getApplicationStatus()
							.equals(VolunteerCode.ApplicationStatus.NOT_ATTENDED.getCode()) && !application.isDelete())
					.count());
			volunteerResponse.setApprovedCount(applications.stream()
					.filter(application -> application.getApplicationStatus()
							.equals(VolunteerCode.ApplicationStatus.APPROVED.getCode()) && !application.isDelete())
					.count());
		}
		return volunteerResponse;
	}

	@Override
	public void addNewVolunteer(AddNewVolunteerRequestDto requestDto) {
		Event event = eventRepository.findByEventIdAndIsDeleteIsFalse(requestDto.getEventId())
				.orElseThrow(() -> new ResourceNotFoundException("Event not found."));

		Volunteer volunteer = VolunteerMapper.INSTANCE.toVolunteerEntity(requestDto);
		volunteer.getApplicant().setApplicantFullName(volunteer.getApplicant().getApplicantLastName() + " "
				+ volunteer.getApplicant().getApplicantFirstName());
		volunteer.setMember(false);
		Volunteer volunteerSaved = volunteerRepository.saveAndFlush(volunteer);

		EventApplication eventApplication = EventApplication.builder().event(event).volunteer(volunteerSaved)
				.applicationStatus(VolunteerCode.ApplicationStatus.PENDING.getCode()).build();
		eventApplicationRepository.save(eventApplication);
	}

	@Override
	public void memberApplyEvent(MemberApplyEventRequestDto requestDto) {
		Account account = accountRepository.findByAccountIdAndIsDeleteIsFalse(requestDto.getAccountId())
				.orElseThrow(() -> new ResourceNotFoundException("Account not found."));
		Event event = eventRepository.findByEventIdAndIsDeleteIsFalse(requestDto.getEventId())
				.orElseThrow(() -> new ResourceNotFoundException("Event not found."));
		// check if member is applied
		long memberCount = event.getApplications().stream()
				.filter(application -> application.getVolunteer().equals(account.getVolunteer())).count();
		if (memberCount == 1)
			throw new ApplyEventException("439", "Already applied.");
		// check if account is volunteer
		if (account.getVolunteer() != null) {
			EventApplication eventApplication = EventApplication.builder().event(event)
					.volunteer(account.getVolunteer())
					.applicationStatus(VolunteerCode.ApplicationStatus.PENDING.getCode()).build();
			eventApplicationRepository.save(eventApplication);
		} else {
			if (requestDto.getBiography() == null)
				// throw exception if account is not volunteer and not send bio
				throw new NonVolunteerApplyException();
			else {
				// create new member
				Volunteer volunteer = Volunteer.builder().account(account).applicant(account.getApplicant())
						.biography(requestDto.getBiography()).isMember(true).build();
				Volunteer volunteerSaved = volunteerRepository.saveAndFlush(volunteer);
				account.setVolunteer(volunteerSaved);
				accountRepository.save(account);
				// save application
				EventApplication eventApplication = EventApplication.builder().event(event).volunteer(volunteerSaved)
						.applicationStatus(VolunteerCode.ApplicationStatus.PENDING.getCode()).build();
				eventApplicationRepository.save(eventApplication);
			}
		}
	}

	@Override
	@Async
	public CompletableFuture<Void> approveVolunteerApplications(Integer eventId, List<Integer> volunteersId) {
		Event event = eventRepository.findByEventIdAndIsDeleteIsFalse(eventId)
				.orElseThrow(() -> new ResourceNotFoundException("Event not found."));
		eventApplicationRepository.updateApplicationsStatus(event, volunteersId,
				VolunteerCode.ApplicationStatus.APPROVED.getCode());
		List<Volunteer> volunteers = event.getApplications().stream().map(EventApplication::getVolunteer)
				.filter(volunteer -> volunteersId.contains(volunteer.getVolunteerId())).collect(Collectors.toList());
		volunteers.forEach(volunteer -> emailUtilsService.sendApprovedVolunteerEmail(volunteer, event));
		return CompletableFuture.completedFuture(null);
	}

	@Override
	@Async
	public CompletableFuture<Void> declineVolunteerApplications(Integer eventId, List<Integer> volunteersId) {
		Event event = eventRepository.findByEventIdAndIsDeleteIsFalse(eventId)
				.orElseThrow(() -> new ResourceNotFoundException("Event not found."));
		eventApplicationRepository.updateApplicationsStatus(event, volunteersId,
				VolunteerCode.ApplicationStatus.DECLINED.getCode());
		List<Volunteer> volunteers = event.getApplications().stream().map(EventApplication::getVolunteer)
				.filter(volunteer -> volunteersId.contains(volunteer.getVolunteerId())).collect(Collectors.toList());
		volunteers.forEach(volunteer -> emailUtilsService.sendDeclinedVolunteerEmail(volunteer, event));
		return CompletableFuture.completedFuture(null);
	}

	@Async
	@Override
	public CompletableFuture<Void> approveEventApplications(Integer volunteerId, List<Integer> eventsId) {
		Volunteer volunteer = volunteerRepository.findByVolunteerIdAndIsDeleteIsFalse(volunteerId)
				.orElseThrow(() -> new ResourceNotFoundException("Volunteer not found."));
		eventApplicationRepository.updateApplicationsStatus(volunteer, eventsId,
				VolunteerCode.ApplicationStatus.APPROVED.getCode());
		List<Event> events = volunteer.getApplications().stream().map(EventApplication::getEvent)
				.filter(event -> eventsId.contains(event.getEventId())).collect(Collectors.toList());
		events.forEach(event -> emailUtilsService.sendApprovedVolunteerEmail(volunteer, event));
		return CompletableFuture.completedFuture(null);
	}

	@Async
	@Override
	public CompletableFuture<Void> declineEventApplications(Integer volunteerId, List<Integer> eventsId) {
		Volunteer volunteer = volunteerRepository.findByVolunteerIdAndIsDeleteIsFalse(volunteerId)
				.orElseThrow(() -> new ResourceNotFoundException("Volunteer not found."));
		eventApplicationRepository.updateApplicationsStatus(volunteer, eventsId,
				VolunteerCode.ApplicationStatus.DECLINED.getCode());
		List<Event> events = volunteer.getApplications().stream().map(EventApplication::getEvent)
				.filter(event -> eventsId.contains(event.getEventId())).collect(Collectors.toList());
		events.forEach(event -> emailUtilsService.sendDeclinedVolunteerEmail(volunteer, event));
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public void notAttendedApplications(Integer eventId, List<Integer> volunteersId) {
		Event event = eventRepository.findByEventIdAndIsDeleteIsFalse(eventId)
				.orElseThrow(() -> new ResourceNotFoundException("Event not found."));
		eventApplicationRepository.updateApplicationsStatus(event, volunteersId,
				VolunteerCode.ApplicationStatus.NOT_ATTENDED.getCode());

	}

	@Override
	public void updateApplicationStatus(Integer eventId, Integer volunteerId, Integer applicationStatus) {
		Event event = eventRepository.findByEventIdAndIsDeleteIsFalse(eventId)
				.orElseThrow(() -> new ResourceNotFoundException("Event not found."));
		Volunteer volunteer = volunteerRepository.findByVolunteerIdAndIsDeleteIsFalse(volunteerId)
				.orElseThrow(() -> new ResourceNotFoundException("Volunteer not found."));
		eventApplicationRepository.updateApplicationStatus(event, volunteer, applicationStatus);
	}

	private GetEventResponseDto convertEventToGetEventResponseDto(Event event) {
		GetEventResponseDto eventResponse = VolunteerMapper.INSTANCE.toGetEventResponseDto(event);
		// count application
		long applicationCount = event.getApplications().stream().filter(application -> !application.isDelete()).count();
		eventResponse.setApplicationCount(applicationCount);
		// count approved application
		long approvedCount = event.getApplications().stream().filter(application -> application.getApplicationStatus()
				.equals(VolunteerCode.ApplicationStatus.APPROVED.getCode()) && !application.isDelete()).count();
		eventResponse.setApprovedApplicationCount(approvedCount);
		// set event state
		VolunteerCode.EventState state = generateEventStateByEventDate(event.getEventStartDate(),
				event.getEventEndDate());
		eventResponse.setEventState(state.getCode());
		eventResponse.setEventStatus(state.getDisplay());

		// set publication state
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		if (event.getPublicationEndDateTime().before(currentTime))
			eventResponse.setPublicationState("Đã kết thúc đăng bài");
		else if (event.getPublicationStartDateTime().after(currentTime))
			eventResponse.setPublicationState("Đăng bài theo lịch");
		else if (currentTime.after(event.getPublicationStartDateTime())
				&& currentTime.before(event.getPublicationEndDateTime()))
			eventResponse.setPublicationState("Đang đăng bài");
		return eventResponse;
	}

	private VolunteerCode.EventState generateEventStateByEventDate(Timestamp eventStartDate, Timestamp eventEndDate) {
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		if (eventEndDate.before(currentTime))
			return VolunteerCode.EventState.OCCURRED;
		else if (eventStartDate.after(currentTime))
			return VolunteerCode.EventState.NOT_OCCURRED;
		else if (currentTime.after(eventStartDate) && currentTime.before(eventEndDate))
			return VolunteerCode.EventState.IN_PROGRESS;
		return null;
	}
}
