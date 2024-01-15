package com.graduatebackend.dto.volunteer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetVolunteerResponseDto implements Serializable {
    @JsonProperty("volunteer_id")
    private Integer volunteerId;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("date_of_birth")
    private Date dateOfBirth;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("mail_address")
    private String mailAddress;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("biography")
    private String biography;
    @JsonProperty("is_member")
    private boolean isMember;
    @JsonProperty("application_count")
    private long applicationCount;
    @JsonProperty("approved_count")
    private long approvedCount;
    @JsonProperty("attended_count")
    private long attendedCount;
    @JsonProperty("not_attended_count")
    private long notAttendedCount;
}
