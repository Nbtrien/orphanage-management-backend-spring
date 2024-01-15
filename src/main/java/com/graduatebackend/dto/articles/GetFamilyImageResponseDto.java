package com.graduatebackend.dto.articles;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetFamilyImageResponseDto implements Serializable {
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("image_category")
    private int imageCategory;
}
