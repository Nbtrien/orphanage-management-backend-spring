package com.graduatebackend.dto.family;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetFamilyDonationStatsResponseDto implements Serializable {
    @JsonProperty("family_id")
    private Integer familyId;
    @JsonProperty("family_name")
    private String familyName;
    @JsonProperty("donation_count")
    private Long donationCount;
    @JsonProperty("total_donation_amount")
    private Double totalDonationAmount;
    @JsonProperty("funding_amount")
    private Double fundingAmount;
}
