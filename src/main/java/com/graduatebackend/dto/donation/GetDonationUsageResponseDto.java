package com.graduatebackend.dto.donation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.family.GetFamilyForDonateResponseDto;
import com.graduatebackend.entity.Family;
import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDonationUsageResponseDto implements Serializable {
    @JsonProperty("donation_usage_id")
    private int donationUsageId;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("usage_time")
    private Timestamp usageTime;
    @JsonProperty("usage_note")
    private String usageNote;
    @JsonProperty("family")
    private GetFamilyForDonateResponseDto family;
    @JsonProperty("donation_id")
    private Integer donationId;
    @JsonProperty("donation_amount")
    private Double donationAmount;
    @JsonProperty("donation_time")
    private Timestamp donationTime;
    @JsonProperty("donor_id")
    private Integer donorId;
    @JsonProperty("donor_full_name")
    private String donorFullName;
}
