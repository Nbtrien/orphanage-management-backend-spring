package com.graduatebackend.dto.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SummaryStatisticsResponseDto implements Serializable {
    @JsonProperty("total_children")
    private long totalChildren;
    @JsonProperty("total_employees")
    private long totalEmployees;
    @JsonProperty("total_donations")
    private long totalDonations;
    @JsonProperty("total_donation_amount")
    private double totalDonationAmounts;

}
