package vn.ecornomere.ecornomereAZ.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChatMessageDto {
      @NotBlank(message = "Tin nhắn không được để trống")
      @Size(max = 1000, message = "Tin nhắn không được vượt quá 1000 ký tự")
      private String message;

      private String response;
      private Long timestamp;
      private String sessionId;

      public ChatMessageDto() {
            this.timestamp = System.currentTimeMillis();
      }

      // Constructor cần thiết
      public ChatMessageDto(String message, String response) {
            this.message = message;
            this.response = response;
            this.timestamp = System.currentTimeMillis(); // optional nếu bạn muốn set luôn
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

      public Long getTimestamp() {
            return timestamp;
      }

      public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
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

}
