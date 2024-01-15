package com.graduatebackend.dto.volunteer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetEventPostResponseDto implements Serializable {
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
    @JsonProperty("event_maximum_participant")
    private Integer eventMaximumParticipant;
    @JsonProperty("is_accepting")
    private boolean isAccepting;
    @JsonProperty("images")
    private List<String> images;
    @JsonProperty("is_applied")
    private boolean isApplied;
}
