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
public class GetEventResponseDto implements Serializable {
    @JsonProperty("event_id")
    private Integer eventId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("event_start_date")
    private Timestamp eventStartDate;
    @JsonProperty("event_end_date")
    private Timestamp eventEndDate;
    @JsonProperty("publication_start_date_time")
    private Timestamp publicationStartDateTime;
    @JsonProperty("publication_end_date_time")
    private Timestamp publicationEndDateTime;
    @JsonProperty("event_maximum_participant")
    private Integer eventMaximumParticipant;
    @JsonProperty("application_count")
    private long applicationCount;
    @JsonProperty("approved_application_count")
    private long approvedApplicationCount;
    @JsonProperty("event_status")
    private String eventStatus;
    @JsonProperty("event_state")
    private int eventState;
    @JsonProperty("publication_state")
    private String publicationState;
}
