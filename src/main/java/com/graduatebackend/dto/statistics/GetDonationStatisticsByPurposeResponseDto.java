package com.graduatebackend.dto.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDonationStatisticsByPurposeResponseDto implements Serializable {
    @JsonProperty("purpose")
    private String purpose;
    @JsonProperty("donation_count")
    private long donationCount;
    @JsonProperty("donation_amount")
    private double donationAmount;
}
