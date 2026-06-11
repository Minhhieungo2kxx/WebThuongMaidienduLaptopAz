package vn.ecornomere.ecornomereAZ.service.payments;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ecornomere.ecornomereAZ.enums.PaymentMethod;
import vn.ecornomere.ecornomereAZ.enums.PaymentTransactionStatus;
import vn.ecornomere.ecornomereAZ.dto.request.MomoCallbackRequest;
import vn.ecornomere.ecornomereAZ.dto.request.PaymentDefault;
import vn.ecornomere.ecornomereAZ.model.entity.PaymentTransaction;
import vn.ecornomere.ecornomereAZ.repository.PaymentTransactionRepository;


@Service
@RequiredArgsConstructor
public class MomoService {
      private static final String PARTNER_CODE = "MOMO";
      private static final String REDIRECT_URL = "http://localhost:8081/momo-return";
      private static final String IPN_URL = "http://localhost:8081/momo-ipn";
      private static final String REQUEST_TYPE = "payWithMethod";
      private final PaymentTransactionRepository paymentTransactionRepository;
      private final ObjectMapper objectMapper;

      @Value("${access.key}")
      private String accessKey;

      @Value("${secret.key}")
      private String secretKey;


      public String createPaymentRequest(String amount,String requestId) {
            try {
//                  String requestId = PARTNER_CODE + new Date().getTime();
                  String orderId = requestId;
                  String orderInfo = "Thanh toán MoMo";
                  String extraData = "";

                  String rawSignature = String.format(
                              "accessKey=%s&amount=%s&extraData=%s&ipnUrl=%s&orderId=%s&orderInfo=%s&partnerCode=%s&redirectUrl=%s&requestId=%s&requestType=%s",
                              accessKey, amount, extraData, IPN_URL, orderId, orderInfo, PARTNER_CODE, REDIRECT_URL,
                              requestId, REQUEST_TYPE);

                  String signature = signHmacSHA256(rawSignature, secretKey);

                  JSONObject requestBody = new JSONObject();
                  requestBody.put("partnerCode", PARTNER_CODE);
                  requestBody.put("accessKey", accessKey);
                  requestBody.put("requestId", requestId);
                  requestBody.put("amount", amount);
                  requestBody.put("orderId", orderId);
                  requestBody.put("orderInfo", orderInfo);
                  requestBody.put("redirectUrl", REDIRECT_URL);
                  requestBody.put("ipnUrl", IPN_URL);
                  requestBody.put("extraData", extraData);
                  requestBody.put("requestType", REQUEST_TYPE);
                  requestBody.put("signature", signature);

                  CloseableHttpClient client = HttpClients.createDefault();
                  HttpPost post = new HttpPost("https://test-payment.momo.vn/v2/gateway/api/create");
                  post.setHeader("Content-Type", "application/json");
                  post.setEntity(new StringEntity(requestBody.toString(), StandardCharsets.UTF_8));

                  CloseableHttpResponse response = client.execute(post);

                  BufferedReader br = new BufferedReader(
                              new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
                  StringBuilder result = new StringBuilder();
                  String line;

                  while ((line = br.readLine()) != null)
                        result.append(line);

                  JSONObject json = new JSONObject(result.toString());

                  return json.getString("payUrl");

            } catch (Exception e) {
                  e.printStackTrace();
                  return null;
            }
      }
      @Transactional
      public String createMomoPayment(String email, BigDecimal amount, HttpServletRequest request, PaymentDefault paymentDefault) throws JsonProcessingException {
            String requestId = PARTNER_CODE + new Date().getTime();
            PaymentTransaction transaction = new PaymentTransaction();
            transaction.setTxnRef(requestId);
            transaction.setEmail(email);
            transaction.setAmount(amount);
            transaction.setStatus(PaymentTransactionStatus.PENDING);
            transaction.setPaymentMethod(PaymentMethod.MOMO);
            transaction.setShippingInfoJson(
                    objectMapper.writeValueAsString(paymentDefault));
            transaction.setCreatedAt(
                    LocalDateTime.now());
            paymentTransactionRepository.save(transaction);
            return createPaymentRequest(String.valueOf(amount.intValueExact()),requestId);
      }

      private static String signHmacSHA256(String data, String key) throws Exception {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKey);

            byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();

            for (byte b : hash) {
                  String h = Integer.toHexString(0xff & b);
                  if (h.length() == 1)
                        hex.append('0');
                  hex.append(h);
            }

            return hex.toString();
      }
      public boolean verifyMomoCallbackSignature(MomoCallbackRequest callback) {
            try {
                  if (callback.getSignature() == null || callback.getSignature().isBlank()) {
                        return false;
                  }
                  String rawSignature = "accessKey=" + accessKey +
                                  "&amount=" + callback.getAmount() +
                                  "&extraData=" + (callback.getExtraData() == null ? "" : callback.getExtraData()) +
                                  "&message=" + callback.getMessage() +
                                  "&orderId=" + callback.getOrderId() +
                                  "&orderInfo=" + callback.getOrderInfo() +
                                  "&orderType=" + callback.getOrderType() +
                                  "&partnerCode=" + callback.getPartnerCode() +
                                  "&payType=" + callback.getPayType() +
                                  "&requestId=" + callback.getRequestId() +
                                  "&responseTime=" + callback.getResponseTime() +
                                  "&resultCode=" + callback.getResultCode() +
                                  "&transId=" + callback.getTransId();

                  String generatedSignature =
                          signHmacSHA256(rawSignature, secretKey);

                  return MessageDigest.isEqual(
                          generatedSignature.getBytes(StandardCharsets.UTF_8),
                          callback.getSignature().getBytes(StandardCharsets.UTF_8)
                  );
            } catch (Exception e) {
                  return false;
            }
      }

}
