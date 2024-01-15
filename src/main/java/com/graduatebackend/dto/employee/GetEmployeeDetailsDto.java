package com.graduatebackend.dto.employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetEmployeeDetailsDto implements Serializable {
    @JsonProperty("employee_id")
    private Integer employeeId;

    @JsonProperty("employee_first_name")
    private String employeeFirstName;

    @JsonProperty("employee_last_name")
    private String employeeLastName;

    @JsonProperty("employee_full_name")
    private String employeeFullName;

    @JsonProperty("employee_date_of_birth")
    private Date employeeDateOfBirth;

    @JsonProperty("employee_gender")
    private String employeeGender;

    @JsonProperty("employee_ethnicity")
    private String employeeEthnicity;

    @JsonProperty("employee_religion")
    private String employeeReligion;

    @JsonProperty("employee_phone_number")
    private String employeePhoneNumber;

    @JsonProperty("employee_mail_address")
    private String employeeMailAddress;

    @JsonProperty("position")
    private String position;

    @JsonProperty("hire_date")
    private Date hireDate;

    @JsonProperty("end_date")
    private Date endDate;

    @JsonProperty("salary")
    private String salary;

    @JsonProperty("address")
    private String address;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("payrolls")
    private List<GetEmployeePayrollResponseDto> payrolls;
}
