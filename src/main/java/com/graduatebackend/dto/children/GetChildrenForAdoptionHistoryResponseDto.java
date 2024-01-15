package com.graduatebackend.dto.children;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetChildrenForAdoptionHistoryResponseDto implements Serializable {
    @JsonProperty("children_id")
    private Integer childrenId;
    @JsonProperty("children_full_name")
    private String childrenFullName;
}
