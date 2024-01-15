package com.graduatebackend.dto.employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPositionListResponseDto implements Serializable {
    @JsonProperty("employee_position_id")
    private Integer employeePositionId;

    @JsonProperty("employee_position_title")
    private String employeePositionTitle;

    @JsonProperty("employee_position_description")
    private String employeePositionDescription;

    @JsonProperty("employees")
    private int noOfEmployees;
}
