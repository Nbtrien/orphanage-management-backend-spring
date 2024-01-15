package com.graduatebackend.dto.donation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDonationMonthlyResponseDto implements Serializable {
    @JsonProperty("donor_name")
    private String donorName;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("donation_time")
    private Timestamp donationTime;
    @JsonProperty("donation_purpose")
    private String donationPurpose;
}
