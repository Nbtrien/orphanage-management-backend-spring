package com.graduatebackend.dto.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetChildrenStatisticsByAgeResponseDto implements Serializable {
    @JsonProperty("age_range")
    private String ageRange;
    @JsonProperty("children_count")
    private long childrenCount;
}
