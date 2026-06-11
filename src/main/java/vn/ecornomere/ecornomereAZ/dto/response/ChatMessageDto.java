package vn.ecornomere.ecornomereAZ.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {
      @NotBlank(message = "Tin nhắn không được để trống")
      @Size(max = 1000, message = "Tin nhắn không được vượt quá 1000 ký tự")
      private String message;

      private String response;
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
      private LocalDateTime timestamp;
      private String sessionId;


}
