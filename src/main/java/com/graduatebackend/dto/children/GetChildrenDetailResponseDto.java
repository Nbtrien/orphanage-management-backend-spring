package com.graduatebackend.dto.children;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class GetChildrenDetailResponseDto implements Serializable {
    @JsonProperty("children_id")
    private Integer childrenId;
    @JsonProperty("first_name")
    private String childrenFirstName;
    @JsonProperty("last_name")
    private String childrenLastName;
    @JsonProperty("full_name")
    private String childrenFullName;
    @JsonProperty("date_of_birth")
    private Date childrenDateOfBirth;
    @JsonProperty("gender")
    private String childrenGender;
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
    @JsonProperty("address")
    private String address;
    @JsonProperty("orphan_type")
    private String orphanType;
    @JsonProperty("children_status_id")
    private String childrenStatusId;
    @JsonProperty("children_status")
    private String childrenStatus;
    @JsonProperty("is_change_status")
    private boolean isChangeStatus;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("documents")
    private List<DocumentDto> documents;
    @JsonProperty("relatives")
    private List<GetRelativeResponseDto> relatives;
}
