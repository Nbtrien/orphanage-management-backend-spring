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
public class ProvinceDto implements Serializable {
    @JsonProperty("province_id")
    private String provinceId;
    @JsonProperty("province_name")
    private String provinceName;
    @JsonProperty("province_name_en")
    private String provinceNameEn;
    @JsonProperty("province_full_name")
    private String provinceFullName;
    @JsonProperty("province_full_name_en")
    private String provinceFullNameEn;
    @JsonProperty("districts")
    private List<DistrictDto> districts;
}
