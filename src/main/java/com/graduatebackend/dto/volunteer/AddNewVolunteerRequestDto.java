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
public class AddNewVolunteerRequestDto implements Serializable {
    @JsonProperty("event_id")
    private Integer eventId;
    @JsonProperty("first_name")
    private String applicantFirstName;
    @JsonProperty("last_name")
    private String applicantLastName;
    @JsonProperty("date_of_birth")
    private Date applicantDateOfBirth;
    @JsonProperty("gender")
    private int applicantGender;
    @JsonProperty("mail_address")
    private String applicantMailAddress;
    @JsonProperty("phone_number")
    private String applicantPhoneNumber;
    @JsonProperty("biography")
    private String biography;
}
