package com.graduatebackend.dto.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDonationStatisticsByFamilyResponseDto implements Serializable {
    @JsonProperty("family")
    private String family;
    @JsonProperty("donation_count")
    private long donationCount;
    @JsonProperty("donation_amount")
    private double donationAmount;
    @JsonProperty("usage_amount")
    private double usageAmount;
}
