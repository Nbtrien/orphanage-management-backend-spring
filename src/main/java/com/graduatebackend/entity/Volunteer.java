package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "volunteer")
public class Volunteer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VOLUNTEER_ID")
    private Integer volunteerId;
    @Basic
    @Column(name = "BIOGRAPHY", columnDefinition = "TEXT")
    private String biography;
    @Basic
    @Column(name = "IS_MEMBER")
    private boolean isMember;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "APPLICANT_ID")
    private Applicant applicant;
    @OneToOne(mappedBy = "volunteer", fetch = FetchType.LAZY)
    private Account account;
    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<EventApplication> applications;
}