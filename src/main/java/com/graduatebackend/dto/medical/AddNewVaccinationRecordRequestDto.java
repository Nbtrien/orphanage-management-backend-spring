package com.graduatebackend.dto.medical;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewVaccinationRecordRequestDto implements Serializable {
    @NotBlank(message = "Please enter vaccine name")
    @JsonProperty("vaccine_name")
    private String vaccineName;

    @NotBlank(message = "Please enter vaccine type")
    @JsonProperty("vaccine_type")
    private String vaccineType;

    @JsonProperty("vaccination_date")
    private Date vaccinationDate;

    @NotBlank(message = "Please enter vaccine location")
    @JsonProperty("vaccination_location")
    private String vaccinationLocation;

    @JsonProperty("vaccination_notes")
    private String vaccinationNotes;

    @JsonProperty("document_file_name")
    private String vaccinationDocumentFileName;

    @JsonProperty("document_file_path")
    private String vaccinationDocumentFilePath;
}
