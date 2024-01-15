package com.graduatebackend.dto.family;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.graduatebackend.entity.Children;
import com.graduatebackend.entity.FamilyCondition;
import com.graduatebackend.entity.Mother;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewFamilyRequestDto implements Serializable {
    @NotBlank(message = "Please enter family name")
    @JsonProperty("family_name")
    private String familyName;

    @JsonProperty("date_of_formation")
    private Date dateOfFormation;

    @NotNull(message = "Please choose mother")
    @JsonProperty("mother_id")
    private Integer motherId;

    @NotNull(message = "Please choose family condition")
    @JsonProperty("family_condition_id")
    private Integer familyConditionId;
}
