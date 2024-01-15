package com.graduatebackend.dto.articles;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.models.auth.In;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetFamilyPostDetailResponseDto implements Serializable {
    @JsonProperty("family_id")
    private Integer familyId;
    @JsonProperty("family_name")
    private String familyName;
    @JsonProperty("no_of_children")
    private Long noOfChildren;
    @JsonProperty("range_age")
    private String rangeAge;
    @JsonProperty("donation_count")
    private Long donationCount;
    @JsonProperty("total_donation_amount")
    private Double totalDonationAmount;
    @JsonProperty("title")
    private String title;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("content")
    private String content;
    @JsonProperty("main_image_url")
    private String mainImageUrl;
    @JsonProperty("banner_image_url")
    private String bannerImageUrl;
    @JsonProperty("slide_images")
    private List<String> slideImage;
}
