package com.graduatebackend.dto.employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetEmployeePositionResponseDto implements Serializable {
    @JsonProperty("position_id")
    private Integer employeePositionId;

    @JsonProperty("position_title")
    private String employeePositionTitle;

    @JsonProperty("description")
    private String employeePositionDescription;
}
