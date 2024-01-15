package com.graduatebackend.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAccountDetailResponseDto implements Serializable {
    @JsonProperty("first_name")
    private String applicantFirstName;
    @JsonProperty("last_name")
    private String applicantLastName;
    @JsonProperty("full_name")
    private String applicantFullName;
    @JsonProperty("date_of_birth")
    private Date applicantDateOfBirth;
    @JsonProperty("gender")
    private String applicantGender;
    @JsonProperty("ethnicity")
    private String applicantEthnicity;
    @JsonProperty("nationality")
    private String applicantNationality;
    @JsonProperty("religion")
    private String applicantReligion;
    @JsonProperty("phone_number")
    private String applicantPhoneNumber;
    @JsonProperty("mail_address")
    private String applicantMailAddress;
    @JsonProperty("address")
    private String address;
    @JsonProperty("register_time")
    private Timestamp registerDateTime;
    @JsonProperty("biography")
    private String biography;
}
