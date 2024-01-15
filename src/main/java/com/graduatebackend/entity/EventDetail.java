package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "event_detail")
public class EventDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVENT_DETAIL_ID")
    private Integer eventDetailId;
    @Basic
    @Column(name = "DISPLAY_ORDER", nullable = false)
    private Integer displayOrder;
    @Basic
    @Column(name = "HEADLINE", columnDefinition = "TEXT", nullable = false)
    private String headline;
    @Basic
    @Column(name = "CONTENTS", columnDefinition = "TEXT", nullable = false)
    private String contents;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "EVENT_IMAGE_ID")
    private EventImage image;

    @ManyToOne()
    @JoinColumn(name = "EVENT_ID")
    private Event event;
}
