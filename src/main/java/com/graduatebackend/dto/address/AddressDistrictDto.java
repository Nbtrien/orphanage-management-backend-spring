package com.graduatebackend.dto.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDistrictDto implements Serializable {
    @JsonProperty("district_id")
    private String districtId;
    @JsonProperty("district_name")
    private String districtName;
    @JsonProperty("district_name_en")
    private String districtNameEn;
    @JsonProperty("district_full_name")
    private String districtFullName;
    @JsonProperty("district_full_name_en")
    private String districtFullNameEn;
}
