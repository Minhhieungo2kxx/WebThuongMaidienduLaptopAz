package vn.ecornomere.ecornomereAZ.controller.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.ecornomere.ecornomereAZ.model.User;
import vn.ecornomere.ecornomereAZ.model.dto.ChatMessageDto;
import vn.ecornomere.ecornomereAZ.service.UserService;
import vn.ecornomere.ecornomereAZ.service.ChatBoxAi.ChatService;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
      private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

      private final ChatService chatService;

      private final UserService userService;

      public ChatController(ChatService chatService, UserService userService) {
            this.chatService = chatService;
            this.userService = userService;
      }

      /**
       * Gửi tin nhắn đến AI và nhận phản hồi
       */
      @PostMapping("/send")
      public ResponseEntity<Map<String, Object>> sendMessage(
                  @Valid @RequestBody ChatMessageDto messageDto,
                  BindingResult bindingResult,
                  HttpSession session) {

            Map<String, Object> response = new HashMap<>();

            if (bindingResult.hasErrors()) {
                  response.put("success", false);
                  response.put("message", "Dữ liệu không hợp lệ");
                  response.put("errors", bindingResult.getAllErrors());
                  return ResponseEntity.badRequest().body(response);
            }

            String sessionId = session.getId();
            logger.info("Gửi tin nhắn mới từ session: {}", sessionId);
            String email = (String) session.getAttribute("email");
            User user = null;

            if (email != null && !email.isEmpty()) {
                  user = userService.getbyEmail(email);
            } else {
                  logger.warn("Email trong session bị null hoặc rỗng");
            }

            try {

                  ChatMessageDto result = chatService.processMessage(messageDto, sessionId, user);

                  response.put("success", true);
                  response.put("message", "Tin nhắn đã được xử lý thành công");
                  response.put("data", result);
                  response.put("timestamp", System.currentTimeMillis());

                  return ResponseEntity.ok(response);

            } catch (Exception e) {
                  logger.error("Lỗi khi xử lý tin nhắn AI", e);

                  response.put("success", false);
                  response.put("message", "Đã có lỗi xảy ra khi xử lý tin nhắn");
                  response.put("error", e.getMessage());

                  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
      }

      /**
       * Lấy lịch sử chat của người dùng (theo session)
       */
      @GetMapping("/history")
      public ResponseEntity<Map<String, Object>> getChatHistory(HttpSession session) {
            Map<String, Object> response = new HashMap<>();
            String sessionId = session.getId();

            String email = (String) session.getAttribute("email");
            User user = null;

            if (email != null && !email.isEmpty()) {
                  user = userService.getbyEmail(email);
            } else {
                  logger.warn("Email trong session bị null hoặc rỗng");
            }

            try {
                  List<ChatMessageDto> history = chatService.getChatHistory(sessionId, user);

                  response.put("success", true);
                  response.put("data", history);
                  response.put("count", history.size());
                  response.put("timestamp", System.currentTimeMillis());
                  return ResponseEntity.ok(response);

            } catch (Exception e) {
                  response.put("success", false);
                  response.put("message", "Không thể lấy lịch sử chat");
                  response.put("error", e.getMessage());
                  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
      }

      /**
       * Xóa lịch sử chat theo session
       */
      @DeleteMapping("/history/clear")
      public ResponseEntity<Map<String, Object>> clearChatHistory(HttpSession session) {
            Map<String, Object> response = new HashMap<>();
            String sessionId = session.getId();

            String email = (String) session.getAttribute("email");
            User user = null;

            if (email != null && !email.isEmpty()) {
                  user = userService.getbyEmail(email);
            } else {
                  logger.warn("Email trong session bị null hoặc rỗng");
            }

            try {
                  chatService.clearChatHistory(sessionId, user);

                  response.put("success", true);
                  response.put("message", "Lịch sử chat đã được xóa thành công");

                  return ResponseEntity.ok(response);

            } catch (Exception e) {
                  logger.error("Lỗi khi xóa lịch sử chat", e);

                  response.put("success", false);
                  response.put("message", "Không thể xóa lịch sử chat");
                  response.put("error", e.getMessage());

                  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
      }

      /**
       * Kiểm tra trạng thái hoạt động của API
       */
      @GetMapping("/health")
      public ResponseEntity<Map<String, Object>> healthCheck() {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "OK");
            response.put("service", "Chat AI Service");
            response.put("timestamp", System.currentTimeMillis());

            return ResponseEntity.ok(response);
      }

}
