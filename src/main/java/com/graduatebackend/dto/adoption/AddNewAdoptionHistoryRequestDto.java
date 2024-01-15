package com.graduatebackend.dto.adoption;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewAdoptionHistoryRequestDto implements Serializable {
    @JsonProperty("children_id")
    private Integer childrenId;
    @JsonProperty("applicant")
    private AddNewApplicantRequestDto applicant;
    @JsonProperty("spouse")
    private AddNewApplicantSpouseRequestDto spouse;
    @JsonProperty("adoption_date")
    private Date adoptionDate;
    @JsonProperty("description")
    private String adoptionDescription;
    @JsonProperty("document_file_path")
    private String documentFilePath;
}
