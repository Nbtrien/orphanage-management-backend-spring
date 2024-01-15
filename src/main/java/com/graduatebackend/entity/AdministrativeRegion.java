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
@Table(name = "administrative_region")
public class AdministrativeRegion extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ADMINISTRATIVE_REGION_ID")
    private Integer administrativeRegionId;
    @Basic
    @Column(name = "REGION_NAME")
    private String regionName;
    @Basic
    @Column(name = "REGION_NAME_EN")
    private String regionNameEn;
    @Basic
    @Column(name = "REGION_CODE_NAME")
    private String regionCodeName;
    @Basic
    @Column(name = "REGION_CODE_NAME_EN")
    private String regionCodeNameEn;

    @OneToMany(mappedBy = "administrativeRegion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Province> provinces;
}
