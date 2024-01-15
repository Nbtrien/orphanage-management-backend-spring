package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "relative")
public class Relative extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "RELATIVE_ID")
    private Integer relativeId;
    @Basic
    @Column(name = "RELATIVE_FULL_NAME")
    private String relativeFullName;
    @Basic
    @Column(name = "RELATIVE_FIRST_NAME")
    private String relativeFirstName;
    @Basic
    @Column(name = "RELATIVE_LAST_NAME")
    private String relativeLastName;
    @Basic
    @Column(name = "RELATIVE_DATE_OF_BIRTH")
    private Date relativeDateOfBirth;
    @Basic
    @Column(name = "RELATIVE_GENDER")
    private int relativeGender;
    @Basic
    @Column(name = "RELATIVE_PHONE_NUMBER", columnDefinition = "char(10)")
    private String relativePhoneNumber;
    @Basic
    @Column(name = "RELATIVE_MAIL_ADDRESS")
    private String relativeMailAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RELATIVE_ADDRESS_ID")
    private Address address;

    @OneToMany(mappedBy = "relative", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChildrenRelativeRelationship> relativeRelationships = new ArrayList<>();
//    @Basic
//    @Column(name = "RELATIVE_ADDRESS_ID")
//    private int relativeAddressId;

}
