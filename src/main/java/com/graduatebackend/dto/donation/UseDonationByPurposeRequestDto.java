package com.graduatebackend.dto.donation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UseDonationByPurposeRequestDto implements Serializable {
    @JsonProperty("purpose_id")
    private Integer purposeId;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("note")
    private String note;
    @JsonProperty("family_id")
    private Integer familyId;
}
