package com.graduatebackend.dto.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistrictDto implements Serializable {
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
    @JsonProperty("wards")
    private List<WardDto> wards;
}
