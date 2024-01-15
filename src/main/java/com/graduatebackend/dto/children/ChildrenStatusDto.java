package com.graduatebackend.dto.children;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChildrenStatusDto implements Serializable {
    @JsonProperty("children_status_id")
    private Integer childrenStatusId;
    @JsonProperty("children_status_name")
    private String childrenStatusName;
    @JsonProperty("description")
    private String description;
}
