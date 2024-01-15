package com.graduatebackend.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.address.AddNewAddressDto;
import com.graduatebackend.dto.image.AddNewImageDto;
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
public class UpdateAccountRequestDto implements Serializable {
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

    @Size(min = 10, max = 10, message = "Phone number must be 10 characters long")
    @Pattern(regexp = "\\d+", message = "Phone number must contain only digits")
    @JsonProperty("phone_number")
    private String applicantPhoneNumber;

    @Size(min = 12, max = 12, message = "Citizen ID must be 12 characters long")
    @Pattern(regexp = "\\d+", message = "Citizen ID must contain only digits")
    @JsonProperty("citizen_id_number")
    private String citizenIdNumber;

    @JsonProperty("address")
    private AddNewAddressDto address;
}
