package com.graduatebackend.entity;


import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "monthly_payroll")
public class MonthlyPayroll extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "MONTHLY_PAYROLL_ID")
    private Integer monthlyPayrollId;
    @Basic
    @Column(name = "MONTH")
    private int month;
    @Basic
    @Column(name = "YEAR")
    private int year;
    @Basic
    @Column(name = "START_DATE")
    private Date startDate;
    @Basic
    @Column(name = "END_DATE")
    private Date endDate;
    @Basic
    @Column(name = "PAYROLL_STATUS")
    private int payrollStatus;
    @Basic
    @Column(name = "DATE_OF_PAYMENT")
    private Date dateOfPayment;

    @OneToMany(mappedBy = "monthlyPayroll", fetch = FetchType.LAZY)
    private List<EmployeePayroll> payrolls;
}
