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
@Table(name = "article_detail")
public class ArticleDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARTICLE_DETAIL_ID")
    private Integer articleDetailId;
    @Basic
    @Column(name = "DISPLAY_ORDER", nullable = false)
    private Integer displayOrder;
    @Basic
    @Column(name = "DISPLAY_TYPE", nullable = false)
    private int displayType;
    @Basic
    @Column(name = "HEADLINE", columnDefinition = "TEXT", nullable = false)
    private String headline;
    @Basic
    @Column(name = "CONTENTS", columnDefinition = "TEXT", nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "ARTICLE_ID")
    private Article article;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ARTICLE_IMAGE_ID")
    private ArticleImage image;
}
