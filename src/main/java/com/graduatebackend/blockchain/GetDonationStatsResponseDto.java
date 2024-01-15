package com.graduatebackend.blockchain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDonationStatsResponseDto implements Serializable {
    @JsonProperty("count")
    private Integer count;
    @JsonProperty("amount")
    private Long amount;
    @JsonProperty("used_amount")
    private Long usedAmount;
    @JsonProperty("remaining_amount")
    private Long remainingAmount;
}