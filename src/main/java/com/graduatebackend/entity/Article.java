package com.graduatebackend.entity;

import jnr.ffi.annotations.In;
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
@Table(name = "article")
@Entity
public class Article extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ARTICLE_ID")
    private Integer articleId;
    @Basic
    @Column(name = "ARTICLE_TITLE")
    private String articleTitle;
    @Basic
    @Column(name = "ARTICLE_SLUG")
    private String articleSlug;
    @Basic
    @Column(name = "PUBLICATION_START_DATE_TIME")
    private Timestamp publicationStartDateTime;
    @Basic
    @Column(name = "PUBLICATION_END_DATE_TIME")
    private Timestamp publicationEndDateTime;
    @Basic
    @Column(name = "ARTICLE_SUMMARY")
    private String summary;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ARTICLE_IMAGE_ID")
    private ArticleImage image;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "BANNER_IMAGE_ID")
    private ArticleImage bannerImage;

    @ManyToOne
    @JoinColumn(name = "ARTICLE_CATEGORY_ID")
    private ArticleCategory category;

    @OneToMany(mappedBy = "article", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<ArticleDetail> articleDetails;

    public void prePersist() {
        for (ArticleDetail articleDetail : articleDetails) {
            if (articleDetail.getArticle() == null) {
                articleDetail.setArticle(this);
            }
        }
    }


}
