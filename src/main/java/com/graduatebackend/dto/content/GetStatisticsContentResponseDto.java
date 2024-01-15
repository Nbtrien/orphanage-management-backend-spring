package com.graduatebackend.dto.content;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetStatisticsContentResponseDto implements Serializable {
    @JsonProperty("adopted_children")
    private long adoptedChildren;
    @JsonProperty("in_care_children")
    private long inCareChildren;
}
