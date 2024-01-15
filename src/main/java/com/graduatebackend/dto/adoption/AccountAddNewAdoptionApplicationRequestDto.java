package com.graduatebackend.dto.adoption;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.image.AddNewImageDto;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountAddNewAdoptionApplicationRequestDto implements Serializable {
    @JsonProperty("account_id")
    private Integer accountId;
    @JsonProperty("front_image")
    private AddNewImageDto frontImage;
    @JsonProperty("back_image")
    private AddNewImageDto backImage;
    @JsonProperty("marital_status_id")
    private Integer maritalStatusId;
    @JsonProperty("spouse")
    private AddNewApplicantSpouseRequestDto spouse;
    @JsonProperty("reason")
    private String reason;
}
