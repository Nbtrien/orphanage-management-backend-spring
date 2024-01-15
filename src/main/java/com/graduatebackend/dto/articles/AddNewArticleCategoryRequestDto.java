package com.graduatebackend.dto.articles;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.entity.ArticleCategory;
import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewArticleCategoryRequestDto implements Serializable {
    @JsonProperty("category_name")
    private String categoryName;
    @JsonProperty("category_slug")
    private String categorySlug;
    @JsonProperty("category_summary")
    private String categorySummary;
    @JsonProperty("image_file_path")
    private String imageFilePath;
    @JsonProperty("parent_id")
    private Integer parentCategoryId;
}
