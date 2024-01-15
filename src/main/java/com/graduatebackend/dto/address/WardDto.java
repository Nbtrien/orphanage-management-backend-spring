package com.graduatebackend.dto.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WardDto implements Serializable {
    @JsonProperty("ward_id")
    private String wardId;
    @JsonProperty("ward_name")
    private String wardName;
    @JsonProperty("ward_name_en")
    private String wardNameEn;
    @JsonProperty("ward_full_name")
    private String wardFullName;
    @JsonProperty("ward_full_name_en")
    private String wardFullNameEn;
}
