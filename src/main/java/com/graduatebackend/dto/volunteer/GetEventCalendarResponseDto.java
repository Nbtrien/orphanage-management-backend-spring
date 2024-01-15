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
public class GetEventCalendarResponseDto implements Serializable {
    @JsonProperty("event_id")
    private Integer eventId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("event_start_date")
    private Timestamp eventStartDate;
    @JsonProperty("event_end_date")
    private Timestamp eventEndDate;
}
