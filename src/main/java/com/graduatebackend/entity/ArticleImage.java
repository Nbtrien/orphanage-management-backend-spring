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
@Table(name = "article_image")
public class ArticleImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARTICLE_IMAGE_ID")
    private Integer articleImageId;
    @Basic
    @Column(name = "IMAGE_FILE_NAME", nullable = false)
    private String imageFileName;
    @Basic
    @Column(name = "IMAGE_FILE_PATH", nullable = false)
    private String imageFilePath;
}
