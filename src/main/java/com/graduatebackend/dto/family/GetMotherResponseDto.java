package com.graduatebackend.dto.family;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.entity.Address;
import com.graduatebackend.entity.Image;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetMotherResponseDto implements Serializable {
    @JsonProperty("mother_id")
    private Integer motherId;
    @JsonProperty("mother_name")
    private String motherName;
    @JsonProperty("employee_id")
    private Integer employeeId;
    @JsonProperty("date_of_birth")
    private Date dateOfBirth;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("mail_address")
    private String mailAddress;
    @JsonProperty("ethnicity")
    private String ethnicity;
    @JsonProperty("religion")
    private String religion;
    @JsonProperty("hire_date")
    private Date hireDate;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("address")
    private String address;
}
