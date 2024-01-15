package com.graduatebackend.dto.donation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.entity.Donor;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewDonationRequestDto implements Serializable {
    @JsonProperty("donor")
    private AddNewDonorRequestDto donor;
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

    @Override
    public String toString() {
        return "AddNewDonationRequestDto{" +
                "donor=" + donor +
                ", amount=" + amount +
                ", donationPurposeId=" + donationPurposeId +
                ", familyId=" + familyId +
                ", urlReturn='" + urlReturn + '\'' +
                ", donationMessage='" + donationMessage + '\'' +
                '}';
    }
}
