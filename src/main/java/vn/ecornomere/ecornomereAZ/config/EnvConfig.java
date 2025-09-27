package vn.ecornomere.ecornomereAZ.config;

import org.springframework.stereotype.Component;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;

@Component
public class EnvConfig {

      @SuppressWarnings("unused")
      @PostConstruct
      public void init() {
            // Nạp file .env
            Dotenv dotenv = Dotenv.load();
            // Đặt các biến môi trường
            System.setProperty("SPRING_MAIL_USERNAME", dotenv.get("SPRING_MAIL_USERNAME"));
            System.setProperty("SPRING_MAIL_PASSWORD", dotenv.get("SPRING_MAIL_PASSWORD"));
            System.setProperty("SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_ID",
                        dotenv.get("SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_ID"));
            System.setProperty("SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_SECRET",
                        dotenv.get("SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_SECRET"));
            System.setProperty("gemini.api.key", dotenv.get("gemini.api.key"));
      }

}
