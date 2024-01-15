package com.graduatebackend.constant;

import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class AppointmentCode {
	@Getter
	@AllArgsConstructor
	public enum AppointmentStatus implements DisplayCode {
		PENDING(0, "Chưa xác nhận"), APPROVED(1, "Đã xác nhận"), DECLINED(2, "Đã từ chối"),
		CANCELED(3, "Đã hủy bỏ"), COMPLETED(4, "Đã hoàn thành"), NO_SHOW(5, "Không đến thăm"),
		EXPIRED(6, "Đã hết hạn");

		private final Integer code;
		private final String display;

		public static AppointmentStatus of(String display) {
			return Stream.of(AppointmentStatus.values()).filter(p -> p.getDisplay().equals(display)).findFirst()
					.orElse(null);
		}

		public static AppointmentStatus of(Integer code) {
			return Stream.of(AppointmentStatus.values()).filter(p -> p.getCode().equals(code)).findFirst().orElse(null);
		}
	}
}
