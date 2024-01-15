package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "event_image")
public class EventImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVENT_IMAGE_ID")
    private Integer eventImageId;
    @Basic
    @Column(name = "IMAGE_FILE_NAME", nullable = false)
    private String imageFileName;
    @Basic
    @Column(name = "IMAGE_FILE_PATH", nullable = false)
    private String imageFilePath;

}
