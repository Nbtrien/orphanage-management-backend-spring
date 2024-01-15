package com.graduatebackend.dto.adoption;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.address.AddNewAddressDto;
import lombok.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAccountSpouseResponseDto implements Serializable {
    @JsonProperty("applicant_spouse_id")
    private Integer applicantSpouseId;
    @JsonProperty("first_name")
    private String spouseFirstName;
    @JsonProperty("last_name")
    private String spouseLastName;
    @JsonProperty("date_of_birth")
    private Date spouseDateOfBirth;
    @JsonProperty("gender")
    private String spouseGender;
    @JsonProperty("ethnicity")
    private String spouseEthnicity;
    @JsonProperty("nationality")
    private String spouseNationality;
    @JsonProperty("religion")
    private String spouseReligion;
    @JsonProperty("mail_address")
    private String spouseMailAddress;
    @JsonProperty("phone_number")
    private String spousePhoneNumber;
    @JsonProperty("citizen_id_number")
    private String citizenIdNumber;
    @JsonProperty("address")
    private AddNewAddressDto address;
}
