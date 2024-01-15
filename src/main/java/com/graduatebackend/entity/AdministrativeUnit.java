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
@Table(name = "administrative_unit")
public class AdministrativeUnit extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ADMINISTRATIVE_UNIT_ID")
    private Integer administrativeUnitId;
    @Basic
    @Column(name = "UNIT_FULL_NAME")
    private String unitFullName;
    @Basic
    @Column(name = "UNIT_FULL_NAME_EN")
    private String unitFullNameEn;
    @Basic
    @Column(name = "UNIT_SHORT_NAME")
    private String unitShortName;
    @Basic
    @Column(name = "UNIT_SHORT_NAME_EN")
    private String unitShortNameEn;
    @Basic
    @Column(name = "UNIT_CODE_NAME")
    private String unitCodeName;
    @Basic
    @Column(name = "UNIT_CODE_NAME_EN")
    private String unitCodeNameEn;
}
