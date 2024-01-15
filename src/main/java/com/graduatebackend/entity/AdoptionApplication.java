package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "adoption_application")
public class AdoptionApplication extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADOPTION_APPLICATION_ID")
    private Integer adoptionApplicationId;
    @Basic
    @Column(name = "DATE_OF_APPLICATION")
    private Date dateOfApplication;
    @Basic
    @Column(name = "DEADLINE_DATE")
    private Date deadlineDate;
    @Basic
    @Column(name = "DATE_OF_PROCESSING")
    private Date dateOfProcessing;
    @Basic
    @Column(name = "REASON", columnDefinition = "text")
    private String reason;
    @Basic
    @Column(name = "APPLICATION_STATUS")
    private int applicationStatus;
    @Basic
    @Column(name = "IS_SENT_NOTIFICATION_EMAIL")
    private boolean isSentNotificationEmail;
    @Basic
    @Column(name = "IS_SENT_DETAILED_EMAIL")
    private boolean isSentDetailedEmail;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "APPLICANT_ID")
    private Applicant applicant;

    @ManyToOne
    @JoinColumn(name = "APPLICANT_SPOUSE_ID")
    private ApplicantSpouse spouse;

    @ManyToOne
    @JoinColumn(name = "MARITAL_STATUS_ID")
    private MaritalStatus maritalStatus;

    @ManyToOne
    @JoinColumn(name = "CHILDREN_ID")
    private Children children;

    @OneToOne(mappedBy = "application")
    private AdoptionApplicationDocument document;
}

