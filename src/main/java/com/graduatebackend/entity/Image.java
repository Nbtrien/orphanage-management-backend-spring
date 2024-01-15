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
@Table(name = "image")
public class Image extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "IMAGE_ID")
    private Integer imageId;
    @Basic
    @Column(name = "IMAGE_FILE_NAME")
    private String imageFileName;
    @Basic
    @Column(name = "IMAGE_FILE_PATH")
    private String imageFilePath;

    @OneToMany(mappedBy = "image", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Children> children;

    @OneToOne(mappedBy = "image", fetch = FetchType.LAZY)
    private Employee employee;
}
