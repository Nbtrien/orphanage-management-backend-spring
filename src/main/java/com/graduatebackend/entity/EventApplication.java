package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "event_application")
public class EventApplication extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVENT_APPLICATION_ID")
    private Long eventApplicationId;
    @Basic
    @Column(name = "APPLICATION_STATUS", nullable = false)
    private Integer applicationStatus = 0;

    @ManyToOne
    @JoinColumn(name = "VOLUNTEER_ID", nullable = false)
    private Volunteer volunteer;

    @ManyToOne
    @JoinColumn(name = "EVENT_ID", nullable = false)
    private Event event;
}