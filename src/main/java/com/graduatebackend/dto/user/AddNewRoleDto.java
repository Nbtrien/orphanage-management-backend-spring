package com.graduatebackend.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddNewRoleDto implements Serializable {

    @NotBlank(message = "Please enter role title")
    @JsonProperty("role_title")
    private String roleTitle;

    @NotBlank(message = "Please enter role description")
    @JsonProperty("role_description")
    private String roleDescription;

    @JsonProperty("permissions_id")
    private List<Integer> permissionsId;

}
