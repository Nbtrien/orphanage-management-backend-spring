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
public class MedicalRecordResponseDto implements Serializable {
    @JsonProperty("medical_record_id")
    private Integer medicalRecordId;

    @JsonProperty("diagnosis")
    private String diagnosis;

    @JsonProperty("prescription")
    private String prescription;

    @JsonProperty("medical_notes")
    private String medicalNotes;

    @JsonProperty("visit_date")
    private Date visitDate;

    @JsonProperty("document_name")
    private String documentName;

    @JsonProperty("document_url")
    private String documentUrl;
}
