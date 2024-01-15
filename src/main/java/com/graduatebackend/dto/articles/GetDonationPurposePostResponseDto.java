package com.graduatebackend.dto.articles;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.donation.GetDonationPurposeResponseDto;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDonationPurposePostResponseDto implements Serializable {
    @JsonProperty("donation_purpose_post_id")
    private Integer donationPurposePostId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("content")
    private String content;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("banner_image_url")
    private String bannerImageUrl;
}
