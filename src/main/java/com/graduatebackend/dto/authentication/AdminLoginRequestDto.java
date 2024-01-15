package com.graduatebackend.dto.authentication;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoginRequestDto implements Serializable {
    @NotBlank(message = "Please enter email address")
    @Size(max = 255, message = "Please enter your email address within 255 characters.")
    @Email(message = "The format of the email address is incorrect.", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^" +
            ".-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @NotBlank(message = "Please enter password")
    private String password;

}
