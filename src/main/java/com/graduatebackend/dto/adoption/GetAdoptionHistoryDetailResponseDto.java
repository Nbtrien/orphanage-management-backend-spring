package com.graduatebackend.dto.adoption;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.dto.children.GetChildrenResponseDto;
import com.graduatebackend.entity.Children;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAdoptionHistoryDetailResponseDto implements Serializable {
    @JsonProperty("adoption_history_id")
    private Integer adoptionHistoryId;
    @JsonProperty("adoption_date")
    private Date adoptionDate;
    @JsonProperty("adoption_description")
    private String adoptionDescription;
    @JsonProperty("document_url")
    private String documentUrl;
    @JsonProperty("applicant")
    private GetApplicantDetailResponseDto applicant;
    @JsonProperty("spouse")
    private GetSpouseDetailResponseDto spouse;
    @JsonProperty("children")
    private GetChildrenResponseDto children;
}
