package com.graduatebackend.dto.children;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.medical.MedicalRecordResponseDto;
import com.graduatebackend.dto.medical.VaccinationRecordResponseDto;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetChildrenRelativesResponseDto implements Serializable {
    @JsonProperty("children_id")
    private Integer childrenId;

    @JsonProperty("children_full_name")
    private String childrenFullName;

    @JsonProperty("relatives")
    private List<GetRelativeResponseDto> relatives;
}
