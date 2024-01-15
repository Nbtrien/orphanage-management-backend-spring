package com.graduatebackend.constant;

import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class DonationCode {
	@Getter
	@AllArgsConstructor
	public enum ResponseCode {
		SUCCESS("00", "Giao dịch thành công"), SUSPICIOUS("07", "Trừ tiền thành công. Giao dịch bị nghi ngờ"),
		INTERNET_BANKING_NOT_REGISTERED("09",
				"Giao dịch không thành công do: Thẻ/Tài khoản chưa đăng ký dịch vụ " + "InternetBanking tại ngân hàng"),
		AUTHENTICATION_FAILURE("10",
				"Giao dịch không thành công do: Khách hàng xác thực thông tin thẻ/tài khoản " + "không đúng quá 3 lần"),
		EXPIRED_TRANSACTION("11", "Giao dịch không thành công do: Đã hết hạn chờ thanh toán"),
		ACCOUNT_LOCKED("12", "Giao dịch không thành công do: Thẻ/Tài khoản của khách hàng bị khóa"),
		OTP_INCORRECT("13", "Giao dịch không thành công do Quý khách nhập sai mật khẩu xác thực giao dịch (OTP)"),
		TRANSACTION_CANCELLED("24", "Giao dịch không thành công do: Khách hàng hủy giao dịch"),
		INSUFFICIENT_BALANCE("51",
				"Giao dịch không thành công do: Tài khoản của quý khách không đủ số dư để thực " + "hiện giao dịch"),
		EXCEEDED_DAILY_TRANSACTION_LIMIT("65",
				"Giao dịch không thành công do: Tài khoản của Quý khách đã vượt quá " + "hạn mức giao dịch trong ngày"),
		BANK_UNDER_MAINTENANCE("75", "Ngân hàng thanh toán đang bảo trì"),
		PASSWORD_RETRY_LIMIT_EXCEEDED("79",
				"Giao dịch không thành công do: KH nhập sai mật khẩu thanh toán quá số " + "lần quy định"),
		OTHER_ERRORS("99", "Các lỗi khác (lỗi còn lại, không có trong danh sách mã lỗi đã liệt kê)");

		private final String code;
		private final String display;

		public static ResponseCode ofDisplay(String display) {
			return Stream.of(ResponseCode.values()).filter(p -> p.getDisplay().equals(display)).findFirst()
					.orElse(null);
		}

		public static ResponseCode ofCode(String code) {
			return Stream.of(ResponseCode.values()).filter(p -> p.getCode().equals(code)).findFirst().orElse(null);
		}
	}

	@Getter
	@AllArgsConstructor
	public enum PaymentStatus implements DisplayCode {
		NOT_PAID(0, "Chưa thanh toán"), PAID(1, "Đã thanh toán"), PAYMENT_FAILED(2, "Thanh toán không thành công");

		private final Integer code;
		private final String display;

		public static PaymentStatus of(String display) {
			return Stream.of(PaymentStatus.values()).filter(p -> p.getDisplay().equals(display)).findFirst()
					.orElse(null);
		}

		public static PaymentStatus of(Integer code) {
			return Stream.of(PaymentStatus.values()).filter(p -> p.getCode().equals(code)).findFirst().orElse(null);
		}
	}

}
