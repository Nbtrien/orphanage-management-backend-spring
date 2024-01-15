package com.graduatebackend.dto.account;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerifyAccountRequestDto implements Serializable {
    @NotBlank(message = "Email cannot blank")
    private String email;
    @NotBlank(message = "Token cannot blank!")
    private String token;
}
