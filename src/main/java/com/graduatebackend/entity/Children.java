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
@Table(name = "children")
public class Children extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CHILDREN_ID")
    private Integer childrenId;
    @Basic
    @Column(name = "CHILDREN_FULL_NAME")
    private String childrenFullName;
    @Basic
    @Column(name = "CHILDREN_FIRST_NAME")
    private String childrenFirstName;
    @Basic
    @Column(name = "CHILDREN_LAST_NAME")
    private String childrenLastName;
    @Basic
    @Column(name = "CHILDREN_DATE_OF_BIRTH")
    private Date childrenDateOfBirth;
    @Basic
    @Column(name = "CHILDREN_GENDER")
    private int childrenGender;
    @Basic
    @Column(name = "DATE_OF_ADMISSION")
    private Date dateOfAdmission;
    @Basic
    @Column(name = "DATE_OF_DEPARTURE")
    private Date dateOfDeparture;
    @Basic
    @Column(name = "CHILDREN_ETHNICITY")
    private String childrenEthnicity;
    @Basic
    @Column(name = "CHILDREN_RELIGION")
    private String childrenReligion;
    @Basic
    @Column(name = "CHILDREN_CIRCUMSTANCE", columnDefinition = "text")
    private String childrenCircumstance;
    @Basic
    @Column(name = "IS_WAITING_ADOPTION")
    private boolean isWaitingAdoption;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "CHILDREN_ADDRESS_ID")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "CHILDREN_STATUS_ID")
    private ChildrenStatus childrenStatus;

    @ManyToOne
    @JoinColumn(name = "ORPHAN_TYPE_ID")
    private OrphanType orphanType;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "CHILDREN_IMAGE_ID")
    private Image image;

    @ManyToOne
    @JoinColumn(name = "CHILDREN_FAMILY_ID")
    private Family family;

    @OneToOne(mappedBy = "children")
    private AdoptionHistory adoptionHistory;

    @OneToMany(mappedBy = "children", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChildrenDocument> documents;

    @OneToMany(mappedBy = "children", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MedicalRecord> medicalRecords;

    @OneToMany(mappedBy = "children", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VaccinationRecord> vaccinationRecords;

    @OneToMany(mappedBy = "children", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChildrenStatusHistory> childrenStatusHistories;

    @OneToMany(mappedBy = "children", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChildrenRelativeRelationship> relativeRelationships;

    @OneToMany(mappedBy = "children", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChildrenFamilyHistory> familyHistories;

    @Override
    public String toString() {
        return "Children{" +
                "childrenId=" + childrenId +
                ", childrenFullName='" + childrenFullName + '\'' +
                ", childrenFirstName='" + childrenFirstName + '\'' +
                ", childrenLastName='" + childrenLastName + '\'' +
                ", childrenDateOfBirth=" + childrenDateOfBirth +
                ", childrenGender=" + childrenGender +
                ", dateOfAdmission=" + dateOfAdmission +
                ", dateOfDeparture=" + dateOfDeparture +
                ", childrenEthnicity='" + childrenEthnicity + '\'' +
                ", childrenReligion='" + childrenReligion + '\'' +
                ", childrenCircumstance='" + childrenCircumstance + '\'' +
                ", isWaitingAdoption=" + isWaitingAdoption +
                ", address=" + address +
                ", childrenStatus=" + childrenStatus +
                ", orphanType=" + orphanType +
                ", image=" + image +
                ", family=" + family +
                '}';
    }
}
