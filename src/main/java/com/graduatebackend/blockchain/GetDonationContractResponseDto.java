package com.graduatebackend.blockchain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDonationContractResponseDto implements Serializable {
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

    @Override
    public String toString() {
        return "GetDonationContractResponseDto{" +
                "donationId=" + donationId +
                ", donorToken='" + donorToken + '\'' +
                ", donorName='" + donorName + '\'' +
                ", amount=" + amount +
                ", donationTime=" + donationTime +
                ", blockHash=" + blockHash +
                ", donationHash='" + donationHash + '\'' +
                ", familyId=" + familyId +
                ", purpose='" + purpose + '\'' +
                '}';
    }
}
