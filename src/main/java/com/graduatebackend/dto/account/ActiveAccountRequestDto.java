package com.graduatebackend.dto.account;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActiveAccountRequestDto implements Serializable {
	@NotBlank(message = "Email cannot blank")
	private String email;
	@NotBlank(message = "Token cannot blank!")
	private String token;
	@JsonProperty("applicant")
	private UpdateAccountRequestDto applicant;
}
