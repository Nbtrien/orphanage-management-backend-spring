package com.graduatebackend.dto.articles;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.image.AddNewImageDto;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewArticleRequestDto implements Serializable {
    @JsonProperty("title")
    private String articleTitle;
    @JsonProperty("article_slug")
    private String articleSlug;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("publication_start_date_time")
    private Timestamp publicationStartDateTime;
    @JsonProperty("publication_end_date_time")
    private Timestamp publicationEndDateTime;
    @JsonProperty("image")
    private AddNewImageDto image;
    @JsonProperty("banner_image")
    private AddNewImageDto bannerImage;
    @JsonProperty("category_id")
    private Integer categoryId;
    @JsonProperty("article_details")
    private List<AddNewArticleDetailRequestDto> articleDetails;
}
