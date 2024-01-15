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
@Table(name = "ward")
public class Ward extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "WARD_ID")
    private String wardId;
    @Basic
    @Column(name = "WARD_NAME")
    private String wardName;
    @Basic
    @Column(name = "WARD_NAME_EN")
    private String wardNameEn;
    @Basic
    @Column(name = "WARD_FULL_NAME")
    private String wardFullName;
    @Basic
    @Column(name = "WARD_FULL_NAME_EN")
    private String wardFullNameEn;
    @Basic
    @Column(name = "WARD_CODE_NAME")
    private String wardCodeName;

    @ManyToOne
    @JoinColumn(name = "ADMINISTRATIVE_UNIT_ID")
    private AdministrativeUnit administrativeUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DISTRICT_ID")
    private District district;
}
