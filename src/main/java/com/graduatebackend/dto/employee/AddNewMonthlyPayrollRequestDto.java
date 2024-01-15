package com.graduatebackend.dto.employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewMonthlyPayrollRequestDto implements Serializable {
    @JsonProperty("month")
    private int month;
    @JsonProperty("year")
    private int year;
}
