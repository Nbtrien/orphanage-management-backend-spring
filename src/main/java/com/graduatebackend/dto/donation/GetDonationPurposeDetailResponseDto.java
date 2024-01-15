package com.graduatebackend.dto.donation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.blockchain.GetDonationStatsResponseDto;
import com.graduatebackend.dto.articles.GetDonationPurposePostResponseDto;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDonationPurposeDetailResponseDto implements Serializable {
    @JsonProperty("donation_purpose_id")
    private Integer donationPurposeId;
    @JsonProperty("purpose")
    private String purpose;
    @JsonProperty("description")
    private String description;
    @JsonProperty("stats")
    private GetDonationStatsResponseDto stats;
    @JsonProperty("is_posted")
    private boolean isPosted;
    @JsonProperty("is_active")
    private boolean isActive;
}
