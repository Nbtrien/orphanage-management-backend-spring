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
@Table(name = "orphan_type")
public class OrphanType extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ORPHAN_TYPE_ID")
    private Integer orphanTypeId;
    @Basic
    @Column(name = "ORPHAN_TYPE_NAME")
    private String orphanTypeName;
    @Basic
    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "orphanType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Children> children;
}
