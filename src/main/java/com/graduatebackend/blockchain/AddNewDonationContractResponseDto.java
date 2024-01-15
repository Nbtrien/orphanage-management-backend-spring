package com.graduatebackend.blockchain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewDonationContractResponseDto implements Serializable {
    @JsonProperty("donation_id")
    private Integer donationId;
    @JsonProperty("donation_token")
    private String donorToken;
    @JsonProperty("block_hash")
    private Integer blockHash;
    @JsonProperty("donation_hash")
    private String donationHash;
    @JsonProperty("transaction_hash")
    private String transactionHash;
}
