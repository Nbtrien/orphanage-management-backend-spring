package com.graduatebackend.dto.donation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.family.GetFamilyForDonateResponseDto;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDonationResponseDto implements Serializable {
    @JsonProperty("donation_id")
    private Integer donationId;
    @JsonProperty("donor_id")
    private Integer donorId;
    @JsonProperty("donor_name")
    private String donorName;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("used_amount")
    private double usedAmount;
    @JsonProperty("remaining_amount")
    private double remainingAmount;
    @JsonProperty("donation_time")
    private Timestamp donationTime;
    @JsonProperty("purpose")
    private GetDonationPurposeResponseDto purpose;
    @JsonProperty("donation_message")
    private String donationMessage;
    @JsonProperty("pay_date")
    private Timestamp payDate;
    @JsonProperty("is_used")
    private boolean isUsed;
    @JsonProperty("is_sent_mail")
    private boolean isSentEmail;
    @JsonProperty("family")
    private GetFamilyForDonateResponseDto family;
}
