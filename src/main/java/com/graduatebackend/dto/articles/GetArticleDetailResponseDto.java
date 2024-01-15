package com.graduatebackend.dto.articles;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetArticleDetailResponseDto implements Serializable {
    @JsonProperty("headline")
    private String headline;
    @JsonProperty("contents")
    private String contents;
    @JsonProperty("display_type")
    private Integer displayType;
    @JsonProperty("image")
    private String image;
}
