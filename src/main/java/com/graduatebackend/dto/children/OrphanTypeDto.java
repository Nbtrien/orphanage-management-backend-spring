package com.graduatebackend.dto.children;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrphanTypeDto implements Serializable {
    @JsonProperty("orphan_type_id")
    private Integer orphanTypeId;
    @JsonProperty("orphan_type_name")
    private String orphanTypeName;
    @JsonProperty("description")
    private String description;
}
