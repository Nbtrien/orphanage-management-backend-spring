package com.graduatebackend.constant;

import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class VolunteerCode {
	@Getter
	@AllArgsConstructor
	public enum ApplicationStatus implements DisplayCode {
		PENDING(0, "Chưa xác nhận"), APPROVED(1, "Đã xác nhận"), DECLINED(2, "Đã từ chối"),
		ATTENDED(3, "Đã tham gia"), NOT_ATTENDED(4, "Không tham gia"), CANCELED(5, "Đã hủy");

		private final Integer code;
		private final String display;

		public static ApplicationStatus of(String display) {
			return Stream.of(ApplicationStatus.values()).filter(p -> p.getDisplay().equals(display)).findFirst()
					.orElse(null);
		}

		public static ApplicationStatus of(Integer code) {
			return Stream.of(ApplicationStatus.values()).filter(p -> p.getCode().equals(code)).findFirst().orElse(null);
		}
	}

	@Getter
	@AllArgsConstructor
	public enum EventState implements DisplayCode {
		All(0, "Tất cả"), OCCURRED(1, "Đã diễn ra"), IN_PROGRESS(2, "Đang diễn ra"),
		NOT_OCCURRED(3, "Chưa diễn ra");

		private final Integer code;
		private final String display;

		public static EventState of(String display) {
			return Stream.of(EventState.values()).filter(p -> p.getDisplay().equals(display)).findFirst().orElse(null);
		}

		public static EventState of(Integer code) {
			return Stream.of(EventState.values()).filter(p -> p.getCode().equals(code)).findFirst().orElse(null);
		}
	}
}
