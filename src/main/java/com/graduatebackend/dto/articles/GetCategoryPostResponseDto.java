package com.graduatebackend.dto.articles;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.PageApiResponseDto;
import lombok.*;
import org.springframework.data.domain.Page;

import javax.persistence.Basic;
import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetCategoryPostResponseDto {
    @JsonProperty("article_category_id")
    private int articleCategoryId;
    @JsonProperty("category_name")
    private String categoryName;
    @JsonProperty("category_slug")
    private String categorySlug;
    @JsonProperty("category_summary")
    private String categorySummary;
    @JsonProperty("image_url")
    private String imageFilePath;
    @JsonProperty("article_posts")
    private PageApiResponseDto<GetArticlePostResponseDto> articlePosts;
}
