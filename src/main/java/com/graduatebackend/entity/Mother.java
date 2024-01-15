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
@Table(name = "mother")
@Entity
public class Mother extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "MOTHER_ID")
    private Integer motherId;
    @Basic
    @Column(name = "MOTHER_NAME")
    private String motherName;

    @OneToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

    @OneToOne
    @JoinColumn(name = "FAMILY_ID")
    private Family family;

    @OneToMany(mappedBy = "mother")
    private List<MotherFamilyHistory> motherFamilyHistories;
}
