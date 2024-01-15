package com.graduatebackend.dto.adoption;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.entity.ApplicantSpouse;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetApplicantDetailResponseDto implements Serializable {
    @JsonProperty("applicant_id")
    private Integer applicantId;

    @JsonProperty("applicant_full_name")
    private String applicantFullName;

    @JsonProperty("applicant_date_of_birth")
    private Date applicantDateOfBirth;

    @JsonProperty("applicant_gender")
    private String applicantGender;

    @JsonProperty("applicant_ethnicity")
    private String applicantEthnicity;

    @JsonProperty("applicant_nationality")
    private String applicantNationality;

    @JsonProperty("applicant_religion")
    private String applicantReligion;

    @JsonProperty("applicant_mail_address")
    private String applicantMailAddress;

    @JsonProperty("applicant_phone_number")
    private String applicantPhoneNumber;

    @JsonProperty("citizen_id_number")
    private String citizenIdNumber;

    @JsonProperty("applicant_marital_status")
    private String maritalStatus;

    @JsonProperty("address")
    private String address;

    @JsonProperty("citizen_id_front_image_url")
    private String citizenIdFrontImageUrl;

    @JsonProperty("citizen_id_back_image_url")
    private String citizenIdBackImageUrl;

    @JsonProperty("spouse")
    private GetSpouseDetailResponseDto spouse;
}
