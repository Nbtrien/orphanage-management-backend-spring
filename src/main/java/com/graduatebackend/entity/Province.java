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
@Table(name = "province")
public class Province extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "PROVINCE_ID")
    private String provinceId;
    @Basic
    @Column(name = "PROVINCE_NAME")
    private String provinceName;
    @Basic
    @Column(name = "PROVINCE_NAME_EN")
    private String provinceNameEn;
    @Basic
    @Column(name = "PROVINCE_FULL_NAME")
    private String provinceFullName;
    @Basic
    @Column(name = "PROVINCE_FULL_NAME_EN")
    private String provinceFullNameEn;
    @Basic
    @Column(name = "PROVINCE_CODE_NAME")
    private String provinceCodeName;

    @ManyToOne
    @JoinColumn(name = "ADMINISTRATIVE_UNIT_ID")
    private AdministrativeUnit administrativeUnit;

    @ManyToOne
    @JoinColumn(name = "ADMINISTRATIVE_REGION_ID")
    private AdministrativeRegion administrativeRegion;

    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<District> districts;
}
