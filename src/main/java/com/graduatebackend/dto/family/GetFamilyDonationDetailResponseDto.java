package com.graduatebackend.dto.family;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.donation.GetDonationResponseDto;
import com.graduatebackend.dto.donation.GetDonationUsageResponseDto;
import com.graduatebackend.dto.donation.GetFundingUsageDetailResponseDto;
import com.graduatebackend.dto.donation.GetFundingUsageResponseDto;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetFamilyDonationDetailResponseDto implements Serializable {
    @JsonProperty("family_id")
    private Integer familyId;
    @JsonProperty("family_name")
    private String familyName;
    @JsonProperty("date_of_formation")
    private Date dateOfFormation;
    @JsonProperty("date_of_termination")
    private Date dateOfTermination;
    @JsonProperty("mother")
    private GetMotherResponseDto mother;
    @JsonProperty("condition")
    private GetFamilyConditionResponseDto condition;
    @JsonProperty("no_of_children")
    private Integer noOfChildren;
    @JsonProperty("donation_count")
    private Long donationCount;
    @JsonProperty("total_donation_amount")
    private Double totalDonationAmount;
    @JsonProperty("funding_amount")
    private Double fundingAmount;
    @JsonProperty("donations")
    private List<GetDonationResponseDto> donations;
    @JsonProperty("funding_usages")
    private List<GetFundingUsageResponseDto> fundingUsages;
}
