package com.graduatebackend.dto.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDonationStatisticsByMonthResponseDto implements Serializable {
    @JsonProperty("month")
    private int month;
    @JsonProperty("donation_count")
    private long donationCount;
    @JsonProperty("donor_count")
    private long donorCount;
    @JsonProperty("donation_amount")
    private double donationAmount;
    @JsonProperty("usage_amount")
    private double usageAmount;
}
