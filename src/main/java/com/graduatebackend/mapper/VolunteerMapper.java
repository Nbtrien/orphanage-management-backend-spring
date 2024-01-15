package com.graduatebackend.mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.ReportingPolicy.IGNORE;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.graduatebackend.dto.volunteer.AddNewEventRequestDto;
import com.graduatebackend.dto.volunteer.AddNewVolunteerRequestDto;
import com.graduatebackend.dto.volunteer.GetEventByVolunteerResponseDto;
import com.graduatebackend.dto.volunteer.GetEventCalendarResponseDto;
import com.graduatebackend.dto.volunteer.GetEventDetailResponseDto;
import com.graduatebackend.dto.volunteer.GetEventPostDetailResponseDto;
import com.graduatebackend.dto.volunteer.GetEventPostResponseDto;
import com.graduatebackend.dto.volunteer.GetEventResponseDto;
import com.graduatebackend.dto.volunteer.GetVolunteerApplyResponseDto;
import com.graduatebackend.dto.volunteer.GetVolunteerResponseDto;
import com.graduatebackend.entity.Event;
import com.graduatebackend.entity.EventDetail;
import com.graduatebackend.entity.Volunteer;

@Mapper(unmappedTargetPolicy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface VolunteerMapper {
	VolunteerMapper INSTANCE = Mappers.getMapper(VolunteerMapper.class);

	/**
	 * Convert AddNewEventRequestDto to Event entity
	 *
	 * @param requestDto
	 * @return
	 */
	Event toEventEntity(AddNewEventRequestDto requestDto);

	/**
	 * Convert Event entity to GetEventPostResponseDto
	 *
	 * @param event
	 * @return
	 */
	GetEventPostResponseDto toGetEventPostResponseDto(Event event);

	/**
	 * Convert EventDetail entity to GetEventDetailResponseDto
	 *
	 * @param eventDetail
	 * @return
	 */
	@Mapping(target = "image", source = "image.imageFilePath")
	@Named("toGetEventDetailResponseDto")
	GetEventDetailResponseDto toGetEventDetailResponseDto(EventDetail eventDetail);

	/**
	 * Convert EventDetail entity list to GetEventDetailResponseDto list
	 *
	 * @param eventDetails
	 * @return
	 */
	@IterableMapping(qualifiedByName = "toGetEventDetailResponseDto")
	@Named("toGetEventDetailResponseDtoList")
	List<GetEventDetailResponseDto> toGetEventDetailResponseDtoList(List<EventDetail> eventDetails);

	/**
	 * Convert Event Entity to GetEventPostDetailResponseDto
	 *
	 * @param event
	 * @return
	 */
	@Mapping(target = "eventDetails", expression = "java(toGetEventDetailResponseDtoList(event.getEventDetails()))")
	@Mapping(target = "image", source = "image.imageFilePath")
	@Mapping(target = "bannerImage", source = "bannerImage.imageFilePath")
	GetEventPostDetailResponseDto toGetEventPostDetailResponseDto(Event event);

	/**
	 * Convert AddNewVolunteerRequestDto to Volunteer entity
	 *
	 * @param requestDto
	 * @return
	 */
	@Mapping(target = "applicant.applicantMailAddress", source = "applicantMailAddress")
	@Mapping(target = "applicant.applicantFirstName", source = "applicantFirstName")
	@Mapping(target = "applicant.applicantLastName", source = "applicantLastName")
	@Mapping(target = "applicant.applicantDateOfBirth", source = "applicantDateOfBirth")
	@Mapping(target = "applicant.applicantPhoneNumber", source = "applicantPhoneNumber")
	@Mapping(target = "applicant.applicantGender", source = "applicantGender")
	Volunteer toVolunteerEntity(AddNewVolunteerRequestDto requestDto);

	/**
	 * Convert Event entity to GetEventResponseDto
	 *
	 * @param event
	 * @return
	 */
	GetEventResponseDto toGetEventResponseDto(Event event);

	/**
	 * Convert Event Entity to GetEventByVolunteerResponseDto
	 *
	 * @param event
	 * @return
	 */
	GetEventByVolunteerResponseDto toGetEventByVolunteerResponseDto(Event event);

	/**
	 * Convert Volunteer Entity to GetVolunteerApplyResponseDto
	 *
	 * @param volunteer
	 * @return
	 */
	@Mapping(source = "applicant.applicantMailAddress", target = "mailAddress")
	@Mapping(source = "applicant.applicantDateOfBirth", target = "dateOfBirth")
	@Mapping(source = "applicant.applicantPhoneNumber", target = "phoneNumber")
	@Mapping(source = "applicant.applicantGender", target = "gender")
	GetVolunteerApplyResponseDto toGetVolunteerApplyResponseDto(Volunteer volunteer);

	/**
	 * Convert Volunteer Entity to GetVolunteerResponseDto
	 *
	 * @param volunteer
	 * @return
	 */
	@Mapping(source = "applicant.applicantMailAddress", target = "mailAddress")
	@Mapping(source = "applicant.applicantDateOfBirth", target = "dateOfBirth")
	@Mapping(source = "applicant.applicantPhoneNumber", target = "phoneNumber")
	@Mapping(source = "applicant.applicantGender", target = "gender")
	GetVolunteerResponseDto toGetVolunteerResponseDto(Volunteer volunteer);

	/**
	 * Convert Event entity to GetEventCalendarResponseDto
	 *
	 * @param event
	 * @return
	 */
	GetEventCalendarResponseDto toGetEventCalendarResponseDto(Event event);
}
