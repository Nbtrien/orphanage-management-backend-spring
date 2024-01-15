package com.graduatebackend.dto.adoption;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAdoptionHistoryResponseDto implements Serializable {
    @JsonProperty("adoption_history_id")
    private Integer adoptionHistoryId;
    @JsonProperty("adoption_date")
    private Date adoptionDate;
    @JsonProperty("adoption_description")
    private String adoptionDescription;
    @JsonProperty("children_id")
    private Integer childrenId;
    @JsonProperty("children_full_name")
    private String childrenFullName;
    @JsonProperty("children_image_url")
    private String childrenImageUrl;
    @JsonProperty("application_id")
    private Integer applicantId;
    @JsonProperty("applicant_full_name")
    private String applicantFullName;
    @JsonProperty("spouse_full_name")
    private String spouseFullName;
}
