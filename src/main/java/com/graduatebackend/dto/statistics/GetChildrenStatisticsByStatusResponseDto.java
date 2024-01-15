package com.graduatebackend.dto.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetChildrenStatisticsByStatusResponseDto implements Serializable {
    @JsonProperty("status")
    private String status;
    @JsonProperty("children_count")
    private long childrenCount;
}
