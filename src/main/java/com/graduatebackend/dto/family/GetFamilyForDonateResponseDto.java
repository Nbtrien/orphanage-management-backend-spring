package com.graduatebackend.dto.family;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetFamilyForDonateResponseDto implements Serializable {
    @JsonProperty("family_id")
    private Integer familyId;

    @JsonProperty("family_name")
    private String familyName;
}
