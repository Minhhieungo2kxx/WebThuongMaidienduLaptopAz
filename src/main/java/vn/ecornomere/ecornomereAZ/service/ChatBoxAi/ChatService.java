package vn.ecornomere.ecornomereAZ.service.ChatBoxAi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import vn.ecornomere.ecornomereAZ.model.User;
import vn.ecornomere.ecornomereAZ.model.dto.ChatMessageDto;
import vn.ecornomere.ecornomereAZ.model.entity.ChatMessage;
import vn.ecornomere.ecornomereAZ.repository.ChatMessageRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class ChatService {
      private static final Logger logger = LoggerFactory.getLogger(ChatService.class);
      @Autowired
      private ChatMessageRepository chatMessageRepository;

      // Lưu trữ lịch sử chat theo session
      private final Map<String, List<ChatMessageDto>> chatHistory = new ConcurrentHashMap<>();

      @Value("${gemini.api.key}")
      private String apiKey;

      @Value("${gemini.api.url}")
      private String apiUrl;

      private static final String SYSTEM_TEMPLATE = """
                  Bạn là một trợ lý AI thông minh và hữu ích.
                  Hãy trả lời một cách ngắn gọn, chính xác và thân thiện.
                  Nếu không biết câu trả lời, hãy thành thật nói là không biết.
                  Trả lời bằng tiếng Việt trừ khi được yêu cầu khác.
                  """;

      public ChatMessageDto processMessage(ChatMessageDto messageDto, String sessionId, User user) {
            try {
                  logger.info("Processing message for session: {}", sessionId);

                  // Lưu tin nhắn user
                  messageDto.setSessionId(sessionId);
                  addToHistory(sessionId, messageDto);

                  String context = buildContext(sessionId, user);

                  String aiResponse = callGeminiApi(messageDto.getMessage(), context);

                  ChatMessageDto responseDto = new ChatMessageDto();
                  responseDto.setMessage(messageDto.getMessage());
                  responseDto.setResponse(aiResponse);
                  responseDto.setSessionId(sessionId);
                  responseDto.setTimestamp(System.currentTimeMillis());

                  addResponseToHistory(sessionId, aiResponse);

                  logger.info("Successfully processed message for session: {}", sessionId);
                  saveMessage(sessionId, user, messageDto.getMessage(), aiResponse);

                  return responseDto;

            } catch (Exception e) {
                  logger.error("Error processing chat message for session: {}", sessionId, e);
                  ChatMessageDto errorResponse = new ChatMessageDto();
                  errorResponse.setMessage(messageDto.getMessage());
                  errorResponse.setResponse("Xin lỗi, đã có lỗi xảy ra. Vui lòng thử lại sau.");
                  errorResponse.setSessionId(sessionId);
                  return errorResponse;
            }
      }

      private String callGeminiApi(String userMessage, String context) throws Exception {
            RestTemplate rest = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();

            // Tạo prompt kết hợp hệ thống + context + message
            String fullPrompt;
            if (context == null || context.isEmpty()) {
                  fullPrompt = SYSTEM_TEMPLATE + "\n" + userMessage;
            } else {
                  fullPrompt = SYSTEM_TEMPLATE + "\nLịch sử cuộc trò chuyện:\n" + context + "\n\nTin nhắn hiện tại: "
                              + userMessage;
            }

            // Tạo body JSON theo spec API
            Map<String, Object> part = new HashMap<>();
            part.put("text", fullPrompt);

            Map<String, Object> content = new HashMap<>();
            content.put("parts", List.of(part));

            Map<String, Object> body = new HashMap<>();
            body.put("contents", List.of(content));

            String bodyJson = mapper.writeValueAsString(body);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-goog-api-key", apiKey);

            HttpEntity<String> request = new HttpEntity<>(bodyJson, headers);

            ResponseEntity<String> resp = rest.postForEntity(apiUrl, request, String.class);
            if (!resp.getStatusCode().is2xxSuccessful()) {
                  throw new RuntimeException(
                              "Gemini API response error: " + resp.getStatusCodeValue() + " / " + resp.getBody());
            }

            String respBody = resp.getBody();
            JsonNode root = mapper.readTree(respBody);
            // Dựa theo cấu trúc JSON trả về, bạn cần điều chỉnh đường dẫn sau
            // Ví dụ: root.candidates[0].content.parts[0].text
            JsonNode candidate = root.path("candidates").get(0);
            if (candidate.isMissingNode()) {
                  return "Xin lỗi, không có phản hồi từ AI.";
            }
            JsonNode textNode = candidate.path("content").path("parts").get(0).path("text");
            String result = textNode.asText("");
            return result.isEmpty() ? "Xin lỗi, không có phản hồi hợp lệ." : result;
      }

      private String buildContext(String sessionId, User user) {
            List<ChatMessageDto> history;

            if (user != null) {
                  // Lấy lịch sử từ DB theo user
                  List<ChatMessage> messages = chatMessageRepository.findByUserOrderByCreatedAtAsc(user);
                  history = messages.stream()
                              .map(m -> new ChatMessageDto(m.getMessage(), m.getResponse()))
                              .collect(Collectors.toList());
            } else {
                  // Lấy từ RAM theo session
                  history = chatHistory.get(sessionId);
            }

            if (history == null || history.isEmpty()) {
                  return "";
            }

            StringBuilder sb = new StringBuilder();
            int start = Math.max(0, history.size() - 5); // lấy 5 đoạn gần nhất
            for (int i = start; i < history.size(); i++) {
                  ChatMessageDto msg = history.get(i);
                  if (msg.getMessage() != null) {
                        sb.append("User: ").append(msg.getMessage()).append("\n");
                  }
                  if (msg.getResponse() != null) {
                        sb.append("AI: ").append(msg.getResponse()).append("\n");
                  }
            }

            return sb.toString();
      }

      private void addToHistory(String sessionId, ChatMessageDto m) {
            chatHistory.computeIfAbsent(sessionId, k -> new ArrayList<>()).add(m);
            List<ChatMessageDto> list = chatHistory.get(sessionId);
            if (list.size() > 50) {
                  list.subList(0, list.size() - 50).clear();
            }
      }

      private void addResponseToHistory(String sessionId, String response) {
            List<ChatMessageDto> list = chatHistory.get(sessionId);
            if (list != null && !list.isEmpty()) {
                  ChatMessageDto last = list.get(list.size() - 1);
                  last.setResponse(response);
            }
      }

      // Lưu tin nhắn (theo user nếu có, ngược lại thì theo sessionId)
      @Transactional
      public void saveMessage(String sessionId, User user, String message, String response) {
            ChatMessage chat = new ChatMessage();
            chat.setSessionId(sessionId);
            chat.setUser(user); // Gán entity User ở đây
            chat.setMessage(message);
            chat.setResponse(response);
            chatMessageRepository.save(chat);
      }

      // Lấy lịch sử chat, ưu tiên theo user nếu có
      public List<ChatMessageDto> getChatHistory(String sessionId, User user) {
            List<ChatMessage> messages;

            if (user != null) {
                  messages = chatMessageRepository.findByUserOrderByCreatedAtAsc(user);
            } else {
                  messages = chatMessageRepository.findBySessionIdOrderByCreatedAtAsc(sessionId);
            }

            return messages.stream()
                        .map(m -> new ChatMessageDto(m.getMessage(), m.getResponse()))
                        .collect(Collectors.toList());
      }

      @Transactional
      public void clearChatHistory(String sessionId, User user) {
            if (user != null) {
                  // Xóa trong DB theo user
                  chatMessageRepository.deleteByUser(user);
                  logger.info("Cleared chat history for user: {}", user.getId());
            } else {
                  // Xóa trong DB theo sessionId
                  chatMessageRepository.deleteBySessionId(sessionId);
                  logger.info("Cleared chat history for session: {}", sessionId);
            }

            // Nếu bạn vẫn đang lưu lịch sử chat tạm trong RAM thì xóa luôn:
            chatHistory.remove(sessionId);
      }

}
