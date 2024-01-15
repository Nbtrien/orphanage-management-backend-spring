package com.graduatebackend.blockchain;

import lombok.*;

import java.io.Serializable;
import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewDonationContractRequestDto implements Serializable {
    private Integer donationId;
    private String donorToken;
    private String donorName;
    private double amount;
    private Long donationTime;
    private Integer purposeId;
    private String purpose;
    private Integer familyId;

    @Override
    public String toString() {
        return "AddNewDonationContractRequestDto{" +
                "donationId=" + donationId +
                ", donorToken='" + donorToken + '\'' +
                ", donorName='" + donorName + '\'' +
                ", amount=" + amount +
                ", donationTime=" + donationTime +
                ", purposeId=" + purposeId +
                ", purpose='" + purpose + '\'' +
                ", familyId=" + familyId +
                '}';
    }
}
