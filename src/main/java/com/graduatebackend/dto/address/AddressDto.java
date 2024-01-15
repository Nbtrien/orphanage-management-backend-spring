package com.graduatebackend.dto.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto implements Serializable {
    @JsonProperty("province")
    private AddressProvinceDto province;
    @JsonProperty("district")
    private AddressDistrictDto district;
    @JsonProperty("ward")
    private WardDto ward;
    @JsonProperty("address-detail")
    private String addressDetail;
}
