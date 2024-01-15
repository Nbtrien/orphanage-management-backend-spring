package com.graduatebackend.dto.medical;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaccinationRecordResponseDto implements Serializable {
    @JsonProperty("vaccination_record_id")
    private String vaccinationRecordId;

    @JsonProperty("vaccine_name")
    private String vaccineName;

    @JsonProperty("vaccine_type")
    private String vaccineType;

    @JsonProperty("vaccination_date")
    private Date vaccinationDate;

    @JsonProperty("vaccination_location")
    private String vaccinationLocation;

    @JsonProperty("vaccination_notes")
    private String vaccinationNotes;

    @JsonProperty("document_name")
    private String documentName;

    @JsonProperty("document_url")
    private String documentUrl;
}
