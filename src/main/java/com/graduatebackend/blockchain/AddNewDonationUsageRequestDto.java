package com.graduatebackend.blockchain;

import lombok.*;

import java.io.Serializable;
import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AddNewDonationUsageRequestDto implements Serializable {
    private BigInteger donationId;
    private byte[] donationHash;
    private double amount;
    private Long usageTime;
    private BigInteger purposeId;
    private String purpose;
    private BigInteger familyId;
}
