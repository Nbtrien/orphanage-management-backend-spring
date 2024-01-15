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
public class GetChildrenRecordResponseDto implements Serializable {
    @JsonProperty("children_id")
    private Integer childrenId;

    @JsonProperty("children_full_name")
    private String childrenFullName;

    @JsonProperty("children_date_of_birth")
    private Date childrenDateOfBirth;

    @JsonProperty("children_gender")
    private String childrenGender;

    @JsonProperty("date_of_admission")
    private Date dateOfAdmission;

    @JsonProperty("date_of_departure")
    private Date dateOfDeparture;

    @JsonProperty("orphan_type")
    private String orphanType;

    @JsonProperty("children_status")
    private String childrenStatus;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("documents")
    private List<DocumentDto> documents;

    @JsonProperty("medical_records")
    private List<MedicalRecordResponseDto> medicalRecords;

    @JsonProperty("vaccination_records")
    private List<VaccinationRecordResponseDto> vaccinationRecords;
}
