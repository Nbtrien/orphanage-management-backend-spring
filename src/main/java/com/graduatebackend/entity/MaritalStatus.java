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
@Table(name = "marital_status")
public class MaritalStatus extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "MARITAL_STATUS_ID")
    private Integer maritalStatusId;
    @Basic
    @Column(name = "MARITAL_STATUS_NAME")
    private String maritalStatusName;
    @Basic
    @Column(name = "MARITAL_STATUS_DESCRIPTION")
    private String maritalStatusDescription;

    @OneToMany(mappedBy = "maritalStatus")
    private List<Applicant> applicants;
}
