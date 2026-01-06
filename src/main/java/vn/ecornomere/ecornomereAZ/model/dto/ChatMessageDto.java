package vn.ecornomere.ecornomereAZ.model.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChatMessageDto {
      @NotBlank(message = "Tin nhắn không được để trống")
      @Size(max = 1000, message = "Tin nhắn không được vượt quá 1000 ký tự")
      private String message;

      private String response;
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
      private LocalDateTime timestamp;
      private String sessionId;

      public ChatMessageDto() {
            
      }

      // Constructor cần thiết
      public ChatMessageDto(String message, String response, LocalDateTime localDateTime) {
            this.message = message;
            this.response = response;
            this.timestamp = localDateTime; // optional nếu bạn muốn set luôn
      }

      // Getters and Setters
      public String getMessage() {
            return message;
      }

      public void setMessage(String message) {
            this.message = message;
      }

      public String getResponse() {
            return response;
      }

      public void setResponse(String response) {
            this.response = response;
      }

      public String getSessionId() {
            return sessionId;
      }

      public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
      }

      @Override
      public String toString() {
            return "ChatMessageDto{" +
                        "message='" + message + '\'' +
                        ", response='" + response + '\'' +
                        ", timestamp=" + timestamp +
                        ", sessionId='" + sessionId + '\'' +
                        '}';
      }

      public LocalDateTime getTimestamp() {
            return timestamp;
      }

      public void setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
      }

}
