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
@Table(name = "medical_record")
public class MedicalRecord extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "MEDICAL_RECORD_ID")
    private Integer medicalRecordId;
    @Basic
    @Column(name = "DIAGNOSIS", columnDefinition = "text")
    private String diagnosis;
    @Basic
    @Column(name = "PRESCRIPTION", columnDefinition = "text")
    private String prescription;
    @Basic
    @Column(name = "MEDICAL_NOTES", columnDefinition = "text")
    private String medicalNotes;
    @Column(name = "VISIT_DATE")
    private Date visitDate;
    @Basic
    @Column(name = "MEDICAL_DOCUMENT_FILE_NAME")
    private String medicalDocumentFileName;
    @Basic
    @Column(name = "MEDICAL_DOCUMENT_FILE_PATH")
    private String medicalDocumentFilePath;
    @ManyToOne
    @JoinColumn(name = "CHILDREN_ID")
    private Children children;
}
