package com.graduatebackend.dto.donation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.blockchain.GetDonationStatsResponseDto;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDonorDetailResponseDto implements Serializable {
    @JsonProperty("donor_id")
    private Integer donorId;
    @JsonProperty("donor_full_name")
    private String donorFullName;
    @JsonProperty("donor_token")
    private String donorToken;
    @JsonProperty("donor_date_of_birth")
    private Date donorDateOfBirth;
    @JsonProperty("donor_gender")
    private String donorGender;
    @JsonProperty("donor_mail_address")
    private String donorMailAddress;
    @JsonProperty("donor_phone_number")
    private String donorPhoneNumber;
    @JsonProperty("address")
    private String address;
    @JsonProperty("stats")
    private GetDonationStatsResponseDto stats;
    @JsonProperty("donations")
    private List<GetDonationResponseDto> donations;
}
