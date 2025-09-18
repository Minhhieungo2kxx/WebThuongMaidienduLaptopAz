package vn.ecornomere.ecornomereAZ.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class VNPayConfig {
      public static String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
      public static String vnp_ReturnUrl = "http://localhost:8081/vnpay-payment-return";
      public static String vnp_TmnCode = "GSIQMJSF"; // Mã website tại VNPAY
      public static String vnp_HashSecret = "40WG3OMYWXSZ93PFQZBVRG21A00KYZT4"; // Chuỗi bí mật
      public static String vnp_ApiUrl = "https://sandbox.vnpayment.vn/merchant_webapi/api/transaction";

      public static String hashAllFields(Map<String, String> fields) {
            List<String> fieldNames = new ArrayList<>(fields.keySet());
            Collections.sort(fieldNames);
            StringBuilder sb = new StringBuilder();

            boolean first = true;
            for (String fieldName : fieldNames) {
                  String fieldValue = fields.get(fieldName);
                  if (fieldValue != null && fieldValue.length() > 0) {
                        try {
                              fieldValue = URLEncoder.encode(fieldValue, "UTF-8"); // ✅ Quan trọng
                        } catch (UnsupportedEncodingException e) {
                              throw new RuntimeException(e);
                        }

                        if (!first) {
                              sb.append("&");
                        }
                        sb.append(fieldName).append("=").append(fieldValue);
                        first = false;
                  }
            }
            System.out.println("✅ Chuoi chu ki VNP: " + sb.toString());

            return hmacSHA512(vnp_HashSecret, sb.toString());
      }

      public static String hmacSHA512(final String key, final String data) {
            try {
                  if (key == null || data == null) {
                        throw new NullPointerException();
                  }
                  final javax.crypto.Mac hmac512 = javax.crypto.Mac.getInstance("HmacSHA512");
                  byte[] hmacKeyBytes = key.getBytes();
                  final javax.crypto.spec.SecretKeySpec secretKey = new javax.crypto.spec.SecretKeySpec(hmacKeyBytes,
                              "HmacSHA512");
                  hmac512.init(secretKey);
                  byte[] dataBytes = data.getBytes(java.nio.charset.StandardCharsets.UTF_8);
                  byte[] result = hmac512.doFinal(dataBytes);
                  StringBuilder sb = new StringBuilder(2 * result.length);
                  for (byte b : result) {
                        sb.append(String.format("%02x", b & 0xff));
                  }
                  return sb.toString();
            } catch (Exception ex) {
                  return "";
            }
      }

      public static String getIpAddress(HttpServletRequest request) {
            String ipAdress;
            try {
                  ipAdress = request.getHeader("X-FORWARDED-FOR");
                  if (ipAdress == null) {
                        ipAdress = request.getRemoteAddr();
                  }
            } catch (Exception ex) {
                  ipAdress = "Invalid IP:" + ex.getMessage();
            }
            return ipAdress;
      }

      public static String getRandomNumber(int len) {
            java.util.Random rnd = new java.util.Random();
            String chars = "0123456789";
            StringBuilder sb = new StringBuilder(len);
            for (int i = 0; i < len; i++) {
                  sb.append(chars.charAt(rnd.nextInt(chars.length())));
            }
            return sb.toString();
      }

}
