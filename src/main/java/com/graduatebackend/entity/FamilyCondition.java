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
@Table(name = "family_condition")
public class FamilyCondition extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "FAMILY_CONDITION_ID")
    private Integer familyConditionId;
    @Basic
    @Column(name = "AGE_FROM")
    private int ageFrom;
    @Basic
    @Column(name = "AGE_TO")
    private int ageTo;
    @Basic
    @Column(name = "MIN_NUMBER_OF_CHILDREN")
    private int minNumberOfChildren;
    @Basic
    @Column(name = "MAX_NUMBER_OF_CHILDREN")
    private int maxNumberOfChildren;

    @OneToMany(mappedBy = "condition", fetch = FetchType.LAZY)
    private List<Family> families;

}
