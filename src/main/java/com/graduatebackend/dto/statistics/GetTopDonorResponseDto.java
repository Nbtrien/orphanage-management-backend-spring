package com.graduatebackend.dto.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetTopDonorResponseDto implements Serializable {
    @JsonProperty("donor_id")
    private Integer donorId;
    @JsonProperty("donor_full_name")
    private String donorFullName;
    @JsonProperty("donor_mail_address")
    private String donorMailAddress;
    @JsonProperty("donor_phone_number")
    private String donorPhoneNumber;
    @JsonProperty("donation_count")
    private long donationCount;
    @JsonProperty("total_amount")
    private double totalAmount;
}
