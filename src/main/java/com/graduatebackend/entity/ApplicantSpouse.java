package com.graduatebackend.entity;

import lombok.*;
import org.hibernate.mapping.ToOne;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "applicant_spouse")
public class ApplicantSpouse extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "APPLICANT_SPOUSE_ID")
    private Integer applicantSpouseId;
    @Basic
    @Column(name = "SPOUSE_FULL_NAME")
    private String spouseFullName;
    @Basic
    @Column(name = "SPOUSE_FIRST_NAME")
    private String spouseFirstName;
    @Basic
    @Column(name = "SPOUSE_LAST_NAME")
    private String spouseLastName;
    @Basic
    @Column(name = "SPOUSE_DATE_OF_BIRTH")
    private Date spouseDateOfBirth;
    @Basic
    @Column(name = "SPOUSE_GENDER")
    private Integer spouseGender;
    @Basic
    @Column(name = "SPOUSE_ETHNICITY")
    private String spouseEthnicity;
    @Basic
    @Column(name = "SPOUSE_NATIONALITY")
    private String spouseNationality;
    @Basic
    @Column(name = "SPOUSE_RELIGION")
    private String spouseReligion;
    @Basic
    @Column(name = "SPOUSE_MAIL_ADDRESS")
    private String spouseMailAddress;
    @Basic
    @Column(name = "SPOUSE_PHONE_NUMBER")
    private String spousePhoneNumber;
    @Basic
    @Column(name = "CITIZEN_ID_NUMBER")
    private String citizenIdNumber;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "SPOUSE_ADDRESS_ID")
    private Address address;

    @OneToOne(mappedBy = "spouse")
    private Applicant applicant;
}

