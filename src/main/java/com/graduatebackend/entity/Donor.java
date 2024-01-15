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
@Table(name = "donor")
@Entity
public class Donor extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "DONOR_ID")
    private Integer donorId;
    @Basic
    @Column(name = "DONOR_FULL_NAME")
    private String donorFullName;
    @Basic
    @Column(name = "DONOR_FIRST_NAME")
    private String donorFirstName;
    @Basic
    @Column(name = "DONOR_LAST_NAME")
    private String donorLastName;
    @Basic
    @Column(name = "DONOR_DATE_OF_BIRTH")
    private Date donorDateOfBirth;
    @Basic
    @Column(name = "DONOR_GENDER")
    private Integer donorGender;
    @Basic
    @Column(name = "DONOR_MAIL_ADDRESS")
    private String donorMailAddress;
    @Basic
    @Column(name = "DONOR_PHONE_NUMBER")
    private String donorPhoneNumber;
    @Basic
    @Column(name = "DONOR_TOKEN")
    private String donorToken;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "DONOR_ADDRESS_ID")
    private Address address;

    @OneToMany(mappedBy = "donor")
    private List<Donation> donations;

    @OneToOne(mappedBy = "donor", fetch = FetchType.LAZY)
    private Account account;

}
