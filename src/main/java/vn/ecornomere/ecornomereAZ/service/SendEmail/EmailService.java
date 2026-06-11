package vn.ecornomere.ecornomereAZ.service.SendEmail;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import vn.ecornomere.ecornomereAZ.model.entity.Order;
import vn.ecornomere.ecornomereAZ.model.entity.OrderDetail;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendPasswordResetEmail(String toEmail, String newPassword) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Đặt lại mật khẩu - Hệ thống của bạn");

            String htmlContent = buildEmailContent(newPassword);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Không thể gửi email: " + e.getMessage());
        }
    }

    public void sendOrderConfirmationEmail(String toEmail, Order order, List<OrderDetail> orderDetails) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Xác nhận đơn đặt hàng tại FPT SHOP với ID là: " + order.getId());

            String htmlContent = buildOrderConfirmationEmailContent(order, orderDetails);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Không thể gửi email: " + e.getMessage());
        }
    }

    private String buildEmailContent(String newPassword) {
        return "<html><body>" +
                "<h2 style='color: #333;'>Đặt lại mật khẩu thành công</h2>" +
                "<p>Chào bạn,</p>" +
                "<p>Mật khẩu mới của bạn là: <strong style='color: #e74c3c; font-size: 16px;'>" + newPassword
                + "</strong></p>" +
                "<p>Vui lòng đăng nhập và thay đổi mật khẩu sau khi đăng nhập thành công.</p>" +
                "<p style='color: #7f8c8d;'>Trân trọng,<br/>Đội ngũ hỗ trợ FPT-Shop</p>" +
                "</body></html>";
    }

    private String buildOrderConfirmationEmailContent(Order order, List<OrderDetail> orderDetails) {
        StringBuilder sb = new StringBuilder();

        sb.append("<html><head>");
        sb.append("<style>");
        sb.append("body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }");
        sb.append(
                ".container { max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }");
        sb.append("h2 { color: #007bff; text-align: center; }");
        sb.append("h3 { color: #555; border-bottom: 2px solid #eee; padding-bottom: 5px; }");
        sb.append("table { width: 100%; border-collapse: collapse; margin-top: 15px; }");
        sb.append("th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }");
        sb.append("th { background-color: #f8f8f8; color: #333; }");
        sb.append(".product-table tr:last-child td { border-bottom: none; }");
        sb.append(".total-row { font-weight: bold; background-color: #f0f0f0; }");
        sb.append(".footer { margin-top: 20px; text-align: center; color: #777; font-size: 0.9em; }");
        sb.append("</style>");
        sb.append("</head><body>");

        sb.append("<div class='container'>");
        sb.append("<h2>🥳 Xác nhận đơn hàng #" + order.getId() + "</h2>");
        sb.append("<p>Xin chào <strong>").append(order.getReceiverName()).append("</strong>,</p>");
        sb.append("<p>Cảm ơn bạn đã đặt hàng tại hệ thống của chúng tôi! Dưới đây là chi tiết đơn hàng của bạn:</p>");

        sb.append("<h3>📦 Thông tin giao hàng</h3>");
        sb.append("<ul>");
        sb.append("<li><strong>Địa chỉ:</strong> ").append(order.getReceiverAddress()).append("</li>");
        sb.append("<li><strong>Số điện thoại:</strong> ").append(order.getReceiverPhone()).append("</li>");
        sb.append("</ul>");

        sb.append("<h3>💳 Tóm tắt thanh toán</h3>");
        sb.append("<ul>");
        sb.append("<li><strong>Phương thức:</strong> ").append(order.getPaymentMethod()).append("</li>");
        sb.append("<li><strong>Trạng thái:</strong> ").append(order.getPaymentStatus()).append("</li>");
        sb.append("<li><strong>Tổng tiền hàng:</strong> ").append(formatCurrency(order.getTotalPrice()))
                .append("</li>");
        sb.append("<li><strong>Phí vận chuyển:</strong> ").append(formatCurrency(50000))
                .append("</li>"); // Giả sử có thêm phí ship riêng
        sb.append("<li><strong>Tổng thanh toán:</strong> <strong>").append(formatCurrency(order.getTotalPriceaddShip()))
                .append("</strong></li>");
        sb.append("<li><strong>Thời gian:</strong> ");
        if (order.getPaymentTime() != null) {
            sb.append(formatPaymentTime(order.getPaymentTime().toString()));
        } else {
            sb.append("Chưa xác định");
        }

        sb.append("</li>");
        sb.append("</ul>");

        sb.append("<h3>🛒 Chi tiết sản phẩm</h3>");
        sb.append("<table class='product-table'>");
        sb.append("<thead><tr>");
        sb.append("<th>Sản phẩm</th>");
        sb.append("<th>Giá</th>");
        sb.append("<th>Số lượng</th>");
        sb.append("<th>Thành tiền</th>");
        sb.append("</tr></thead>");
        sb.append("<tbody>");

        for (OrderDetail item : orderDetails) {
            sb.append("<tr>");
            sb.append("<td>").append(item.getProduct().getName()).append("</td>");
            sb.append("<td>").append(String.format("%,.0f đ", item.getPrice())).append("</td>");
            sb.append("<td>").append(item.getQuantity()).append("</td>");
            sb.append("<td>").append(String.format("%,.0f đ", item.getTotalPrice())).append("</td>");
            sb.append("</tr>");
        }

        sb.append("</tbody>");
        sb.append("</table>");

        sb.append("<div class='footer'>");
        sb.append("<p>Mọi thắc mắc xin liên hệ đội ngũ CSKH qua hotline: <strong>1900 6606</strong>.</p>");
        sb.append("<p>Trân trọng,<br/><strong>FPT Shop Team</strong></p>");
        sb.append("</div>");
        sb.append("</div>"); // Kết thúc container

        sb.append("</body></html>");

        return sb.toString();
    }

    private String formatCurrency(double amount) {
        NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return vndFormat.format(amount);
    }

    public String formatPaymentTime(String rawDateTime) {
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        LocalDateTime dateTime = LocalDateTime.parse(rawDateTime, inputFormat);
        return outputFormat.format(dateTime);
    }

}
