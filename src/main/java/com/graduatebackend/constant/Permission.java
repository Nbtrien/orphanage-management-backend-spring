package com.graduatebackend.constant;

import org.springframework.security.core.GrantedAuthority;

public enum Permission implements GrantedAuthority {

	USER_CREATE(Code.USER_CREATE), USER_ACCESS(Code.USER_ACCESS), USER_DELETE(Code.USER_DELETE),
	USER_EDIT(Code.USER_EDIT), USER_SHOW(Code.USER_SHOW);

	private final String authority;

	Permission(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

	public class Code {
		public static final String USER_CREATE = "ROLE_USER_CREATE";
		public static final String USER_ACCESS = "ROLE_USER_ACCESS";
		public static final String USER_SHOW = "ROLE_USER_SHOW";
		public static final String USER_DELETE = "ROLE_USER_DELETE";
		public static final String USER_EDIT = "ROLE_USER_EDIT";
	}
}
