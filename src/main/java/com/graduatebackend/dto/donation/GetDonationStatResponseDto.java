package com.graduatebackend.dto.donation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDonationStatResponseDto implements Serializable {
    @JsonProperty("donation_count")
    private Integer donationCount;
    @JsonProperty("donor_count")
    private Integer donorCount;
    @JsonProperty("total_amount")
    private double totalAmount;
    @JsonProperty("used_amount")
    private double usedAmount;
}
