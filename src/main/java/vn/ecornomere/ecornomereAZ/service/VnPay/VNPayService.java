package vn.ecornomere.ecornomereAZ.service.VnPay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import vn.ecornomere.ecornomereAZ.utils.VNPayConfig;

@Service
public class VNPayService {
      public String createOrder(HttpServletRequest request, int amount, String orderInfor, String urlReturn) {
            String vnp_Version = "2.1.0";
            String vnp_Command = "pay";
            String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
            String vnp_IpAddr = VNPayConfig.getIpAddress(request);
            String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
            String orderType = "order-type";

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", vnp_Version);
            vnp_Params.put("vnp_Command", vnp_Command);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            long vnpAmount = (long) amount;
            vnp_Params.put("vnp_Amount", String.valueOf(vnpAmount * 100));
            vnp_Params.put("vnp_CurrCode", "VND");

            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", orderInfor);
            vnp_Params.put("vnp_OrderType", orderType);

            String locate = "vn";
            vnp_Params.put("vnp_Locale", locate);

            urlReturn += VNPayConfig.vnp_ReturnUrl;
            vnp_Params.put("vnp_ReturnUrl", urlReturn);
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            cld.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator<String> itr = fieldNames.iterator();
            while (itr.hasNext()) {
                  String fieldName = itr.next();
                  String fieldValue = vnp_Params.get(fieldName);
                  if ((fieldValue != null) && (fieldValue.length() > 0)) {
                        // Build hash data
                        hashData.append(fieldName);
                        hashData.append('=');
                        try {
                              hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                              // Build query
                              query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                              query.append('=');
                              query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                        } catch (UnsupportedEncodingException e) {
                              e.printStackTrace();
                        }
                        if (itr.hasNext()) {
                              query.append('&');
                              hashData.append('&');
                        }
                  }
            }
            String queryUrl = query.toString();
            String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
            String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;
            return paymentUrl;
      }

      public int orderReturn(HttpServletRequest request) {
            Map<String, String> fields = new HashMap<>();
            for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
                  String fieldName = params.nextElement();
                  String fieldValue = request.getParameter(fieldName);
                  if ((fieldValue != null) && (fieldValue.length() > 0)) {
                        fields.put(fieldName, fieldValue);
                  }
            }

            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            if (fields.containsKey("vnp_SecureHashType")) {
                  fields.remove("vnp_SecureHashType");
            }
            if (fields.containsKey("vnp_SecureHash")) {
                  fields.remove("vnp_SecureHash");
            }
            String signValue = VNPayConfig.hashAllFields(fields);

            System.out.println("🔐 Chữ ký hệ thống tạo: " + signValue);
            System.out.println("🔐 Chữ ký VNPAY gửi về: " + vnp_SecureHash);
            System.out.println("✅ Trạng thái giao dịch: " + request.getParameter("vnp_TransactionStatus"));

            if (signValue.equals(vnp_SecureHash)) {
                  if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                        return 1;
                  } else {
                        return 0;
                  }
            } else {
                  return -1;
            }

      }

}
