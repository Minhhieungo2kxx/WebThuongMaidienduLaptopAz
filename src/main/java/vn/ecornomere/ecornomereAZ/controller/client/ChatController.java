package vn.ecornomere.ecornomereAZ.controller.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import vn.ecornomere.ecornomereAZ.dto.response.ChatMessageDto;
import vn.ecornomere.ecornomereAZ.model.entity.User;
import vn.ecornomere.ecornomereAZ.service.UserService;
import vn.ecornomere.ecornomereAZ.service.ChatBoxAi.ChatService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {


    private final ChatService chatService;



    @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> sendMessage(
            @Valid @RequestBody ChatMessageDto messageDto,
            BindingResult bindingResult,
            HttpSession session) {
        return chatService.sendMessageChat(messageDto,bindingResult,session);
    }

    @GetMapping("/history")
    public ResponseEntity<Map<String, Object>> getChatHistory(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        String sessionId = session.getId();
        return chatService.getChatHistoryChat(session);
    }

    @DeleteMapping("/history/clear")
    public ResponseEntity<Map<String, Object>> clearChatHistory(HttpSession session)  {
        return chatService.clearChatHistoryChat(session);

    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");
        response.put("service", "Chat AI Service");
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }

}
