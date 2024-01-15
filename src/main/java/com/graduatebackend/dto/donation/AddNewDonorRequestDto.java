package com.graduatebackend.dto.donation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.address.AddNewAddressDto;
import com.graduatebackend.entity.Address;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewDonorRequestDto implements Serializable {
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
}
