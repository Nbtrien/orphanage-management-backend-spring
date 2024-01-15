package com.graduatebackend.dto.articles;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.image.AddNewImageDto;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewArticleDetailRequestDto implements Serializable {
    @JsonProperty("headline")
    private String headline;
    @JsonProperty("contents")
    private String contents;
    @JsonProperty("display_type")
    private int displayType;
    @JsonProperty("image")
    private AddNewImageDto image;
}
