package com.graduatebackend.constant;

import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ChildrenCode {
	@Getter
	@AllArgsConstructor
	public enum Gender implements DisplayCode {
		OTHER(0, "Khác"), MALE(1, "Nam"), FEMALE(2, "Nữ");

		private final Integer code;
		private final String display;

		public static Gender of(String gender) {
			return Stream.of(Gender.values()).filter(p -> p.getCode().equals(gender)).findFirst().orElse(null);
		}

		public static Gender of(Integer code) {
			return Stream.of(Gender.values()).filter(p -> p.getCode().equals(code)).findFirst().orElse(null);
		}
	}

	@Getter
	@AllArgsConstructor
	public enum ChildrenStatus implements DisplayCode {
		IN_CARE(1, "Trẻ em đang được bảo trợ."), LEFT_ORPHANAGE(2, "Trẻ em đã rời khỏi cơ sở trẻ mồ côi."),
		IN_TEMPORARY_CARE(3, "Trẻ em đang ở trong chế độ chăm sóc tạm thời."),
		LIVING_WITH_FAMILY(4, "Trẻ em đang sống cùng gia đình."),
		REUNITED_WITH_FAMILY(5, "Trẻ em đã được hòa nhập trở lại với gia đình."), PASSED_AWAY(6, "Trẻ em đã qua đời."),
		ADOPTED(7, "Trẻ em đã được nhận nuôi.");

		private final Integer code;
		private final String display;

		public static ChildrenStatus of(String display) {
			return Stream.of(ChildrenStatus.values()).filter(p -> p.getCode().equals(display)).findFirst().orElse(null);
		}

		public static ChildrenStatus of(Integer code) {
			return Stream.of(ChildrenStatus.values()).filter(p -> p.getCode().equals(code)).findFirst().orElse(null);
		}
	}
}
