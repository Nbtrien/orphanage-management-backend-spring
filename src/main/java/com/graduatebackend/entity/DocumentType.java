package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "document_type")
public class DocumentType extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "DOCUMENT_TYPE_ID")
    private Integer documentTypeId;
    @Basic
    @Column(name = "DOCUMENT_TYPE_NAME")
    private String documentTypeName;
    @Basic
    @Column(name = "DOCUMENT_TYPE_DESCRIPTION")
    private String documentTypeDescription;

    @OneToMany(mappedBy = "documentType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChildrenDocument> childrenDocuments;
}
