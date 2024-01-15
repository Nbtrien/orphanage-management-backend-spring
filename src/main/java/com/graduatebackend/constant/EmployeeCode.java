package com.graduatebackend.constant;

import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class EmployeeCode {
	@Getter
	@AllArgsConstructor
	public enum PayrollStatus implements DisplayCode {
		PENDING(0, "Bản thảo"), APPROVED(1, "Đã xác nhận"), DONE(2, "Đã hoàn thành"), CANCELED(4, "Đã hủy");

		private final Integer code;
		private final String display;

		public static EmployeeCode.PayrollStatus of(String display) {
			return Stream.of(EmployeeCode.PayrollStatus.values()).filter(p -> p.getDisplay().equals(display))
					.findFirst().orElse(null);
		}

		public static EmployeeCode.PayrollStatus of(Integer code) {
			return Stream.of(EmployeeCode.PayrollStatus.values()).filter(p -> p.getCode().equals(code)).findFirst()
					.orElse(null);
		}
	}

	@Getter
	@AllArgsConstructor
	public enum EmployeePosition implements DisplayCode {
		MOTHER(1, "Bà mẹ"), MEDICAL_STAFF(1, "Nhân viên y tế"), COORDINATOR(2, "Điều phối viên"),
		ACCOUNTANT(4, "Nhân viên kế toán");

		private final Integer code;
		private final String display;

		public static EmployeePosition of(String display) {
			return Stream.of(EmployeePosition.values()).filter(p -> p.getDisplay().equals(display)).findFirst()
					.orElse(null);
		}

		public static EmployeePosition of(Integer code) {
			return Stream.of(EmployeePosition.values()).filter(p -> p.getCode().equals(code)).findFirst().orElse(null);
		}
	}
}
