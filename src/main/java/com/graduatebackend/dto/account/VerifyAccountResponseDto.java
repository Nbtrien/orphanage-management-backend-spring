package com.graduatebackend.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerifyAccountResponseDto implements Serializable {
    @JsonProperty("mail_address")
    private String accountMailAddress;

    @JsonProperty("date_of_birth")
    private Date applicantDateOfBirth;

    @JsonProperty("first_name")
    private String applicantFirstName;

    @JsonProperty("last_name")
    private String applicantLastName;

    @JsonProperty("phone_number")
    private String applicantPhoneNumber;
}
