package com.graduatebackend.constant;

import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class AdoptionCode {
	@Getter
	@AllArgsConstructor
	public enum ApplicationStatus implements DisplayCode {
		PENDING(0, "Chưa xác nhận"), APPROVED(1, "Đã xác nhận"), DONE(2, "Đã hoàn thành"),
		DECLINED(3, "Đã từ chối"), CANCELED(4, "Đã hủy");

		private final Integer code;
		private final String display;

		public static AdoptionCode.ApplicationStatus of(String display) {
			return Stream.of(AdoptionCode.ApplicationStatus.values()).filter(p -> p.getDisplay().equals(display))
					.findFirst().orElse(null);
		}

		public static AdoptionCode.ApplicationStatus of(Integer code) {
			return Stream.of(AdoptionCode.ApplicationStatus.values()).filter(p -> p.getCode().equals(code)).findFirst()
					.orElse(null);
		}
	}

	@Getter
	@AllArgsConstructor
	public enum MaritalStatusCode implements DisplayCode {
		SINGLE(1, "Chưa kết hôn"), ENGAGED(2, "Sống chung chưa kết hôn"), MARRIED(3, "Đã có vợ/chồng"),
		WIDOW(4, "Góa"), DIVORCE(5, "Ly hôn hoặc ly thân");

		private final Integer code;
		private final String display;

		public static MaritalStatusCode of(String display) {
			return Stream.of(MaritalStatusCode.values()).filter(p -> p.getDisplay().equals(display)).findFirst()
					.orElse(null);
		}

		public static MaritalStatusCode of(Integer code) {
			return Stream.of(MaritalStatusCode.values()).filter(p -> p.getCode().equals(code)).findFirst().orElse(null);
		}
	}
}
