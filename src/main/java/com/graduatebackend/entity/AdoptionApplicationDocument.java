package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "adoption_application_document")
public class AdoptionApplicationDocument extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADOPTION_APPLICATION_DOCUMENT_ID")
    private Integer adoptionApplicationDocumentId;
    @Basic
    @Column(name = "APPLICATION_PDF_FILE_PATH")
    private String applicationPDFFilePath;
    @Basic
    @Column(name = "CHILDREN_PDF_FILE_PATH")
    private String childrenPDFFilePath;
    @OneToOne
    @JoinColumn(name = "ADOPTION_APPLICATION_ID")
    private AdoptionApplication application;
}

