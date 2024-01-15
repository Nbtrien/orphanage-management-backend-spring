package com.graduatebackend.dto.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDto implements Serializable {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String type;
    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("roles")
    private List<String> roles;
}
