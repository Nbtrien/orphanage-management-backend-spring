package com.graduatebackend.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("data")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateUserRequestDto {
    @NotBlank
    @Size(max = 255)
    @JsonProperty("user_name")
    private String userName;

    @Size(max = 255)
    @JsonProperty("password")
    private String password;

    @NotBlank
    @Size(max = 255)
    @JsonProperty("user_mail_address")
    private String userMailAddress;

    @NotNull
    @JsonProperty("roles_id")
    private List<Integer> rolesId;
}
