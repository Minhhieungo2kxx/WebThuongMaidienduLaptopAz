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
                  Bạn là trợ lý AI của một website thương mại điện tử chuyên bán Laptop.

                  NHIỆM VỤ CHÍNH:
                  1. Xác định câu hỏi của người dùng có liên quan đến việc mua bán Laptop hay không
                  2. Phát hiện câu hỏi MƠ HỒ hoặc THIẾU NGỮ CẢNH
                  3. Trả lời phù hợp theo từng trường hợp

                  ========================
                  A. CHỦ ĐỀ ĐƯỢC PHÉP
                  ========================
                  - Laptop, máy tính, MacBook
                  - Cấu hình Laptop: CPU, RAM, SSD/HDD, card đồ họa (GPU), màn hình, pin
                  - Giá sản phẩm, so sánh, tư vấn mua Laptop
                  - Mua hàng, thanh toán, trả góp
                  - Bảo hành, đổi trả, vận chuyển

                  ========================
                  B. CHỦ ĐỀ KHÔNG ĐƯỢC PHÉP
                  ========================
                  - Toán học, lập trình, y tế, chính trị, đời sống cá nhân
                  - Kiến thức công nghệ CHUNG không gắn với Laptop
                  - Bất kỳ nội dung nào ngoài phạm vi website

                  ========================
                  C. XỬ LÝ CÂU HỎI
                  ========================

                   TRƯỜNG HỢP 1 – NGOÀI CHỦ ĐỀ:
                  Nếu câu hỏi KHÔNG liên quan đến Laptop hoặc mua bán trên website:
                  → Trả lời CHÍNH XÁC đoạn sau (không thêm gì khác):

                  [INVALID]
                  Xin lỗi, tôi chỉ hỗ trợ các câu hỏi liên quan đến Laptop và mua sắm trên website.

                   TRƯỜNG HỢP 2 – MƠ HỒ / THIẾU THÔNG TIN:
                  Nếu câu hỏi CÓ liên quan đến Laptop nhưng thiếu thông tin quan trọng
                  (ví dụ: chỉ hỏi "RAM bao nhiêu là đủ?", "Card đồ họa có tốt không?", "Laptop này ổn không?")
                  → KHÔNG tự suy đoán
                  → Hỏi lại người dùng để làm rõ, chỉ hỏi những thông tin cần thiết, ví dụ:
                  - Nhu cầu sử dụng (học tập, văn phòng, gaming, đồ họa…)
                  - Ngân sách
                  - Thương hiệu hoặc mẫu máy (nếu có)

                  Câu trả lời phải ngắn gọn, lịch sự và hướng người dùng bổ sung thông tin.

                   TRƯỜNG HỢP 3 – HỢP LỆ & ĐỦ THÔNG TIN:
                  Nếu câu hỏi rõ ràng và đầy đủ:
                  → Trả lời trực tiếp, chính xác, không lan man.

                  ========================
                  D. QUY TẮC BẮT BUỘC
                  ========================
                  - KHÔNG trả lời kiến thức chung ngoài ngữ cảnh Laptop
                  - KHÔNG đoán nhu cầu người dùng
                  - KHÔNG nhắc đến từ "AI", "prompt", hay quy tắc nội bộ
                  - Trả lời bằng tiếng Việt, thân thiện, chuyên nghiệp
                  """;

      public ChatMessageDto processMessage(ChatMessageDto messageDto, String sessionId, User user) {
            try {
                  logger.info("Processing message for session: {}", sessionId);

                  // Lưu tin nhắn user
                  messageDto.setSessionId(sessionId);
                  addToHistory(sessionId, messageDto);

                  String context = buildContext(sessionId, user);

                  String aiResponse = callGeminiWithFallback(messageDto.getMessage(), context);

                  ChatMessageDto responseDto = new ChatMessageDto();
                  responseDto.setMessage(messageDto.getMessage());
                  responseDto.setSessionId(sessionId);
                  responseDto.setTimestamp(LocalDateTime.now());
                  // Intent không hợp lệ
                  if (aiResponse.contains("[INVALID]")) {
                        responseDto.setResponse(
                                    "Xin lỗi, tôi chỉ hỗ trợ các câu hỏi liên quan đến Laptop và mua sắm trên website.");
                        return responseDto;
                  }
                  // Intent hợp lệ
                  responseDto.setResponse(aiResponse);
                  responseDto.setMessage(messageDto.getMessage());
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

      private String callGeminiWithFallback(String userMessage, String context) throws Exception {
            List<String> modelList = Arrays.stream(models.split(","))
                        .map(String::trim)
                        .toList();

            Exception lastException = null;

            for (String model : modelList) {
                  try {
                        logger.warn("Trying Gemini model: {}", model);
                        return callGeminiApiWithModel(model, userMessage, context);
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

      private String callGeminiApiWithModel(String model, String userMessage, String context) throws Exception {

            RestTemplate rest = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();

            String fullPrompt;
            if (context == null || context.isEmpty()) {
                  fullPrompt = SYSTEM_TEMPLATE + "\nNgười dùng: " + userMessage + "\nTrợ lý:";
            } else {
                  fullPrompt = SYSTEM_TEMPLATE
                              + "\nLịch sử:\n" + context
                              + "\nNgười dùng: " + userMessage
                              + "\nTrợ lý:";
            }

            Map<String, Object> part = Map.of("text", fullPrompt);
            Map<String, Object> content = Map.of("parts", List.of(part));
            Map<String, Object> body = Map.of("contents", List.of(content));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-goog-api-key", apiKey);

            HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(body), headers);

            String url = baseUrl + "/" + model + ":generateContent";

            ResponseEntity<String> resp = rest.postForEntity(url, request, String.class);

            JsonNode root = mapper.readTree(resp.getBody());
            JsonNode textNode = root.path("candidates")
                        .get(0)
                        .path("content")
                        .path("parts")
                        .get(0)
                        .path("text");

            return textNode.asText("Xin lỗi, không có phản hồi.");
      }

      private String buildContext(String sessionId, User user) {
            List<ChatMessageDto> history;

            if (user != null) {
                  // Lấy lịch sử từ DB theo user
                  List<ChatMessage> messages = chatMessageRepository.findByUserOrderByCreatedAtAsc(user);
                  history = messages.stream()
                              .map(m -> new ChatMessageDto(m.getMessage(), m.getResponse(), m.getCreatedAt()))
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
