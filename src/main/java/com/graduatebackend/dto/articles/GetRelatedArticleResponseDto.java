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
public class GetRelatedArticleResponseDto implements Serializable {
    @JsonProperty("article_id")
    private Integer articleId;
    @JsonProperty("article_title")
    private String articleTitle;
    @JsonProperty("article_slug")
    private String articleSlug;
    @JsonProperty("publication_start_date_time")
    private Timestamp publicationStartDateTime;
    @JsonProperty("image_url")
    private String imageUrl;
}
