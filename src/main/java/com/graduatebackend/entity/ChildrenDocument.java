package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "children_document")
public class ChildrenDocument extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CHILDREN_DOCUMENT_ID")
    private Integer childrenDocumentId;
    @Basic
    @Column(name = "DOCUMENT_FILE_NAME")
    private String documentFileName;
    @Basic
    @Column(name = "DOCUMENT_FILE_PATH")
    private String documentFilePath;
    @Basic
    @Column(name = "DOCUMENT_DESCRIPTION")
    private String documentDescription;

    @ManyToOne
    @JoinColumn(name = "DOCUMENT_TYPE_ID")
    private DocumentType documentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHILDREN_ID")
    private Children children;

//    @Basic
//    @Column(name = "DOCUMENT_TYPE_ID")
//    private int documentTypeId;
//    @Basic
//    @Column(name = "CHILDREN_ID")
//    private int childrenId;
}
