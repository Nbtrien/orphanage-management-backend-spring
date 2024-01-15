package com.graduatebackend.dto.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetChildrenStatisticsByFamilyResponseDto implements Serializable {
    @JsonProperty("family")
    private String family;
    @JsonProperty("children_count")
    private long childrenCount;
}
