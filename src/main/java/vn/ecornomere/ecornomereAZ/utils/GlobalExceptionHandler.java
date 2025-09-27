package vn.ecornomere.ecornomereAZ.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GlobalExceptionHandler {
      private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

      /**
       * Xử lý lỗi validation
       */
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
            response.put("message", "Đã có lỗi xảy ra trong quá trình xử lý");
            response.put("timestamp", System.currentTimeMillis());

            // Chỉ trả về thông tin lỗi chi tiết trong development
            if (isDevelopmentEnvironment()) {
                  response.put("error", ex.getMessage());
                  response.put("cause", ex.getCause() != null ? ex.getCause().getMessage() : null);
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
      }

      /**
       * Xử lý lỗi Exception tổng quát
       */
      @ExceptionHandler(Exception.class)
      public ResponseEntity<Map<String, Object>> handleGeneralException(
                  Exception ex, WebRequest request) {

            logger.error("Unexpected error occurred", ex);

            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Đã có lỗi không mong muốn xảy ra");
            response.put("timestamp", System.currentTimeMillis());

            // Chỉ trả về thông tin lỗi chi tiết trong development
            if (isDevelopmentEnvironment()) {
                  response.put("error", ex.getMessage());
                  response.put("type", ex.getClass().getSimpleName());
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
      }

      /**
       * Xử lý lỗi AI Service (custom exception)
       */
      @ExceptionHandler(AIServiceException.class)
      public ResponseEntity<Map<String, Object>> handleAIServiceException(
                  AIServiceException ex, WebRequest request) {

            logger.error("AI Service error: {}", ex.getMessage(), ex);

            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Dịch vụ AI tạm thời không khả dụng");
            response.put("timestamp", System.currentTimeMillis());

            if (isDevelopmentEnvironment()) {
                  response.put("error", ex.getMessage());
            }

            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
      }

      /**
       * Kiểm tra môi trường development
       */
      private boolean isDevelopmentEnvironment() {
            String profile = System.getProperty("spring.profiles.active", "");
            return profile.contains("dev") || profile.contains("local");
      }

      /**
       * Custom exception cho AI Service
       */
      public static class AIServiceException extends RuntimeException {
            public AIServiceException(String message) {
                  super(message);
            }

            public AIServiceException(String message, Throwable cause) {
                  super(message, cause);
            }
      }

}
