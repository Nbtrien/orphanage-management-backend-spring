package com.graduatebackend.dto.donation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.address.AddNewAddressDto;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAccountDonorResponseDto implements Serializable {
    @JsonProperty("donor_id")
    private Integer donorId;
    @JsonProperty("full_name")
    private String donorFullName;
    @JsonProperty("first_name")
    private String donorFirstName;
    @JsonProperty("last_name")
    private String donorLastName;
    @JsonProperty("date_of_birth")
    private Date donorDateOfBirth;
    @JsonProperty("gender")
    private Integer donorGender;
    @JsonProperty("mail_address")
    private String donorMailAddress;
    @JsonProperty("phone_number")
    private String donorPhoneNumber;
    @JsonProperty("address")
    private AddNewAddressDto address;
    @JsonProperty("is_first_time")
    private boolean isFirstTime;
}
