package com.graduatebackend.dto.donation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountAddNewDonationRequestDto implements Serializable {
    @JsonProperty("donor_id")
    private Integer donorId;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("donation_purpose_id")
    private Integer donationPurposeId;
    @JsonProperty("family_id")
    private Integer familyId;
    @JsonProperty("url_return")
    private String urlReturn;
    @JsonProperty("message")
    private String donationMessage;
}
