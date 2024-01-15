package com.graduatebackend.dto.adoption;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewAdoptionApplicationRequestDto implements Serializable {
    @JsonProperty("applicant")
    private AddNewApplicantRequestDto applicant;
    @JsonProperty("spouse")
    private AddNewApplicantSpouseRequestDto spouse;
    @JsonProperty("reason")
    private String reason;
}
