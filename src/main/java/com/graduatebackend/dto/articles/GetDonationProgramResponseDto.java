package com.graduatebackend.dto.articles;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.blockchain.GetDonationStatsResponseDto;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDonationProgramResponseDto implements Serializable {
    @JsonProperty("donation_purpose_id")
    private Integer donationPurposeId;
    @JsonProperty("purpose")
    private String purpose;
    @JsonProperty("description")
    private String description;
    @JsonProperty("stats")
    private GetDonationStatsResponseDto stats;
    @JsonProperty("post")
    private GetDonationPurposePostResponseDto post;
}
