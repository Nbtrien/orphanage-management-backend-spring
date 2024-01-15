package com.graduatebackend.service;

import com.graduatebackend.dto.ResponseDto;
import com.graduatebackend.dto.authentication.AdminLoginRequestDto;
import com.graduatebackend.dto.authentication.RegisterRequestDto;

public interface AuthenticationService {

	/**
	 * admin login
	 * 
	 * @param loginRequestDto
	 * @return
	 */
	ResponseDto<?> authenticateAdmin(AdminLoginRequestDto loginRequestDto);

	/**
	 * admin register
	 * 
	 * @param registerRequestDto
	 * @return
	 */
	ResponseDto<?> registerAdmin(RegisterRequestDto registerRequestDto);
}
