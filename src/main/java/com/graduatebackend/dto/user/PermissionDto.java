package com.graduatebackend.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionDto implements Serializable {
    @JsonProperty("permission_id")
    private int permissionId;

    @JsonProperty("permission_title")
    private String permissionTitle;
}
