package com.graduatebackend.dto.adoption;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.models.auth.In;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAdoptionApplicationResponseDto implements Serializable {
    @JsonProperty("adoption_application_id")
    private Integer adoptionApplicationId;
    @JsonProperty("date_of_application")
    private Date dateOfApplication;
    @JsonProperty("application_status")
    private String applicationStatus;
    @JsonProperty("application_id")
    private Integer applicantId;
    @JsonProperty("applicant_full_name")
    private String applicantFullName;
    @JsonProperty("applicant_mail_address")
    private String applicantMailAddress;
}
