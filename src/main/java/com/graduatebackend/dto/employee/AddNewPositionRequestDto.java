package com.graduatebackend.dto.employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewPositionRequestDto implements Serializable {
    @JsonProperty("title")
    private String employeePositionTitle;
    @JsonProperty("description")
    private String employeePositionDescription;
}
