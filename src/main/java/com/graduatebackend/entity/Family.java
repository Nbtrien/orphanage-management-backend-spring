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
@Table(name = "family")
public class Family extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "FAMILY_ID")
    private Integer familyId;
    @Basic
    @Column(name = "FAMILY_NAME")
    private String familyName;
    @Basic
    @Column(name = "DATE_OF_FORMATION")
    private Date dateOfFormation;
    @Basic
    @Column(name = "DATE_OF_TERMINATION")
    private Date dateOfTermination;

    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Children> children;

    @OneToOne(mappedBy = "family", cascade = CascadeType.ALL)
    private Mother mother;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FAMILY_CONDITION_ID")
    private FamilyCondition condition;

    @OneToMany(mappedBy = "family")
    private List<ChildrenFamilyHistory> childrenFamilyHistories;

    @OneToMany(mappedBy = "family")
    private List<MotherFamilyHistory> motherFamilyHistories;

    @OneToMany(mappedBy = "family")
    private List<Donation> donations;

    @OneToMany(mappedBy = "family")
    private List<FundingUsage> fundingUsages;

    @OneToOne(mappedBy = "family")
    private FamilyPost post;

    @OneToOne
    @JoinColumn(name = "INTENT_ID")
    private DialogflowIntent intent;
}
