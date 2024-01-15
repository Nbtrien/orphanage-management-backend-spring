package com.graduatebackend.dto.adoption;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountAddAdoptionApplicationRequestDto implements Serializable {
    @JsonProperty("account_id")
    private Integer accountId;
    @JsonProperty("marital_status_id")
    private Integer maritalStatusId;
    @JsonProperty("spouse")
    private AddNewApplicantSpouseRequestDto spouse;
    @JsonProperty("reason")
    private String reason;
}
