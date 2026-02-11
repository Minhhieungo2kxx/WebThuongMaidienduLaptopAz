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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import vn.ecornomere.ecornomereAZ.model.User;
import vn.ecornomere.ecornomereAZ.model.dto.ChatMessageDto;
import vn.ecornomere.ecornomereAZ.model.entity.ChatMessage;
import vn.ecornomere.ecornomereAZ.repository.ChatMessageRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

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

      @Value("${gemini.api.base-url}")
      private String baseUrl;
      @Value("${gemini.api.models}")
      private String models; // chuỗi CSV
      private static final String SYSTEM_TEMPLATE = """
                  Bạn là trợ lý tư vấn mua sắm của một website thương mại điện tử chuyên bán laptop, PC và các linh kiện, phụ kiện máy tính.

                  NHIỆM VỤ:
                  - Chỉ trả lời các câu hỏi liên quan đến laptop, PC, linh kiện máy tính và phụ kiện đi kèm
                  - Tư vấn cấu hình, so sánh sản phẩm, khả năng tương thích, nhu cầu sử dụng
                  - Không suy đoán khi thiếu thông tin
                  - Không trả lời ngoài phạm vi sản phẩm công nghệ của website

                  ========================
                  ĐỊNH DẠNG BẮT BUỘC:
                  ========================

                  Nếu câu hỏi KHÔNG liên quan đến laptop, PC hoặc linh kiện:
                  → CHỈ trả về đúng:
                  [INVALID]

                  Nếu câu hỏi liên quan và hợp lệ:
                  → Trả về đúng định dạng:
                  [OK]
                  <nội dung trả lời>

                  KHÔNG được viết thêm bất kỳ nội dung nào ngoài định dạng trên.
                  KHÔNG giải thích lý do khi trả về [INVALID].
                  KHÔNG nhắc đến quy tắc hay hệ thống.

                  Luôn trả lời bằng tiếng Việt, ngắn gọn, chuyên nghiệp.
                  """;

      public ChatMessageDto processMessage(ChatMessageDto messageDto, String sessionId, User user) {
            try {
                  logger.info("Processing message for session: {}", sessionId);

                  // Lưu tin nhắn user
                  messageDto.setSessionId(sessionId);
                  // addToHistory(sessionId, messageDto);
                  List<ChatMessageDto> history = getChatHistory(sessionId, user);
                  String aiResponse = callGeminiWithFallback(
                              messageDto.getMessage(),
                              history);

                  ChatMessageDto responseDto = new ChatMessageDto();
                  responseDto.setMessage(messageDto.getMessage());
                  responseDto.setSessionId(sessionId);
                  responseDto.setTimestamp(LocalDateTime.now());
                  // Intent không hợp lệ

                  if (isInvalidResponse(aiResponse)) {
                        responseDto.setResponse(
                                    "Xin lỗi, tôi chỉ hỗ trợ các câu hỏi liên quan đến Laptop và mua sắm trên website");
                        return responseDto;
                  }
                  String finalResponse = extractOkContent(aiResponse);
                  responseDto.setResponse(finalResponse);
                  responseDto.setMessage(messageDto.getMessage());
                  // addResponseToHistory(sessionId, aiResponse);
                  logger.info("Successfully processed message for session: {}", sessionId);
                  saveMessage(sessionId, user, messageDto.getMessage(),"[OK]\n" + finalResponse);

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

      private String callGeminiWithFallback(String userMessage, List<ChatMessageDto> history) throws Exception {
            List<String> modelList = Arrays.stream(models.split(","))
                        .map(String::trim)
                        .toList();

            Exception lastException = null;

            for (String model : modelList) {
                  try {
                        logger.warn("Trying Gemini model: {}", model);
                        return callGeminiApiWithModel(model, userMessage, history);
                  } catch (HttpClientErrorException e) {
                        lastException = e;

                        // Chỉ fallback khi lỗi quota / not found / rate limit
                        if (e.getStatusCode().value() == 429 || e.getStatusCode().value() == 404) {
                              logger.warn("Model {} failed ({}), switching...", model, e.getStatusCode());
                              continue;
                        } else {
                              throw e;
                        }
                  }
            }

            throw new RuntimeException("All Gemini models are unavailable", lastException);
      }

      private String callGeminiApiWithModel(
                  String model,
                  String userMessage,
                  List<ChatMessageDto> history) throws Exception {

            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> body = buildRequestBody(userMessage, history);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-goog-api-key", apiKey);

            HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(body), headers);

            String url = baseUrl + "/" + model + ":generateContent";

            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            JsonNode root = mapper.readTree(response.getBody());

            return root.path("candidates")
                        .get(0)
                        .path("content")
                        .path("parts")
                        .get(0)
                        .path("text")
                        .asText("");
      }

      private Map<String, Object> buildRequestBody(String userMessage, List<ChatMessageDto> history) {
            List<Map<String, Object>> contents = new ArrayList<>();

            // 1. SYSTEM (neo hành vi)
            contents.add(Map.of(
                        "role", "user",
                        "parts", List.of(Map.of("text", SYSTEM_TEMPLATE))));

            // 2. HISTORY (tối đa 5, chỉ hợp lệ)
            if (history != null) {
                  history.stream()
                              .filter(h -> h.getResponse() != null && h.getResponse().startsWith("[OK]"))
                              .skip(Math.max(0, history.size() - 5))
                              .forEach(h -> {
                                    contents.add(Map.of(
                                                "role", "user",
                                                "parts", List.of(Map.of("text", h.getMessage()))));
                                    contents.add(Map.of(
                                                "role", "model",
                                                "parts", List.of(Map.of("text", h.getResponse()))));
                              });
            }

            // 3. USER MESSAGE (luôn là cuối)
            contents.add(Map.of(
                        "role", "user",
                        "parts", List.of(Map.of("text", userMessage))));

            return Map.of("contents", contents);
      }

      private boolean isInvalidResponse(String aiResponse) {
            return aiResponse != null && aiResponse.trim().equals("[INVALID]");
      }

      private String extractOkContent(String aiResponse) {
            return aiResponse.replaceFirst("^\\[OK\\]\\s*", "").trim();
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
                        .map(m -> new ChatMessageDto(m.getMessage(), m.getResponse(), m.getCreatedAt()))
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
