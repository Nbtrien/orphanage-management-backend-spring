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
@Table(name = "children_relative_relationship")
public class ChildrenRelativeRelationship extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CHILDREN_RELATIVE_RELATIONSHIP_ID")
    private Integer childrenRelativeRelationshipId;

    @Basic
    @Column(name = "RELATIONSHIP")
    private String relationship;

    @ManyToOne
    @JoinColumn(name = "CHILDREN_ID")
    private Children children;

    @ManyToOne
    @JoinColumn(name = "RELATIVE_ID")
    private Relative relative;


//    @Basic
//    @Column(name = "CHILDREN_ID")
//    private int childrenId;
//    @Basic
//    @Column(name = "RELATIVE_ID")
//    private int relativeId;

}
