package com.graduatebackend.dto.account;

import java.io.Serializable;
import java.sql.Date;

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
public class AddNewAccountRequestDto implements Serializable {
	@JsonProperty("first_name")
	private String applicantFirstName;
	@JsonProperty("last_name")
	private String applicantLastName;
	@JsonProperty("mail_address")
	private String applicantMailAddress;
	@JsonProperty("date_of_birth")
	private Date applicantDateOfBirth;
	@JsonProperty("phone_number")
	private String applicantPhoneNumber;
	@JsonProperty("password")
	private String accountPassword;
	@JsonProperty("confirm_password")
	private String confirmPassword;
}
