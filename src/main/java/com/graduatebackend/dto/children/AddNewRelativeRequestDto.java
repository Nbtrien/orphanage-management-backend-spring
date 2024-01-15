package com.graduatebackend.dto.children;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.address.AddNewAddressDto;
import com.graduatebackend.dto.image.AddNewImageDto;
import lombok.*;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewRelativeRequestDto implements Serializable {
    @NotBlank(message = "Please enter first name")
    @JsonProperty("first_name")
    private String relativeFirstName;

    @NotBlank(message = "Please enter last name")
    @JsonProperty("last_name")
    private String relativeLastName;

    @JsonProperty("date_of_birth")
    private Date relativeDateOfBirth;

    @Min(value = 0, message = "Please enter valid gender")
    @JsonProperty("gender")
    private int relativeGender;

    @Size(min = 10, max = 10, message = "Phone number must be 10 characters long")
    @Pattern(regexp = "\\d+", message = "Phone number must contain only digits")
    @JsonProperty("phone_number")
    private String relativePhoneNumber;

    @Pattern(regexp = "^(|\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b)$", message = "Invalid email address")
    @JsonProperty("mail_address")
    private String relativeMailAddress;

    @NotBlank(message = "Please enter relationship")
    @JsonProperty("relationship")
    private String relationship;

    @JsonProperty("address")
    private AddNewAddressDto address;
}
