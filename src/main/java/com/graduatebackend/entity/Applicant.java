package com.graduatebackend.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "applicant")
public class Applicant extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "APPLICANT_ID")
    private Integer applicantId;
    @Basic
    @Column(name = "APPLICANT_FULL_NAME")
    private String applicantFullName;
    @Basic
    @Column(name = "APPLICANT_FIRST_NAME")
    private String applicantFirstName;
    @Basic
    @Column(name = "APPLICANT_LAST_NAME")
    private String applicantLastName;
    @Basic
    @Column(name = "APPLICANT_DATE_OF_BIRTH")
    private Date applicantDateOfBirth;
    @Basic
    @Column(name = "APPLICANT_GENDER")
    private Integer applicantGender;
    @Basic
    @Column(name = "APPLICANT_ETHNICITY")
    private String applicantEthnicity;
    @Basic
    @Column(name = "APPLICANT_NATIONALITY")
    private String applicantNationality;
    @Basic
    @Column(name = "APPLICANT_RELIGION")
    private String applicantReligion;
    @Basic
    @Column(name = "APPLICANT_MAIL_ADDRESS")
    private String applicantMailAddress;
    @Basic
    @Column(name = "APPLICANT_PHONE_NUMBER")
    private String applicantPhoneNumber;
    @Basic
    @Column(name = "CITIZEN_ID_NUMBER")
    private String citizenIdNumber;

    @Fetch(FetchMode.JOIN)
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "APPLICANT_ADDRESS_ID")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "MARITAL_STATUS_ID")
    private MaritalStatus maritalStatus;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "APPLICANT_SPOUSE_ID")
    private ApplicantSpouse spouse;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "CITIZEN_ID_FRONT_IMAGE_ID")
    private Image frontImage;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "CITIZEN_ID_BACK_IMAGE_ID")
    private Image backImage;

    @OneToOne(mappedBy = "applicant", cascade = CascadeType.ALL)
    private Account account;

    @OneToOne(mappedBy = "applicant")
    private Volunteer volunteer;

    @OneToMany(mappedBy = "applicant")
    private List<AdoptionApplication> adoptionApplications;

    @OneToMany(mappedBy = "applicant")
    private List<AdoptionHistory> adoptionHistories;

    @Override
    public String toString() {
        return "Applicant{" +
                "applicantId=" + applicantId +
                ", applicantFullName='" + applicantFullName + '\'' +
                ", applicantFirstName='" + applicantFirstName + '\'' +
                ", applicantLastName='" + applicantLastName + '\'' +
                ", applicantDateOfBirth=" + applicantDateOfBirth +
                ", applicantGender=" + applicantGender +
                ", applicantEthnicity='" + applicantEthnicity + '\'' +
                ", applicantNationality='" + applicantNationality + '\'' +
                ", applicantReligion='" + applicantReligion + '\'' +
                ", applicantMailAddress='" + applicantMailAddress + '\'' +
                ", applicantPhoneNumber='" + applicantPhoneNumber + '\'' +
                ", citizenIdNumber='" + citizenIdNumber + '\'' +
                '}';
    }
}
