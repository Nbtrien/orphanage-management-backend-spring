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
public class AddNewMedicalRecordRequestDto implements Serializable {
    @NotBlank(message = "Please enter diagnosis")
    @JsonProperty("diagnosis")
    private String diagnosis;

    @NotBlank(message = "Please enter prescription")
    @JsonProperty("prescription")
    private String prescription;

    @JsonProperty("medical_notes")
    private String medicalNotes;

    @JsonProperty("visit_date")
    private Date visitDate;

    @JsonProperty("document_file_name")
    private String medicalDocumentFileName;

    @JsonProperty("document_file_path")
    private String medicalDocumentFilePath;
}
