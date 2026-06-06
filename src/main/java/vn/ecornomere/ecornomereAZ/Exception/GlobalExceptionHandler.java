package vn.ecornomere.ecornomereAZ.Exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
      private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

      @ExceptionHandler(MethodArgumentNotValidException.class)
      public ResponseEntity<Map<String, Object>> handleValidationException(
                  MethodArgumentNotValidException ex) {

            logger.warn("Validation error occurred", ex);

            Map<String, Object> response = new HashMap<>();
            Map<String, String> errors = new HashMap<>();

            ex.getBindingResult().getAllErrors().forEach(error -> {
                  String fieldName = ((FieldError) error).getField();
                  String errorMessage = error.getDefaultMessage();
                  errors.put(fieldName, errorMessage);
            });

            response.put("success", false);
            response.put("message", "Dữ liệu đầu vào không hợp lệ");
            response.put("errors", errors);
            response.put("timestamp", System.currentTimeMillis());

            return ResponseEntity.badRequest().body(response);
      }

      /**
       * Xử lý lỗi bind
       */
      @ExceptionHandler(BindException.class)
      public ResponseEntity<Map<String, Object>> handleBindException(BindException ex) {
            logger.warn("Binding error occurred", ex);

            Map<String, Object> response = new HashMap<>();
            Map<String, String> errors = ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .collect(Collectors.toMap(
                                    FieldError::getField,
                                    FieldError::getDefaultMessage,
                                    (existing, replacement) -> existing));

            response.put("success", false);
            response.put("message", "Lỗi trong quá trình xử lý dữ liệu");
            response.put("errors", errors);
            response.put("timestamp", System.currentTimeMillis());

            return ResponseEntity.badRequest().body(response);
      }

      /**
       * Xử lý lỗi IllegalArgument
       */
      @ExceptionHandler(IllegalArgumentException.class)
      public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(
                  IllegalArgumentException ex, WebRequest request) {

            logger.warn("Illegal argument: {}", ex.getMessage());

            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Tham số không hợp lệ");
            response.put("error", ex.getMessage());
            response.put("timestamp", System.currentTimeMillis());

            return ResponseEntity.badRequest().body(response);
      }

      /**
       * Xử lý lỗi Runtime tổng quát
       */
      @ExceptionHandler(RuntimeException.class)
      public ResponseEntity<Map<String, Object>> handleRuntimeException(
                  RuntimeException ex, WebRequest request) {

            logger.error("Runtime exception occurred", ex);

            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Đã có lỗi xảy ra trong quá trình xử lý: "+ex.getMessage());
            response.put("timestamp", LocalDateTime.now().toString());

            // Chỉ trả về thông tin lỗi chi tiết trong development
            if (isDevelopmentEnvironment()) {
                  response.put("error", ex.getMessage());
                  response.put("cause", ex.getCause() != null ? ex.getCause().getMessage() : null);
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
      }



      private boolean isDevelopmentEnvironment() {
            String profile = System.getProperty("spring.profiles.active", "");
            return profile.contains("dev") || profile.contains("local");
      }


      @ExceptionHandler(IllegalStateException.class)
      public ResponseEntity<?> handleIllegalStateException(IllegalStateException ex) {
            ErrorResponException<?> errorResponse = new ErrorResponException<>(
                        HttpStatus.BAD_REQUEST.value(),
                        "Exception File",
                        LocalDateTime.now().toString(),
                        ex.getMessage(),
                        null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
      }
      @ExceptionHandler(Exception.class)
      public ResponseEntity<ApiResponse<Object>> handleException(Exception e) {

            log.error("Lỗi hệ thống", e);

            ApiResponse<Object> response = ApiResponse.builder()
                    .success(false)
                    .message("Đã có lỗi xảy ra")
                    .error(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
      }
      @ExceptionHandler(ChatProcessingException.class)
      public ResponseEntity<ApiResponse<Object>> handleChatProcessingException(
              ChatProcessingException e) {

            log.error("Lỗi khi xử lý tin nhắn AI", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.builder()
                            .success(false)
                            .message("Đã có lỗi xảy ra khi xử lý tin nhắn")
                            .error(e.getMessage())
                            .build());
      }

      @ExceptionHandler(ChatHistoryException.class)
      public ResponseEntity<ApiResponse<Object>> handleChatHistoryException(
              ChatHistoryException e) {

            log.error("Lỗi lịch sử chat", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.builder()
                            .success(false)
                            .message("Không thể lấy lịch sử chat")
                            .error(e.getMessage())
                            .build());
      }
      @ExceptionHandler(GeminiUnavailableException.class)
      public ResponseEntity<ApiResponse<Object>> handleGeminiUnavailableException(
              GeminiUnavailableException e) {
            log.error("Gemini service is unavailable", e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(ApiResponse.builder()
                            .success(false)
                            .message("Gemini service is unavailable")
                            .error(e.getMessage())
                            .data(null)
                            .build());

      }

}
