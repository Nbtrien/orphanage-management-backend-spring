package com.graduatebackend.dto.employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.address.AddNewAddressDto;
import com.graduatebackend.dto.image.AddNewImageDto;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetEmployeeUpdateInfoResponseDto implements Serializable {
    @JsonProperty("employee_id")
    private Integer employeeId;
    @JsonProperty("first_name")
    private String employeeFirstName;
    @JsonProperty("last_name")
    private String employeeLastName;
    @JsonProperty("date_of_birth")
    private Date employeeDateOfBirth;
    @JsonProperty("gender")
    private int employeeGender;
    @JsonProperty("phone_number")
    private String employeePhoneNumber;
    @JsonProperty("mail_address")
    private String employeeMailAddress;
    @JsonProperty("ethnicity")
    private String employeeEthnicity;
    @JsonProperty("religion")
    private String employeeReligion;
    @JsonProperty("hire_date")
    private Date hireDate;
    @JsonProperty("end_date")
    private Date endDate;
    @JsonProperty("salary")
    private Float salary;
    @JsonProperty("position_id")
    private Integer positionId;
    @JsonProperty("address")
    private AddNewAddressDto address;
}
