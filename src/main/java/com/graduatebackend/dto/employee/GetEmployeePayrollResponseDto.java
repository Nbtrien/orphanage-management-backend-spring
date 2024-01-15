package com.graduatebackend.dto.employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.entity.Employee;
import com.graduatebackend.entity.Salary;
import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetEmployeePayrollResponseDto implements Serializable {
    @JsonProperty("employee_payroll_id")
    private Integer employeePayrollId;

    @JsonProperty("payroll_salary")
    private float payrollSalary;

    @JsonProperty("working_hours")
    private float workingHours;

    @JsonProperty("payroll_amount")
    private float payrollAmount;

    @JsonProperty("employee_payroll_month")
    private int employeePayrollMonth;

    @JsonProperty("employee_payroll_year")
    private int employeePayrollYear;

    @JsonProperty("date_of_payment")
    private Date dateOfPayment;

    @JsonProperty("payroll_start_date")
    private Date payrollStartDate;

    @JsonProperty("payroll_end_date")
    private Date payrollEndDate;

    @JsonProperty("payroll_status")
    private String payrollStatus;

    @JsonProperty("employee_id")
    private Integer employeeId;

    @JsonProperty("employee_full_name")
    private String employeeFullName;

    @JsonProperty("employee_mail_address")
    private String employeeMailAddress;
}
