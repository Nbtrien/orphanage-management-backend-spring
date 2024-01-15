package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "salary")
@Entity
public class Salary extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "SALARY_ID")
    private Integer salaryId;
    @Basic
    @Column(name = "SALARY_AMOUNT", columnDefinition = "decimal(10,0)")
    private float salaryAmount;
    @Basic
    @Column(name = "SALARY_START_DATE")
    private Date salaryStartDate;
    @Basic
    @Column(name = "SALARY_END_DATE")
    private Date salaryEndDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_POSITION_ID")
    private EmployeePosition position;

    @OneToMany(mappedBy = "salary", fetch = FetchType.LAZY)
    private List<EmployeePayroll> payrolls;
//    @Basic
//    @Column(name = "EMPLOYEE_POSITION_ID")
//    private int employeePositionId;
//    @Basic
//    @Column(name = "EMPLOYEE_ID")
//    private int employeeId;
}
