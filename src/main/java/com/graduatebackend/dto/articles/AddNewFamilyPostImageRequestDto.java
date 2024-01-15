package com.graduatebackend.dto.articles;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewFamilyPostImageRequestDto implements Serializable {
    @JsonProperty("image_file_name")
    private String imageFileName;
    @JsonProperty("image_file_path")
    private String imageFilePath;
    @JsonProperty("image_category")
    private int imageCategory;
}
