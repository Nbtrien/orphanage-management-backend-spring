package com.graduatebackend.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDto implements Serializable {
    @JsonProperty("role_id")
    private int roleId;

    @JsonProperty("role_title")
    private String roleTitle;
}
