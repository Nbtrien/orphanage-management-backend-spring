package com.graduatebackend.dto.donation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDonationPurposeResponseDto implements Serializable {
    @JsonProperty("donation_purpose_id")
    private Integer donationPurposeId;
    @JsonProperty("purpose")
    private String purpose;
    @JsonProperty("description")
    private String description;
}
