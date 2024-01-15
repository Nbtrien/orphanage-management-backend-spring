package com.graduatebackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {
	@Autowired
	@Qualifier("userAuthProvider")
	private AuthenticationProvider authenticationProvider;

	@Autowired
	@Qualifier("accountAuthProvider")
	private AuthenticationProvider accountAuthProvider;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
			try {
				Authentication auth = authenticationProvider.authenticate(authentication);
				return auth;
			} catch (NullPointerException e) {
				throw new BadCredentialsException("BadCredentials");
			}
		} else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
			try {
				Authentication auth = accountAuthProvider.authenticate(authentication);
				return auth;
			} catch (NullPointerException e) {
				throw new BadCredentialsException("BadCredentials");
			}
		} else {
			throw new BadCredentialsException("BadCredentials");
		}
	}
}
