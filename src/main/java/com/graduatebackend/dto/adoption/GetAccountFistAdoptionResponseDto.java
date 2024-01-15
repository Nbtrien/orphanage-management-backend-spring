package com.graduatebackend.dto.adoption;

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
public class GetAccountFistAdoptionResponseDto implements Serializable {
    @JsonProperty("first_name")
    private String applicantFirstName;
    @JsonProperty("last_name")
    private String applicantLastName;
    @JsonProperty("date_of_birth")
    private Date applicantDateOfBirth;
    @JsonProperty("gender")
    private int applicantGender;
    @JsonProperty("ethnicity")
    private String applicantEthnicity;
    @JsonProperty("nationality")
    private String applicantNationality;
    @JsonProperty("religion")
    private String applicantReligion;
    @JsonProperty("mail_address")
    private String applicantMailAddress;
    @JsonProperty("phone_number")
    private String applicantPhoneNumber;
    @JsonProperty("citizen_id_number")
    private String citizenIdNumber;
    @JsonProperty("address")
    private AddNewAddressDto address;
    @JsonProperty("marital_status_id")
    private Integer maritalStatusId;
    @JsonProperty("is_first_time")
    private boolean isFirstTime;
    @JsonProperty("spouse")
    private GetAccountSpouseResponseDto spouseResponseDto;
}
