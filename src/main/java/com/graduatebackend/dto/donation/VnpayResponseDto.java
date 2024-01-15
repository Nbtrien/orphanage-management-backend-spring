package com.graduatebackend.dto.donation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VnpayResponseDto implements Serializable {
    @JsonProperty("RspCode")
    private String RspCode;
    @JsonProperty("Message")
    private String Message;
}
