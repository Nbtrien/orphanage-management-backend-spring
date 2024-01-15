package com.graduatebackend.dto.articles;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetCategoryDetailResponseDto implements Serializable {
    @JsonProperty("article_category_id")
    private int articleCategoryId;
    @JsonProperty("category_name")
    private String categoryName;
    @JsonProperty("category_slug")
    private String categorySlug;
    @JsonProperty("no_of_articles")
    private long noOfArticles;
}
