package com.graduatebackend.dto.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewImageDto implements Serializable {
    @NotBlank(message = "Please enter image file name")
    @JsonProperty("image_file_name")
    private String imageFileName;

    @NotBlank(message = "Please enter image file path")
    @JsonProperty("image_file_path")
    private String imageFilePath;
}
