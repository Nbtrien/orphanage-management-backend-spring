package com.graduatebackend.constant;

import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ContentCode {
	@Getter
	@AllArgsConstructor
	public enum PageType implements DisplayCode {
		ADOPTION_INFO(1, "Trang quy định nhận nuôi"), BANK_INFO(2, "Thông tin chuyển khoản");

		private final Integer code;
		private final String display;

		public static PageType of(String display) {
			return Stream.of(PageType.values()).filter(p -> p.getDisplay().equals(display)).findFirst().orElse(null);
		}

		public static PageType of(Integer code) {
			return Stream.of(PageType.values()).filter(p -> p.getCode().equals(code)).findFirst().orElse(null);
		}
	}
}
