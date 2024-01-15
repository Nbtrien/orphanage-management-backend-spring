package com.graduatebackend.dto.children;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetChildrenUpdateStatusResponseDto implements Serializable {
    @JsonProperty("children_id")
    private Integer childrenId;
    @JsonProperty("children_name")
    private String childrenName;
    @JsonProperty("status_id")
    private Integer statusId;
    @JsonProperty("status_name")
    private String statusName;
    @JsonProperty("status_options")
    private List<ChildrenStatusDto> statusOptions;
}
