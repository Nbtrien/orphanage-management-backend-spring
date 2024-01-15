package com.graduatebackend.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAccountResponseDto implements Serializable {
    @JsonProperty("account_id")
    private Integer accountId;
    @JsonProperty("account_mail_address")
    private String accountMailAddress;
    @JsonProperty("applicant_full_name")
    private String applicantFullName;
    @JsonProperty("applicant_phone_number")
    private String applicantPhoneNumber;
    @JsonProperty("register_date_time")
    private Timestamp registerDateTime;
    @JsonProperty("account_status")
    private int accountStatus;
    @JsonProperty("volunteer_id")
    private Integer volunteerId;
    @JsonProperty("donor_id")
    private Integer donorId;
}
