package com.graduatebackend.dto.adoption;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.address.AddNewAddressDto;
import com.graduatebackend.dto.image.AddNewImageDto;
import com.graduatebackend.entity.Address;
import com.graduatebackend.entity.MaritalStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewApplicantRequestDto implements Serializable {
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
    @Size(min = 10, max = 10, message = "Phone number must be 10 characters long")
    @Pattern(regexp = "\\d+", message = "Phone number must contain only digits")
    @JsonProperty("phone_number")
    private String applicantPhoneNumber;
    @Size(min = 12, max = 12, message = "Citizen ID must be 12 characters long")
    @Pattern(regexp = "\\d+", message = "Citizen ID must contain only digits")
    @JsonProperty("citizen_id_number")
    private String citizenIdNumber;
    @JsonProperty("front_image")
    private AddNewImageDto frontImage;
    @JsonProperty("back_image")
    private AddNewImageDto backImage;
    @JsonProperty("address")
    private AddNewAddressDto address;
    @JsonProperty("marital_status_id")
    private Integer maritalStatusId;
}
