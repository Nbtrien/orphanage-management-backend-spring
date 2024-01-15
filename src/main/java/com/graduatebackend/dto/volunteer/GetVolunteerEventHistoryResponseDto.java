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
public class GetVolunteerEventHistoryResponseDto implements Serializable {
    @JsonProperty("title")
    private String title;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("event_start_date")
    private Timestamp eventStartDate;
    @JsonProperty("event_end_date")
    private Timestamp eventEndDate;
    @JsonProperty("register_time")
    private Timestamp registerTime;
    @JsonProperty("event_status")
    private String eventStatus;
    @JsonProperty("apply_status")
    private String applyStatus;
    @JsonProperty("image")
    private String image;
}
