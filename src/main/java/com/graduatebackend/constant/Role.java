package com.graduatebackend.constant;

//public enum Role {
//    SUPER_ADMIN("SUPER_ADMIN"),
//    FINANCE_MANAGER("FINANCE_MANAGER"),
//    COORDINATOR("COORDINATOR"),
//    INFORMATION_MANAGER("INFORMATION_MANAGER"),
//    CONTENT_CREATOR("CONTENT_CREATOR");
//
//    final String value;
//    Role(String value) {
//        this.value = value;
//    }
//    public String getRole() {
//        return value;
//    }
//}

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

	SUPER_ADMIN(Code.SUPER_ADMIN), FINANCE_MANAGER(Code.FINANCE_MANAGER), USER(Code.USER), ADMIN(Code.ADMIN);

	private final String authority;

	Role(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

	public class Code {
		// SUPER_ADMIN("SUPER_ADMIN"),
//    FINANCE_MANAGER("FINANCE_MANAGER"),
//    COORDINATOR("COORDINATOR"),
//    INFORMATION_MANAGER("INFORMATION_MANAGER"),
//    CONTENT_CREATOR("CONTENT_CREATOR");
		public static final String SUPER_ADMIN = "ROLE_SUPER_ADMIN";
		public static final String FINANCE_MANAGER = "ROLE_FINANCE_MANAGER";
		public static final String USER = "ROLE_USER";
		public static final String ADMIN = "ROLE_ADMIN";
	}
}
