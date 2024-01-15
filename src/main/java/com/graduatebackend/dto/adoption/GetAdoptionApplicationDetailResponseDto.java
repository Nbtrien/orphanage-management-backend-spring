package com.graduatebackend.dto.adoption;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.entity.Applicant;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAdoptionApplicationDetailResponseDto implements Serializable {
    @JsonProperty("adoption_application_id")
    private Integer adoptionApplicationId;
    @JsonProperty("date_of_application")
    private Date dateOfApplication;
    @JsonProperty("deadline_date")
    private Date deadlineDate;
    @JsonProperty("date_of_processing")
    private Date dateOfProcessing;
    @JsonProperty("reason")
    private String reason;
    @JsonProperty("application_status")
    private String applicationStatus;
    @JsonProperty("is_sent_notification_email")
    private boolean isSentNotificationEmail;
    @JsonProperty("is_sent_detail_email")
    private boolean isSentDetailedEmail;
    @JsonProperty("applicant")
    private GetApplicantDetailResponseDto applicant;
}
