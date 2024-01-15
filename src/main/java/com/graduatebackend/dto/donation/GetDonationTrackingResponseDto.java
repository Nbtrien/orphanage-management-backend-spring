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
public class GetDonationTrackingResponseDto implements Serializable {
    @JsonProperty("donation_id")
    private Integer donationId;
    @JsonProperty("donation_token")
    private String donorToken;
    @JsonProperty("donor_name")
    private String donorName;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("donation_time")
    private Timestamp donationTime;
    @JsonProperty("block_hash")
    private Integer blockHash;
    @JsonProperty("donation_hash")
    private String donationHash;
    @JsonProperty("family_id")
    public Integer familyId;
    @JsonProperty("purpose")
    public String purpose;

    @JsonProperty("is_used")
    private boolean isUsed;
    @JsonProperty("used_amount")
    private double usedAmount;
    @JsonProperty("remaining_amount")
    private double remainingAmount;
    @JsonProperty("family")
    private GetFamilyForDonateResponseDto family;
    @JsonProperty("donation_usages")
    private List<GetDonationUsageResponseDto> donationUsages;
    @JsonProperty("donation_message")
    private String donationMessage;
}
