package com.graduatebackend.dto.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {
    @NotBlank(message = "Please enter username")
    @JsonProperty("user_name")
    private String userName;

    @NotBlank(message = "Please enter email address")
    @Size(max = 255, message = "Please enter your email address within 255 characters.")
    @Email(message = "The format of the email address is incorrect.", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^" +
            ".-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @NotBlank(message = "Please enter password")
    private String password;

    @JsonProperty("roles_id")
    private List<Integer> rolesId;
}
