package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appointment")
public class Appointment extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "APPOINTMENT_ID")
    private Integer appointmentId;
    @Basic
    @Column(name = "APPOINTMENT_STATUS")
    private int appointmentStatus;
    @Basic
    @Column(name = "APPOINTMENT_REASON")
    private String appointmentReason;
    @Basic
    @Column(name = "ATTENDEES")
    private int attendees;
    @Basic
    @Column(name = "ACCEPT_DATE_TIME")
    private Timestamp acceptDateTime;
    @Basic
    @Column(name = "APPOINTMENT_START_DATE_TIME")
    private Timestamp appointmentStartDateTime;
    @Basic
    @Column(name = "APPOINTMENT_END_DATE_TIME")
    private Timestamp appointmentEndDateTime;
    @Basic
    @Column(name = "DEADLINE_DATE_TIME")
    private Timestamp deadlineDateTime;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;
    @ManyToOne
    @JoinColumn(name = "ACCEPT_USER_ID")
    private User acceptUser;
}
