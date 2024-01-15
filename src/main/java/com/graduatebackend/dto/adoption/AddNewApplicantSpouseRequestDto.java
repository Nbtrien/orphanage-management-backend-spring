package com.graduatebackend.dto.adoption;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.address.AddNewAddressDto;
import com.graduatebackend.entity.Address;
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
public class AddNewApplicantSpouseRequestDto implements Serializable {
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
    @Size(min = 10, max = 10, message = "Phone number must be 10 characters long")
    @Pattern(regexp = "\\d+", message = "Phone number must contain only digits")
    @JsonProperty("phone_number")
    private String spousePhoneNumber;
    @JsonProperty("citizen_id_number")
    private String citizenIdNumber;
    @JsonProperty("address")
    private AddNewAddressDto address;
}
