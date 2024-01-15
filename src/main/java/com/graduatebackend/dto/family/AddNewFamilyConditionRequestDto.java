package com.graduatebackend.dto.family;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Min;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewFamilyConditionRequestDto implements Serializable {
    @Min(value = 0, message = "Please enter age from")
    @JsonProperty("age_from")
    private int ageFrom;
    @Min(value = 0, message = "Please enter age to")
    @JsonProperty("age_to")
    private int ageTo;
    @Min(value = 1, message = "Please enter min number of children")
    @JsonProperty("min_number_of_children")
    private int minNumberOfChildren;
    @Min(value = 1, message = "Please enter max number of children")
    @JsonProperty("max_number_of_children")
    private int maxNumberOfChildren;
}
