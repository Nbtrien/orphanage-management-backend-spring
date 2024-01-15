package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
@Entity
public class Employee extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "EMPLOYEE_ID")
    private Integer employeeId;
    @Basic
    @Column(name = "EMPLOYEE_FULL_NAME")
    private String employeeFullName;
    @Basic
    @Column(name = "EMPLOYEE_FIRST_NAME")
    private String employeeFirstName;
    @Basic
    @Column(name = "EMPLOYEE_LAST_NAME")
    private String employeeLastName;
    @Basic
    @Column(name = "EMPLOYEE_DATE_OF_BIRTH")
    private Date employeeDateOfBirth;
    @Basic
    @Column(name = "EMPLOYEE_GENDER")
    private int employeeGender;
    @Basic
    @Column(name = "EMPLOYEE_PHONE_NUMBER", columnDefinition = "char(10)")
    private String employeePhoneNumber;
    @Basic
    @Column(name = "EMPLOYEE_MAIL_ADDRESS")
    private String employeeMailAddress;
    @Basic
    @Column(name = "EMPLOYEE_ETHNICITY")
    private String employeeEthnicity;
    @Basic
    @Column(name = "EMPLOYEE_RELIGION")
    private String employeeReligion;
    @Basic
    @Column(name = "HIRE_DATE")
    private Date hireDate;
    @Basic
    @Column(name = "END_DATE")
    private Date endDate;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "EMPLOYEE_IMAGE_ID")
    private Image image;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "EMPLOYEE_ADDRESS_ID")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_POSITION_ID")
    private EmployeePosition position;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private Salary salary;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EmployeePayroll> payrolls;

    @OneToOne(mappedBy = "employee")
    private Mother mother;
}
