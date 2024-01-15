package com.graduatebackend.dto.articles;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetArticlePostResponseDto implements Serializable {
    @JsonProperty("article_id")
    private Integer articleId;
    @JsonProperty("article_title")
    private String articleTitle;
    @JsonProperty("article_slug")
    private String articleSlug;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("publication_start_date_time")
    private Timestamp publicationStartDateTime;
    @JsonProperty("publication_end_date_time")
    private Timestamp publicationEndDateTime;
    @JsonProperty("category_id")
    private Integer categoryId;
    @JsonProperty("category")
    private String categoryName;
    @JsonProperty("images")
    private List<String> images;
}
