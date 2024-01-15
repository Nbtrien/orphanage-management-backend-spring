package com.graduatebackend.dto.family;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.entity.Family;
import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetFamilyConditionResponseDto implements Serializable {
    @JsonProperty("family_condition_id")
    private Integer familyConditionId;
    @JsonProperty("age_from")
    private int ageFrom;
    @JsonProperty("age_to")
    private int ageTo;
    @JsonProperty("min_number_of_children")
    private int minNumberOfChildren;
    @JsonProperty("max_number_of_children")
    private int maxNumberOfChildren;
    @JsonProperty("no_of_family")
    private Integer noOfFamily;
}
