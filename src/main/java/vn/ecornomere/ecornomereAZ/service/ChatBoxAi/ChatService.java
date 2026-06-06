package vn.ecornomere.ecornomereAZ.service.ChatBoxAi;

import lombok.RequiredArgsConstructor;
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
import vn.ecornomere.ecornomereAZ.Exception.ChatHistoryException;
import vn.ecornomere.ecornomereAZ.Exception.ChatProcessingException;
import vn.ecornomere.ecornomereAZ.Exception.GeminiUnavailableException;
import vn.ecornomere.ecornomereAZ.model.dto.ChatMessageDto;
import vn.ecornomere.ecornomereAZ.model.entity.ChatMessage;
import vn.ecornomere.ecornomereAZ.model.entity.Product;
import vn.ecornomere.ecornomereAZ.model.entity.User;
import vn.ecornomere.ecornomereAZ.repository.ChatMessageRepository;
import vn.ecornomere.ecornomereAZ.service.Elasticsearch.ProductIndexService;
import vn.ecornomere.ecornomereAZ.service.ProductService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    private final ChatMessageRepository chatMessageRepository;

    private final ProductService productService;

    private final ProductIndexService productIndexService;

    // Lưu trữ lịch sử chat theo session
    private final Map<String, List<ChatMessageDto>> chatHistory = new ConcurrentHashMap<>();

    @Value("${gemini.api.key}")
    private String apiKeys;

    @Value("${gemini.api.base-url}")
    private String baseUrl;
    @Value("${gemini.api.models}")
    private String models; // chuỗi CSV
    private final AtomicInteger keyIndex = new AtomicInteger(0);
    private static final String SYSTEM_TEMPLATE = """
            Bạn là AI tư vấn của website bán laptop, PC và linh kiện.
            THÔNG TIN WEBSITE THƯƠNG MẠI ĐIỆN TỬ:
                        
            - Đây là website thương mại điện tử chuyên bán laptop, PC, linh kiện và phụ kiện công nghệ.
            - Website hoạt động theo quy định pháp luật thương mại điện tử Việt Nam (Nghị định 52/2013/NĐ-CP và Nghị định 85/2021/NĐ-CP).
            - Hệ thống có các chính sách bắt buộc phải công khai gồm: bảo hành, đổi trả, giao hàng, thanh toán, và kiểm hàng.
                        
            CÁC CHÍNH SÁCH CHUẨN CỦA WEBSITE:
                        
            1. Chính sách bảo hành:
            - Áp dụng theo từng sản phẩm cụ thể trong database.
            - Thời gian bảo hành có thể từ 6 tháng đến 36 tháng tùy linh kiện/sản phẩm.
            - Chỉ bảo hành lỗi do nhà sản xuất hoặc lỗi phần cứng.
            - Không bảo hành các lỗi do người dùng (rơi vỡ, vào nước, cháy nổ do sử dụng sai cách).
                        
            2. Chính sách đổi trả:
            - Thông thường áp dụng trong 7 ngày đầu kể từ khi nhận hàng.
            - Chỉ áp dụng khi:
              + Sản phẩm lỗi do nhà sản xuất
              + Giao sai sản phẩm / thiếu hàng
              + Hư hỏng do vận chuyển (có bằng chứng)
            - Sản phẩm đổi trả phải giữ nguyên tình trạng ban đầu, đầy đủ hộp, phụ kiện, hóa đơn.
                        
            3. Chính sách giao hàng:
            - Giao hàng toàn quốc.
            - Có thể hỗ trợ giao nhanh nội thành tùy khu vực.
            - Thời gian giao hàng phụ thuộc địa điểm và đơn vị vận chuyển.
                        
            4. Chính sách thanh toán:
            - Hỗ trợ COD (thanh toán khi nhận hàng)
            - Chuyển khoản ngân hàng
            - Thanh toán online (nếu hệ thống tích hợp)
                        
            5. Chính sách kiểm hàng:
            - Khách hàng có quyền kiểm tra ngoại quan khi nhận hàng.
            - Không được sử dụng thử sản phẩm trước khi thanh toán (trừ khi có chính sách riêng).
                        
            6. Tình trạng đơn hàng:
            - Có thể tra cứu trạng thái đơn hàng nếu có dữ liệu trong hệ thống (ORDER DATABASE).
            - Bao gồm: đang xử lý / đang giao / đã giao / huỷ / hoàn trả.
                        
            7. Thông tin website:
            - Có thể cung cấp thông tin giới thiệu cửa hàng nếu có trong database.
            - Không tự tạo địa chỉ, hotline, hoặc cam kết thương hiệu nếu không có dữ liệu.
                        
            QUY TẮC QUAN TRỌNG:
            - Mọi thông tin chính sách phải ưu tiên dữ liệu từ DATABASE.
            - Nếu không có dữ liệu chính sách → phải nói không có thông tin trong hệ thống.
            - Không được tự suy đoán hoặc tự tạo chính sách mới.

            QUAN TRỌNG:

            Nếu dữ liệu sản phẩm được cung cấp từ DATABASE:

            - Chỉ được trả lời dựa trên dữ liệu đó
            - Không được tự suy đoán
            - Không được tự bịa thông số
            - Không được tạo sản phẩm không tồn tại

            Nếu không tìm thấy sản phẩm:

            Trả về:

            [NOT_FOUND]

            Nếu câu hỏi ngoài lĩnh vực laptop, PC, linh kiện:

            Trả về:

            [INVALID]

            Nếu có dữ liệu:

            Trả về:

            [OK]
            <nội dung>

            Luôn ưu tiên dữ liệu DATABASE hơn kiến thức của mô hình.
            """;

    @Transactional
    public ChatMessageDto processMessage(ChatMessageDto messageDto, String sessionId, User user) {
        try {
            logger.info("Processing message for session: {}", sessionId);

            messageDto.setSessionId(sessionId);
            List<ChatMessageDto> history = getChatHistory(sessionId, user);
            List<Product> products =
                    productService.findRelevantProducts(
                            messageDto.getMessage());
            String productContext =
                    buildProductContext(products);
            String aiResponse =
                    callGeminiWithFallback(
                            messageDto.getMessage(),
                            history,
                            productContext);

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
              if(isNotFoundResponse(aiResponse)){

                    responseDto.setResponse(
                            "Xin lỗi, tôi chưa tìm thấy sản phẩm phù hợp trong hệ thống."
                    );

                    return responseDto;
              }
            String finalResponse = extractOkContent(aiResponse);
            responseDto.setResponse(finalResponse);
            responseDto.setMessage(messageDto.getMessage());
            logger.info("Successfully processed message for session: {}", sessionId);
            saveMessage(sessionId, user, messageDto.getMessage(), "[OK]\n" + finalResponse);

            return responseDto;

        } catch (Exception e) {
            logger.error("Error processing chat message for session: {}", sessionId, e);
            throw new ChatProcessingException("Đã có lỗi xảy ra khi xử lý tin nhắn: "+e.getMessage());
        }
    }


private String callGeminiWithFallback(
        String userMessage,
        List<ChatMessageDto> history,
        String productContext) throws Exception {
    List<String> modelList = Arrays.stream(models.split(","))
            .map(String::trim)
            .toList();
    List<String> keyList = Arrays.stream(apiKeys.split(","))
            .map(String::trim)
            .toList();
    Exception lastException = null;
    for (String model : modelList) {
        int attempts = keyList.size();
        for (int i = 0; i < attempts; i++) {
            String apiKey = getNextApiKey(keyList);
            try {
                logger.info(
                        "Trying model={} key={}",
                        model,
                        maskKey(apiKey)
                );
                return callGeminiApiWithModel(
                        model,
                        apiKey,
                        userMessage,
                        history,
                        productContext
                );
            } catch (HttpClientErrorException e) {
                lastException = e;
                int status = e.getStatusCode().value();
                if (status == 429 || status == 403) {
                    logger.warn("Key {} failed ({}) -> next key", maskKey(apiKey), status
                    );
                    continue;
                }
                if (status == 404) {
                    logger.warn("Model {} not found -> next model", model
                    );
                    break;
                }
                throw e;
            }
        }
    }
    throw new GeminiUnavailableException("All Gemini models are unavailable", lastException);
}

    private String callGeminiApiWithModel(
            String model,
            String apiKey,
            String userMessage,
            List<ChatMessageDto> history, String productContext) throws Exception {

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> body = buildRequestBody(userMessage, history, productContext);

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

    private Map<String, Object> buildRequestBody(String userMessage, List<ChatMessageDto> history, String productContext) {
        List<Map<String, Object>> contents = new ArrayList<>();
        // 1. SYSTEM (neo hành vi)
        contents.add(Map.of(
                "role", "user",
                "parts", List.of(Map.of("text", SYSTEM_TEMPLATE))));
        if (!productContext.isBlank()) {

            contents.add(Map.of(
                    "role", "user",
                    "parts", List.of(
                            Map.of("text", productContext)
                    )));
        }
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
        try {
            List<ChatMessage> messages;

            if (user != null) {
                messages = chatMessageRepository.findByUserOrderByCreatedAtAsc(user);
            } else {
                messages = chatMessageRepository.findBySessionIdOrderByCreatedAtAsc(sessionId);
            }

            return messages.stream()
                    .map(m->ChatMessageDto.builder()
                            .message(m.getMessage())
                            .response(m.getResponse())
                            .timestamp(m.getCreatedAt()).build())
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new  ChatHistoryException("Không thể lấy lịch sử chat: "+e.getMessage());
        }

    }

    @Transactional
    public void clearChatHistory(String sessionId, User user) throws Exception {
        try {
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

        }catch (Exception e){
            throw new Exception("Lỗi khi xóa lịch sử chat :"+e.getMessage());
        }
    }

    private String buildProductContext(List<Product> products) {

        if (products.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        sb.append("""
                DỮ LIỆU SẢN PHẨM TỪ DATABASE:
                """);

        for (Product p : products) {

            sb.append("""
                    -------------------
                    Tên: %s
                    Giá: %.0f VNĐ
                    Tồn kho: %d
                    Hãng: %s
                    Mô tả ngắn: %s
                    Mô tả chi tiết: %s
                    -------------------
                    """
                    .formatted(
                            p.getName(),
                            p.getPrice(),
                            p.getQuantity(),
                            p.getFactory(),
                            p.getShortDesc(),
                            p.getDetailDesc()
                    ));
        }

        return sb.toString();
    }
      private boolean isNotFoundResponse(String aiResponse){

            return aiResponse != null &&
                    aiResponse.trim().equals("[NOT_FOUND]");
      }
    private String getNextApiKey(List<String> keys) {

        int index = Math.abs(
                keyIndex.getAndIncrement()
        ) % keys.size();

        return keys.get(index);
    }
    private String maskKey(String key) {
        if (key == null || key.length() < 8) {
            return "****";
        }
        return key.substring(0, 4)
                + "..."
                + key.substring(key.length() - 4);
    }

}
