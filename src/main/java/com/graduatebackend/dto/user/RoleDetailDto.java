package com.graduatebackend.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDetailDto implements Serializable {
    @JsonProperty("role_id")
    private int roleId;

    @JsonProperty("role_title")
    private String roleTitle;

    @JsonProperty("role_description")
    private String roleDescription;

    @JsonProperty("permissions")
    private List<PermissionDto> permissions;
}
