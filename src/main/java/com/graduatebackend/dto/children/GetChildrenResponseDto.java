package com.graduatebackend.dto.children;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetChildrenResponseDto implements Serializable {
    @JsonProperty("children_id")
    private Integer childrenId;
    @JsonProperty("children_first_name")
    private String childrenFirstName;
    @JsonProperty("children_last_name")
    private String childrenLastName;
    @JsonProperty("children_full_name")
    private String childrenFullName;
    @JsonProperty("children_date_of_birth")
    private Date childrenDateOfBirth;
    @JsonProperty("children_gender")
    private String childrenGender;
    @JsonProperty("date_of_admission")
    private Date dateOfAdmission;
    @JsonProperty("children_ethnicity")
    private String childrenEthnicity;
    @JsonProperty("children_religion")
    private String childrenReligion;
    @JsonProperty("children_circumstance")
    private String childrenCircumstance;
    @JsonProperty("address")
    private String address;
    @JsonProperty("orphan_type")
    private String orphanType;
    @JsonProperty("children_status")
    private String childrenStatus;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("family_id")
    private Integer familyId;
}
