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
@Entity
@Table(name = "adoption_history")
public class AdoptionHistory extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ADOPTION_HISTORY_ID")
    private Integer adoptionHistoryId;
    @Basic
    @Column(name = "ADOPTION_DATE")
    private Date adoptionDate;
    @Basic
    @Column(name = "ADOPTION_DESCRIPTION")
    private String adoptionDescription;
    @Basic
    @Column(name = "ADOPTION_DOCUMENT_FILE_PATH")
    private String documentFilePath;

    @OneToOne
    @JoinColumn(name = "CHILDREN_ID")
    private Children children;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "APPLICANT_ID")
    private Applicant applicant;

    @ManyToOne
    @JoinColumn(name = "APPLICANT_SPOUSE_ID")
    private ApplicantSpouse spouse;

    @ManyToOne
    @JoinColumn(name = "MARITAL_STATUS_ID")
    private MaritalStatus maritalStatus;

//    @OneToMany(mappedBy = "adoptionHistory")
//    private List<AdoptionDocument> adoptionDocuments;
}
