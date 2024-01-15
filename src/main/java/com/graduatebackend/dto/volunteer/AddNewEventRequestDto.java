package com.graduatebackend.dto.volunteer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.image.AddNewImageDto;
import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewEventRequestDto implements Serializable {
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
    @JsonProperty("image")
    private AddNewImageDto image;
    @JsonProperty("banner_image")
    private AddNewImageDto bannerImage;
    @JsonProperty("event_details")
    private List<AddNewEventDetailRequestDto> eventDetails;
}
