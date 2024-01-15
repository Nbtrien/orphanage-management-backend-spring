package com.graduatebackend.dto.employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.address.AddNewAddressDto;
import com.graduatebackend.dto.image.AddNewImageDto;
import lombok.*;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewEmployeeRequestDto implements Serializable {
    @NotBlank(message = "Please enter first name")
    @JsonProperty("first_name")
    private String employeeFirstName;

    @NotBlank(message = "Please enter last name")
    @JsonProperty("last_name")
    private String employeeLastName;

    @JsonProperty("date_of_birth")
    private Date employeeDateOfBirth;

    @Min(value = 0, message = "Please enter valid gender")
    @JsonProperty("gender")
    private int employeeGender;

    @Size(min = 10, max = 10, message = "Phone number must be 10 characters long")
    @Pattern(regexp = "\\d+", message = "Phone number must contain only digits")
    @JsonProperty("phone_number")
    private String employeePhoneNumber;

    @Pattern(regexp = "^(|\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b)$", message = "Invalid email address")
    @JsonProperty("mail_address")
    private String employeeMailAddress;

    @NotBlank(message = "Please enter ethnicity")
    @JsonProperty("ethnicity")
    private String employeeEthnicity;

    @NotBlank(message = "Please enter religion")
    @JsonProperty("religion")
    private String employeeReligion;

    @JsonProperty("hire_date")
    private Date hireDate;

    @DecimalMin(value = "1000.0", message = "Salary must be at least 1000")
    @NotNull(message = "Please enter salary")
    @JsonProperty("salary")
    private Float salary;

    @NotNull(message = "Please choose position")
    @JsonProperty("position_id")
    private Integer positionId;

    @JsonProperty("address")
    private AddNewAddressDto address;

    @JsonProperty("image")
    private AddNewImageDto image;
}
