package vn.ecornomere.ecornomereAZ.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.ecornomere.ecornomereAZ.model.User;
import vn.ecornomere.ecornomereAZ.repository.PasswordResetTokenRepository;

@Service
public class ForgotPasswordService {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean processForgotPassword(String email) {
        try {
            User user = userService.getbyEmail(email);
            if (user == null) {
                return false;
            }

            // Xóa token cũ nếu có
            tokenRepository.deleteByUser(user);

            // Tạo mật khẩu mới
            String newPassword = generateRandomPassword();

            // Cập nhật mật khẩu trong database
            user.setPassword(passwordEncoder.encode(newPassword));
            userService.handleSaveUser(user);

            // Gửi email với mật khẩu mới
            emailService.sendPasswordResetEmail(email, newPassword);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }

        return password.toString();
    }

    @Scheduled(fixedRate = 3600000) // Chạy mỗi giờ
    public void cleanupExpiredTokens() {
        tokenRepository.deleteByExpiryDateLessThan(LocalDateTime.now());
    }
}
