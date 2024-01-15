package com.graduatebackend.dto.children;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.address.AddNewAddressDto;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetRelativeResponseDto implements Serializable {
    @JsonProperty("relative_id")
    private String relativeId;

    @JsonProperty("first_name")
    private String relativeFirstName;

    @JsonProperty("last_name")
    private String relativeLastName;

    @JsonProperty("full_name")
    private String relativeFullName;

    @JsonProperty("date_of_birth")
    private Date relativeDateOfBirth;

    @JsonProperty("gender")
    private String relativeGender;

    @JsonProperty("phone_number")
    private String relativePhoneNumber;

    @JsonProperty("mail_address")
    private String relativeMailAddress;

    @JsonProperty("relationship")
    private String relationship;

    @JsonProperty("address")
    private String address;
}
