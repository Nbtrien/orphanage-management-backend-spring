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
@Table(name = "vaccination_record")
public class VaccinationRecord extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "VACCINATION_RECORD_ID")
    private Integer vaccinationRecordId;
    @Basic
    @Column(name = "VACCINE_NAME")
    private String vaccineName;
    @Basic
    @Column(name = "VACCINE_TYPE")
    private String vaccineType;
    @Basic
    @Column(name = "VACCINATION_DATE")
    private Date vaccinationDate;
    @Basic
    @Column(name = "VACCINATION_LOCATION")
    private String vaccinationLocation;
    @Basic
    @Column(name = "VACCINATION_NOTES", columnDefinition = "text")
    private String vaccinationNotes;
    @Basic
    @Column(name = "VACCINATION_DOCUMENT_FILE_NAME")
    private String vaccinationDocumentFileName;
    @Basic
    @Column(name = "VACCINATION_DOCUMENT_FILE_PATH")
    private String vaccinationDocumentFilePath;

    @ManyToOne
    @JoinColumn(name = "CHILDREN_ID")
    private Children children;

//    @Basic
//    @Column(name = "CHILDREN_ID")
//    private int childrenId;
}
