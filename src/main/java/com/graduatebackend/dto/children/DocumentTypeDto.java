package com.graduatebackend.dto.children;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentTypeDto implements Serializable {
    @JsonProperty("document_type_id")
    private Integer documentTypeId;
    @JsonProperty("document_type_name")
    private String documentTypeName;
}
