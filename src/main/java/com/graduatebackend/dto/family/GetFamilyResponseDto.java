package com.graduatebackend.dto.family;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetFamilyResponseDto implements Serializable {
    @JsonProperty("family_id")
    private Integer familyId;
    @JsonProperty("family_name")
    private String familyName;
    @JsonProperty("date_of_formation")
    private Date dateOfFormation;
    @JsonProperty("date_of_termination")
    private Date dateOfTermination;
    @JsonProperty("mother")
    private GetMotherResponseDto mother;
    @JsonProperty("condition")
    private GetFamilyConditionResponseDto condition;
    @JsonProperty("no_of_children")
    private Integer noOfChildren;
    @JsonProperty("is_posted")
    private boolean isPosted;
}
