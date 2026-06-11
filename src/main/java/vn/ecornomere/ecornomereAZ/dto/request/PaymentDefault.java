package vn.ecornomere.ecornomereAZ.dto.request;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import vn.ecornomere.ecornomereAZ.enums.PaymentMethod;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDefault implements Serializable {

    @NotBlank(message = "Họ tên không được để trống")
    @Size(max = 100, message = "Họ tên không được vượt quá 100 ký tự")
    private String receiverName;

    @NotBlank(message = "Tên địa chỉ không được để trống")
    @Size(max = 100, message = "Họ tên không được vượt quá 100 ký tự")
    private String receiverAddress;

    @Pattern(regexp = "^\\d{10}$", message = "Số điện thoại phảis là 10 chữ số")
    private String receiverPhone;

    private Double summoney;

    private PaymentMethod paymentMethod;


}
