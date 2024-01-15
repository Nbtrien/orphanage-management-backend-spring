package com.graduatebackend.email;

import java.sql.Timestamp;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.graduatebackend.entity.Event;
import com.graduatebackend.entity.Volunteer;
import com.graduatebackend.utils.ConvertUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailUtilsService {
	private final EmailService emailService;

	@Async
	public CompletableFuture<Void> sendApprovedVolunteerEmail(Volunteer volunteer, Event event) {
		String subject = "Đơn Đăng Ký Tình Nguyện Đã Được Chấp Nhận";
		String message = "Chào bạn " + volunteer.getApplicant().getApplicantFullName() + ",\n" + "\n"
				+ "Chúng tôi xin thông báo rằng đơn đăng ký tình nguyện của bạn đã được chấp nhận. Sự đóng góp của "
				+ "bạn là quan trọng và sẽ tạo ra một sự khác biệt đặc biệt trong cộng đồng của chúng ta.\n" + "\n"
				+ "Thông tin chi tiết sự kiện tình nguyện:\n" + "\n" + "Tên sự kiện: " + event.getTitle() + "\n"
				+ "Tổng quan hoạt động: " + event.getSummary() + "\n" + "Thời gian bắt đầu: "
				+ ConvertUtils.formatTimestamp(event.getEventStartDate()) + "\n" + "Thời gian kết thúc: "
				+ ConvertUtils.formatTimestamp(event.getEventEndDate()) + "\n"
				+ "Địa điểm: Tại trại trẻ của chung tôi, địa chỉ K142 đường Lê Văn Hiến, Phường Khuê Mỹ, "
				+ "Quận Ngũ Hành Sơn, Đà Nẵng.\n" + "\n"
				+ "Nếu bạn có bất kỳ câu hỏi hoặc cần thêm thông tin, hãy trả lời email này hoặc liên hệ "
				+ "với chúng tôi qua 0123456789.\n" + "\n"
				+ "Hủy đăng ký: Nếu có bất kỳ lý do nào khiến bạn không thể tham gia, hãy liên hệ với chúng tôi để "
				+ "chúng tôi có thể sắp xếp lại hoặc tìm thay thế.\n" + "\n"
				+ "Chân thành cảm ơn vì sự quan tâm và cam kết của bạn đối với trại trẻ của chúng tôi. Chúng "
				+ "tôi rất mong được gặp bạn và cùng nhau tạo ra những trải nghiệm ý nghĩa. \n\n"
				+ "Mọi thắc mắc và góp ý, xin vui lòng liên hệ với chúng tôi qua:\n"
				+ "Email: support.orphanage@gmail.com\n" + "Hotline: 0123456789\n\n" + "Trân trọng,\n" + "Orphanage";

		emailService.sendEmailWithSimpleMessage(volunteer.getApplicant().getApplicantMailAddress(), subject, message);

		return CompletableFuture.completedFuture(null);
	}

	@Async
	public CompletableFuture<Void> sendApprovedAppointmentEmail(String to, String name, Timestamp startDate,
			Timestamp endDate, int attendees) {
		String subject = "Thông tin đặt lịch hẹn";
		String message = "Chào bạn " + name + ",\n" + "\n"
				+ "Chúng tôi xin thông báo rằng lịch hẹn thăm trại trẻ của bạn đã được chấp nhận.\n" + "\n"
				+ "Thông tin chi tiết lịch hẹn:\n" + "\n" + "Thời gian : " + ConvertUtils.formatTimestamp(startDate)
				+ " ~ " + ConvertUtils.formatTimestamp(endDate) + "\n" + "Số lượng tham gia: " + attendees
				+ " người. \n"
				+ "Địa điểm: Tại trại trẻ của chung tôi, địa chỉ K142 đường Lê Văn Hiến, Phường Khuê Mỹ, "
				+ "Quận Ngũ Hành Sơn, Đà Nẵng.\n" + "\n"
				+ "Nếu bạn có bất kỳ câu hỏi hoặc cần thêm thông tin, hãy trả lời email này hoặc liên hệ "
				+ "với chúng tôi qua 0123456789.\n" + "\n"
				+ "Hủy đăng ký: Nếu có bất kỳ lý do nào khiến bạn không thể tham gia, hãy liên hệ với chúng tôi để "
				+ "chúng tôi có thể sắp xếp lại.\n" + "\n"
				+ "Chân thành cảm ơn vì sự quan tâm và cam kết của bạn đối với trại trẻ của chúng tôi. Chúng "
				+ "tôi rất mong được gặp bạn và cùng nhau tạo ra những trải nghiệm ý nghĩa. \n\n"
				+ "Mọi thắc mắc và góp ý, xin vui lòng liên hệ với chúng tôi qua:\n"
				+ "Email: support.orphanage@gmail.com\n" + "Hotline: 0123456789\n\n" + "Trân trọng,\n" + "Orphanage";

		emailService.sendEmailWithSimpleMessage(to, subject, message);
		return CompletableFuture.completedFuture(null);
	}

	@Async
	public CompletableFuture<Void> sendDeclinedAppointmentEmail(String to, String name, Timestamp startDate,
			Timestamp endDate, int attendees) {
		String subject = "Thông tin đặt lịch hẹn";
		String message = "Chào bạn " + name + ",\n" + "\n"
				+ "Chúng tôi trân trọng sự quan tâm của bạn và mong đợi cơ hội gặp gỡ và thăm trại trẻ mồ côi của chúng"
				+ " tôi.\n" + "\n" + "Thông tin chi tiết lịch hẹn:\n" + "\n" + "Thời gian : "
				+ ConvertUtils.formatTimestamp(startDate) + " ~ " + ConvertUtils.formatTimestamp(endDate) + "\n"
				+ "Số lượng tham gia: " + attendees + " người. \n"
				+ "Địa điểm: Tại trại trẻ của chung tôi, địa chỉ K142 đường Lê Văn Hiến, Phường Khuê Mỹ, "
				+ "Quận Ngũ Hành Sơn, Đà Nẵng.\n" + "\n"
				+ "Sau khi xem xét, chúng tôi rất tiếc phải thông báo rằng không thể xác nhận lịch hẹn thăm trại vào "
				+ "thời điểm đã đề xuất. .\n" + "\n"
				+ "Chúng tôi hiểu rằng việc điều này có thể tạo ra sự phiền hà và xin lỗi vì sự không thoải mái. Trong "
				+ "trường hợp có bất kỳ nhu cầu hoặc câu hỏi nào khác, chúng tôi rất mong được hỗ trợ thông tin hoặc "
				+ "cung cấp thông tin khác theo yêu cầu của bạn.\n" + "\n"
				+ "Chúng tôi hy vọng có cơ hội gặp gỡ và hỗ trợ bạn trong tương lai. Xin cảm ơn sự hiểu thông cảm của "
				+ "bạn.\n" + "\n" + "Mọi thắc mắc và góp ý, xin vui lòng liên hệ với chúng tôi qua:\n"
				+ "Email: support.orphanage@gmail.com\n" + "Hotline: 0123456789\n\n" + "Trân trọng,\n" + "Orphanage";

		emailService.sendEmailWithSimpleMessage(to, subject, message);
		return CompletableFuture.completedFuture(null);
	}

	@Async
	public CompletableFuture<Void> sendDeclinedVolunteerEmail(Volunteer volunteer, Event event) {
		String subject = "Thông báo về Đơn Đăng ký Tình nguyện không được chấp thuận";
		String message = "Chào bạn " + volunteer.getApplicant().getApplicantFullName() + ",\n" + "\n"
				+ "Chúng tôi trân trọng gửi đến bạn thông báo về tình trạng đơn đăng ký sự kiện tình nguyện "
				+ event.getTitle() + " của bạn tại tổ chức của chúng tôi. "
				+ "Rất tiếc, sau quá trình xem xét kỹ lưỡng, đơn của bạn đã không được chấp thuận lúc này.\n" + "\n"
				+ "Chúng tôi rất đánh giá sự quan tâm và nỗ lực của bạn trong việc tham gia vào hoạt động tình "
				+ "nguyện của chúng tôi. Tuy nhiên, quyết định này được đưa ra sau khi xem xét các yếu tố khác "
				+ "nhau, bao gồm nhưng không giới hạn vào nhu cầu hiện tại của chúng tôi và phù hợp với mục tiêu và"
				+ " giá trị của tổ chức.\n" + "\n"
				+ "Chúng tôi mong rằng bạn hiểu và chấp nhận quyết định này. Mặc dù bạn không được chấp thuận lúc "
				+ "này, chúng tôi rất khuyến khích bạn theo đuổi các cơ hội khác trong tương lai và tham gia vào "
				+ "các hoạt động khác của cộng đồng.\n" + "\n"
				+ "Chúng tôi xin gửi lời cảm ơn chân thành đến bạn vì sự quan tâm và cam kết của bạn đối với công "
				+ "việc tình nguyện. Hy vọng rằng chúng ta có thể gặp nhau trong những dự án tương lai.\n" + "\n"
				+ "Nếu bạn có bất kỳ câu hỏi nào hoặc muốn biết thêm chi tiết về quyết định này, đừng ngần ngại "
				+ "liên hệ với chúng tôi qua email này.\n" + "\n"
				+ "Chúng tôi xin chúc bạn mọi điều tốt lành và hy vọng có cơ hội gặp lại bạn trong tương lai. \n\n"
				+ "Mọi thắc mắc và góp ý, xin vui lòng liên hệ với chúng tôi qua:\n"
				+ "Email: support.orphanage@gmail.com\n" + "Hotline: 0123456789\n\n" + "Trân trọng,\n" + "Orphanage";

		emailService.sendEmailWithSimpleMessage(volunteer.getApplicant().getApplicantMailAddress(), subject, message);

		return CompletableFuture.completedFuture(null);
	}

	@Async
	public CompletableFuture<Void> sendVerifyAccountEmail(String to, String token) {
		String subject = "Xác nhận email đăng ký Website Orphanage";
		String message = "Xin chào Bạn,\n\n" + "Bạn vừa đăng ký thành công tài khoản trên website orphanage. "
				+ "Vui lòng click vào đường dẫn sau để kích hoạt tài khoản!\n\n"
				+ "http://localhost:3001/activation?email=" + to + "&token=" + token + "\n\n"
				+ "Nếu Quý khách không gửi yêu cầu này, vui lòng liên hệ ngay với chúng tôi!\n\n"
				+ "Mọi thắc mắc và góp ý, xin vui lòng liên hệ với chúng tôi qua:\n"
				+ "Email: support.orphanage@gmail.com\n" + "Hotline: 0123456789\n\n" + "Trân trọng,\n"
				+ "Vui lòng không trả lời email này!";
		emailService.sendEmailWithSimpleMessage(to, subject, message);

		return CompletableFuture.completedFuture(null);
	}

	@Async
	public CompletableFuture<Void> sendApplicantVerifyAccountEmail(String to, String token) {
		String subject = "Xác nhận email đăng ký Website Orphanage";
		String message = "Xin chào Bạn,\n\n" + "Bạn vừa đăng ký thành công tài khoản trên website orphanage. "
				+ "Vui lòng click vào đường dẫn sau để kích hoạt tài khoản!\n\n"
				+ "http://localhost:3001/applicant/activation?email=" + to + "&token=" + token + "\n\n"
				+ "Nếu Quý khách không gửi yêu cầu này, vui lòng liên hệ ngay với chúng tôi!\n\n"
				+ "Mọi thắc mắc và góp ý, xin vui lòng liên hệ với chúng tôi qua:\n"
				+ "Email: support.orphanage@gmail.com\n" + "Hotline: 0123456789\n\n" + "Trân trọng,\n"
				+ "Vui lòng không trả lời email này!";
		emailService.sendEmailWithSimpleMessage(to, subject, message);

		return CompletableFuture.completedFuture(null);
	}
	
	@Async
	public CompletableFuture<Void> sendDonationUsageConfirmationEmail(String to, String donorName, String donorToken,
			String donationHash, double donationAmount, Timestamp donationTime, double usageAmount, Timestamp usageTime,
			String usageNote) {
		String subject = "Xác Nhận Sử Dụng Quyên Góp của Bạn";
		String message = "Kính gửi quý ông/bà " + donorName + ", \n\n"
				+ "Chúng tôi xin thông báo với bạn về khoản tài trợ của bạn:\n" + "Số Tiền: "
				+ ConvertUtils.formatToVND(donationAmount) + ". \n" + "Ngày quyên Góp: "
				+ ConvertUtils.formatTimestamp(donationTime) + ". \n" + "Mã quyên Góp: " + donationHash + ".\n"
				+ "Đã được trại trẻ của chúng tôi sử dụng với thông tin như sau: \n"
				+ "Số tiền đã sử dụng: " + ConvertUtils.formatToVND(usageAmount) + ". \n" + "Ngày sử dụng: "
				+ ConvertUtils.formatTimestamp(usageTime) + ". \n" + "Chi tiết: " + usageNote + ". \n"
				+ "Chúng tôi cam kết sử dụng mọi đồng tiền quyên góp của bạn một cách minh bạch và hiệu quả nhất để "
				+ "đảm bảo rằng nó sẽ đến đúng nơi và tạo ra sự thay đổi tích cực.\n"
				+ "Hãy theo dõi trang web của chúng tôi hoặc liên hệ trực tiếp để cập nhật về cách chúng tôi sử dụng "
				+ "quyên góp của bạn và những tiến " + "triển mới nhất.\n\n"
				+ "Một lần nữa, chúng tôi chân thành cảm ơn sự hỗ trợ của bạn và hy vọng nhận được sự tiếp tục ủng hộ"
				+ " trong tương lai.\n\n" + "Mọi thắc mắc và góp ý, xin vui lòng liên hệ với chúng tôi qua:\n"
				+ "Email: support.orphanage@gmail.com\n" + "Hotline: 0123456789\n\n" + "Trân trọng,\n" + "Orphanage";

		emailService.sendEmailWithSimpleMessage(to, subject, message);
		return CompletableFuture.completedFuture(null);
	}
}
