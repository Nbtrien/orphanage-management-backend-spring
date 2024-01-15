package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee_payroll")
public class EmployeePayroll extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "EMPLOYEE_PAYROLL_ID")
    private Integer employeePayrollId;
    @Basic
    @Column(name = "PAYROLL_AMOUNT", columnDefinition = "decimal(10,0)")
    private Float payrollAmount;
    @Basic
    @Column(name = "SALARY", columnDefinition = "decimal(10,0)")
    private Float payrollSalary;
    @Basic
    @Column(name = "WORKING_HOURS")
    private Float workingHours;
    @Basic
    @Column(name = "EMPLOYEE_PAYROLL_MONTH")
    private int employeePayrollMonth;
    @Basic
    @Column(name = "EMPLOYEE_PAYROLL_YEAR")
    private int employeePayrollYear;
    @Basic
    @Column(name = "DATE_OF_PAYMENT")
    private Date dateOfPayment;
    @Basic
    @Column(name = "PAYROLL_START_DATE")
    private Date payrollStartDate;
    @Basic
    @Column(name = "PAYROLL_END_DATE")
    private Date payrollEndDate;
    @Basic
    @Column(name = "PAYROLL_STATUS")
    private int payrollStatus;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "SALARY_ID")
    private Salary salary;

    @ManyToOne
    @JoinColumn(name = "MONTHLY_PAYROLL_ID")
    private MonthlyPayroll monthlyPayroll;

//    @Basic
//    @Column(name = "EMPLOYEE_ID")
//    private int employeeId;
//    @Basic
//    @Column(name = "SALARY_ID")
//    private int salaryId;


    @Override
    public String toString() {
        return "EmployeePayroll{" +
                "employeePayrollId=" + employeePayrollId +
                ", payrollAmount=" + payrollAmount +
                ", payrollSalary=" + payrollSalary +
                ", workingHours=" + workingHours +
                ", employeePayrollMonth=" + employeePayrollMonth +
                ", employeePayrollYear=" + employeePayrollYear +
                ", dateOfPayment=" + dateOfPayment +
                ", payrollStartDate=" + payrollStartDate +
                ", payrollEndDate=" + payrollEndDate +
                ", payrollStatus=" + payrollStatus +
                '}';
    }
}
