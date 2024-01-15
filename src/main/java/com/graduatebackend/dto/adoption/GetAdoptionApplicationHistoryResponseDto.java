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
public class GetAdoptionApplicationHistoryResponseDto implements Serializable {
    @JsonProperty("date_of_application")
    private Date dateOfApplication;
    @JsonProperty("application_status")
    private String applicationStatus;
    @JsonProperty("date_of_processing")
    private String dateOfProcessing;
    @JsonProperty("deadline_date")
    private String deadlineDate;
    @JsonProperty("application_pdf_file_path")
    private String applicationPDFFilePath;
    @JsonProperty("children_pdf_file_path")
    private String childrenPDFFilePath;
}
