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
public class GetDonationDetailResponseDto implements Serializable {
    @JsonProperty("donation_id")
    private Integer donationId;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("used_amount")
    private double usedAmount;
    @JsonProperty("remaining_amount")
    private double remainingAmount;
    @JsonProperty("donation_time")
    private Timestamp donationTime;
    @JsonProperty("bank_code")
    private String bankCode;
    @JsonProperty("bank_tran_no")
    private String bankTranNo;
    @JsonProperty("card_type")
    private String cardType;
    @JsonProperty("pay_date")
    private Timestamp payDate;
    @JsonProperty("transaction_no")
    private String transactionNo;
    @JsonProperty("donation_message")
    private String donationMessage;
    @JsonProperty("is_used")
    private boolean isUsed;
    @JsonProperty("is_sent_mail")
    private boolean isSentEmail;
    @JsonProperty("donor")
    private GetDonorListResponseDto donor;
    @JsonProperty("purpose")
    private GetDonationPurposeResponseDto purpose;
    @JsonProperty("family")
    private GetFamilyForDonateResponseDto family;
    @JsonProperty("donation_usages")
    private List<GetDonationUsageResponseDto> donationUsages;
}
