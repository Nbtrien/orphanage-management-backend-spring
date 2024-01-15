package com.graduatebackend.dto.articles;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.donation.AddNewDonorRequestDto;
import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewDonationPurposePostRequestDto implements Serializable {
    @JsonProperty("title")
    private String title;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("content")
    private String content;
    @JsonProperty("image_file_name")
    private String imageFileName;
    @JsonProperty("image_file_path")
    private String imageFilePath;
    @JsonProperty("banner_image_file_path")
    private String bannerImageFilePath;
}
