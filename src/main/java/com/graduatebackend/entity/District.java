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
@Table(name = "district")
public class District extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "DISTRICT_ID")
    private String districtId;
    @Basic
    @Column(name = "DISTRICT_NAME")
    private String districtName;
    @Basic
    @Column(name = "DISTRICT_NAME_EN")
    private String districtNameEn;
    @Basic
    @Column(name = "DISTRICT_FULL_NAME")
    private String districtFullName;
    @Basic
    @Column(name = "DISTRICT_FULL_NAME_EN")
    private String districtFullNameEn;
    @Basic
    @Column(name = "DISTRICT_CODE_NAME")
    private String districtCodeName;

    @ManyToOne
    @JoinColumn(name = "ADMINISTRATIVE_UNIT_ID")
    private AdministrativeUnit administrativeUnit;

    @ManyToOne
    @JoinColumn(name = "PROVINCE_ID")
    private Province province;

    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Ward> wards;
}
