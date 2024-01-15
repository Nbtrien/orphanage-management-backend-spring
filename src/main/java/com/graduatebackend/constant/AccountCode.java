package com.graduatebackend.constant;

import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class AccountCode {
	@Getter
	@AllArgsConstructor
	public enum AccountStatus implements DisplayCode {
		NOT_AUTHENTICATED(0, "Chưa xác thực"), ACTIVE(1, "Đang hoạt động"), LOCKED(2, "Đã khóa");

		private final Integer code;
		private final String display;

		public static AccountStatus of(String display) {
			return Stream.of(AccountStatus.values()).filter(p -> p.getDisplay().equals(display)).findFirst()
					.orElse(null);
		}

		public static AccountStatus of(Integer code) {
			return Stream.of(AccountStatus.values()).filter(p -> p.getCode().equals(code)).findFirst().orElse(null);
		}
	}
}
