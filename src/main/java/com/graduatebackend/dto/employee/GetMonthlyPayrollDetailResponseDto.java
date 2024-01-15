package com.graduatebackend.dto.employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.PageApiResponseDto;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetMonthlyPayrollDetailResponseDto implements Serializable {
    @JsonProperty("monthly_payroll_id")
    private Integer monthlyPayrollId;
    @JsonProperty("month")
    private int month;
    @JsonProperty("year")
    private int year;
    @JsonProperty("start_date")
    private Date startDate;
    @JsonProperty("end_date")
    private Date endDate;
    @JsonProperty("payroll_status")
    private String payrollStatus;
    @JsonProperty("date_of_payment")
    private Date dateOfPayment;
    @JsonProperty("employee_payrolls")
    private PageApiResponseDto<GetEmployeePayrollResponseDto> employeePayrolls;
}
