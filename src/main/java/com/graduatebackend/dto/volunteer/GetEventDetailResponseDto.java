package com.graduatebackend.dto.volunteer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.image.AddNewImageDto;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetEventDetailResponseDto implements Serializable {
    @JsonProperty("headline")
    private String headline;
    @JsonProperty("contents")
    private String contents;
    @JsonProperty("image")
    private String image;
}
