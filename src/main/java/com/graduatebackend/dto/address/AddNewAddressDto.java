package com.graduatebackend.dto.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewAddressDto implements Serializable {
    @JsonProperty("province_id")
    private String provinceId;
    @JsonProperty("district_id")
    private String districtId;
    @JsonProperty("ward_id")
    private String wardId;
    @JsonProperty("address_detail")
    private String addressDetail;

}
