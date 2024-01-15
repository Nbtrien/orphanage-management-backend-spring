package com.graduatebackend.dto.medical;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetChildMedicalRecordResponseDto implements Serializable {
    @JsonProperty("children_id")
    private Integer childrenId;

    @JsonProperty("children_full_name")
    private String childrenFullName;

    @JsonProperty("medical_records")
    private List<MedicalRecordResponseDto> medicalRecords;

    @JsonProperty("vaccination_records")
    private List<VaccinationRecordResponseDto> vaccinationRecords;
}
