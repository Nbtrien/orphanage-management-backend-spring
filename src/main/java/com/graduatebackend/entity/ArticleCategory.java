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
@Table(name = "article_category")
public class ArticleCategory extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ARTICLE_CATEGORY_ID")
    private Integer articleCategoryId;
    @Basic
    @Column(name = "ARTICLE_CATEGORY_NAME")
    private String categoryName;
    @Basic
    @Column(name = "ARTICLE_CATEGORY_SLUG")
    private String categorySlug;
    @Basic
    @Column(name = "CATEGORY_SUMMARY")
    private String categorySummary;
    @Basic
    @Column(name = "IMAGE_FILE_PATH")
    private String imageFilePath;
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private ArticleCategory parentCategory;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Article> articles;

}
