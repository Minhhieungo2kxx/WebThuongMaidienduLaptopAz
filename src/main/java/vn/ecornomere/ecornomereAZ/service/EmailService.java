package vn.ecornomere.ecornomereAZ.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

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
}
