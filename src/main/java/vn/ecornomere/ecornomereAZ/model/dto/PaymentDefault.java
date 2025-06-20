package vn.ecornomere.ecornomereAZ.model.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PaymentDefault implements Serializable {

      @NotBlank(message = "Họ tên không được để trống")
      @Size(max = 100, message = "Họ tên không được vượt quá 100 ký tự")
      private String receiverName;

      @NotBlank(message = "Tên địa chỉ không được để trống")
      @Size(max = 100, message = "Họ tên không được vượt quá 100 ký tự")
      private String receiverAddress;

      @Pattern(regexp = "^\\d{10}$", message = "Số điện thoại phải là 10 chữ số")
      private String receiverPhone;

      public String getReceiverName() {
            return receiverName;
      }

      public void setReceiverName(String receiverName) {
            this.receiverName = receiverName;
      }

      public String getReceiverAddress() {
            return receiverAddress;
      }

      public void setReceiverAddress(String receiverAddress) {
            this.receiverAddress = receiverAddress;
      }

      public String getReceiverPhone() {
            return receiverPhone;
      }

      public void setReceiverPhone(String receiverPhone) {
            this.receiverPhone = receiverPhone;
      }

}
