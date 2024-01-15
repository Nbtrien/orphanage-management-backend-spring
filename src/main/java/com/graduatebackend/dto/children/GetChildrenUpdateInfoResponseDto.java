package com.graduatebackend.dto.children;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.address.AddNewAddressDto;
import com.graduatebackend.dto.medical.MedicalRecordResponseDto;
import com.graduatebackend.dto.medical.VaccinationRecordResponseDto;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetChildrenUpdateInfoResponseDto implements Serializable {
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
    @JsonProperty("is_waiting_adoption")
    private boolean isWaitingAdoption;
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
    @JsonProperty("children_status_id")
    private int childrenStatusId;
    @JsonProperty("address")
    private AddNewAddressDto address;
}
