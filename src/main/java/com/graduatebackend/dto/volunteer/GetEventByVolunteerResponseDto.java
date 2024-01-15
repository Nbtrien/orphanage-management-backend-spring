package com.graduatebackend.dto.volunteer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetEventByVolunteerResponseDto implements Serializable {
    @JsonProperty("event_id")
    private Integer eventId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("event_start_date")
    private Timestamp eventStartDate;
    @JsonProperty("event_end_date")
    private Timestamp eventEndDate;
    @JsonProperty("event_maximum_participant")
    private Integer eventMaximumParticipant;
    @JsonProperty("application_count")
    private long applicationCount;
    @JsonProperty("approved_application_count")
    private long approvedApplicationCount;
    @JsonProperty("event_state")
    private int eventState;
    @JsonProperty("event_status")
    private String eventStatus;
    @JsonProperty("apply_status")
    private String applyStatus;
    @JsonProperty("application_status")
    private Integer applicationStatus;
}
