package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "event")
public class Event extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVENT_ID")
    private Integer eventId;
    @Basic
    @Column(name = "TITLE", nullable = false)
    private String title;
    @Basic
    @Column(name = "SUMMARY", columnDefinition = "TEXT", nullable = false)
    private String summary;
    @Basic
    @Column(name = "EVENT_START_DATE", nullable = false)
    private Timestamp eventStartDate;
    @Basic
    @Column(name = "EVENT_END_DATE", nullable = false)
    private Timestamp eventEndDate;
    @Basic
    @Column(name = "PUBLICATION_START_DATE_TIME", nullable = false)
    private Timestamp publicationStartDateTime;
    @Basic
    @Column(name = "PUBLICATION_END_DATE_TIME")
    private Timestamp publicationEndDateTime;
    @Basic
    @Column(name = "EVENT_MAXIMUM_PARTICIPANT")
    private Integer eventMaximumParticipant;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "EVENT_IMAGE_ID")
    private EventImage image;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "BANNER_IMAGE_ID")
    private EventImage bannerImage;

    @OneToOne
    @JoinColumn(name = "INTENT_ID")
    private DialogflowIntent intent;

    @OneToMany(mappedBy = "event", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<EventDetail> eventDetails;

    @OneToMany(mappedBy = "event", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<EventApplication> applications;

    public void prePersist() {
        for (EventDetail eventDetail : eventDetails) {
            if (eventDetail.getEvent() == null) {
                eventDetail.setEvent(this);
            }
        }
    }
}