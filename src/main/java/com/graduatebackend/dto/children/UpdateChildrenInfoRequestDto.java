package com.graduatebackend.dto.children;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.address.AddNewAddressDto;
import com.graduatebackend.dto.image.AddNewImageDto;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateChildrenInfoRequestDto implements Serializable {
    @JsonProperty("children_id")
    private Integer childrenId;
    @JsonProperty("first_name")
    private String childrenFirstName;
    @JsonProperty("last_name")
    private String childrenLastName;
    @JsonProperty("date_of_birth")
    private Date childrenDateOfBirth;
    @JsonProperty("gender")
    private int childrenGender;
    @JsonProperty("date_of_admission")
    private Date dateOfAdmission;
    @JsonProperty("date_of_departure")
    private Date dateOfDeparture;
    @JsonProperty("ethnicity")
    private String childrenEthnicity;
    @JsonProperty("religion")
    private String childrenReligion;
    @JsonProperty("circumstance")
    private String childrenCircumstance;
    @JsonProperty("orphan_type_id")
    private int orphanTypeId;
    @JsonProperty("address")
    private AddNewAddressDto address;
    @JsonProperty("image")
    private AddNewImageDto image;
}
