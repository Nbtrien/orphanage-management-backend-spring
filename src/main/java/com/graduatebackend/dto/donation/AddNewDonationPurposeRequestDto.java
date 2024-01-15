package com.graduatebackend.dto.donation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewDonationPurposeRequestDto implements Serializable {
    @JsonProperty("purpose")
    private String purpose;
    @JsonProperty("description")
    private String description;
    @JsonProperty("is_active")
    private Integer isActive;
}
