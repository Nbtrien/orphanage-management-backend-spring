package com.graduatebackend.dto.children;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentDto implements Serializable {
    @JsonProperty("file_name")
    private String documentFileName;
    @JsonProperty("file_path")
    private String documentFilePath;
    @JsonProperty("document_type_id")
    private Integer documentTypeId;
}
