package com.graduatebackend.dto.articles;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetFamilyPostResponseDto implements Serializable {
    @JsonProperty("family_id")
    private Integer familyId;
    @JsonProperty("family_name")
    private String familyName;
    @JsonProperty("donation_count")
    private Long donationCount;
    @JsonProperty("total_donation_amount")
    private Double totalDonationAmount;
    @JsonProperty("title")
    private String title;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("image_url")
    private String imageUrl;
}
