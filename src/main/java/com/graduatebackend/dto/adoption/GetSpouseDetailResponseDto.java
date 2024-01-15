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
public class GetSpouseDetailResponseDto implements Serializable {
    @JsonProperty("applicant_spouse_id")
    private Integer applicantSpouseId;
    @JsonProperty("spouse_full_name")
    private String spouseFullName;
    @JsonProperty("spouse_date_of_birth")
    private Date spouseDateOfBirth;
    @JsonProperty("spouse_gender")
    private String spouseGender;
    @JsonProperty("spouse_ethnicity")
    private String spouseEthnicity;
    @JsonProperty("spouse_nationality")
    private String spouseNationality;
    @JsonProperty("spouse_religion")
    private String spouseReligion;
    @JsonProperty("spouse_mail_address")
    private String spouseMailAddress;
    @JsonProperty("spouse_phone_number")
    private String spousePhoneNumber;
    @JsonProperty("address")
    private String address;
    @JsonProperty("citizen_id_number")
    private String citizenIdNumber;
}
