package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ADDRESS_ID")
    private Integer addressId;
    @Basic
    @Column(name = "ADDRESS_DETAIL")
    private String addressDetail;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PROVINCE_ID")
    private Province province;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DISTRICT_ID")
    private District district;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "WARD_ID")
    private Ward ward;

//    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Children> children = new ArrayList<>();
//
//    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
//    private Relative relative;
//
//    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
//    private Employee employee;
//
//    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
//    private Applicant applicant;
//
//    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
//    private ApplicantSpouse applicantSpouse;
}
