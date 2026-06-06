package vn.ecornomere.ecornomereAZ.model.dto;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Userupdate implements Serializable {

      private long id;
      @NotBlank(message = "Email không được để trống")
      @Email(message = "Email không đúng định dạng")
      private String email;

      @Size(min = 6, max = 100, message = "Mật khẩu phải từ 6 đến 100 ký tự")
      private String password;

      @NotBlank(message = "Họ tên không được để trống")
      @Size(max = 100, message = "Họ tên không được vượt quá 100 ký tự")
      private String fullName;

      @Size(max = 255, message = "Địa chỉ không được vượt quá 255 ký tự")
      private String address;
      @Pattern(regexp = "^\\d{10}$", message = "Số điện thoại phải là 10 chữ số")
      private String phone;
      // cloudinary
      private String avatar;

      private String avatarPublicId;

      private String avatarResourceType;



}
