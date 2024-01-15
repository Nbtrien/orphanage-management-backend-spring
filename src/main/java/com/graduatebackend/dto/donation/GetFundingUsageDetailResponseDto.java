package com.graduatebackend.dto.donation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.family.GetFamilyForDonateResponseDto;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetFundingUsageDetailResponseDto implements Serializable {
    @JsonProperty("funding_usage_id")
    private Integer fundingUsageId;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("usage_time")
    private Timestamp usageTime;
    @JsonProperty("usage_note")
    private String usageNote;
    @JsonProperty("purpose")
    private GetDonationPurposeResponseDto purpose;
    @JsonProperty("family")
    private GetFamilyForDonateResponseDto family;
    @JsonProperty("donation_usages")
    private List<GetDonationUsageResponseDto> donationUsages;
}
