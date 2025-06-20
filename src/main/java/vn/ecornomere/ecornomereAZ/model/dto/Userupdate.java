package vn.ecornomere.ecornomereAZ.model.dto;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Userupdate implements Serializable {

      private long id;
      @NotBlank(message = "Email không được để trống")
      @Email(message = "Email không đúng định dạng")
      private String email;

      @NotBlank(message = "Mật khẩu không được để trống")
      @Size(min = 6, max = 100, message = "Mật khẩu phải từ 6 đến 100 ký tự")
      private String password;

      @NotBlank(message = "Họ tên không được để trống")
      @Size(max = 100, message = "Họ tên không được vượt quá 100 ký tự")
      private String fullName;

      @Size(max = 255, message = "Địa chỉ không được vượt quá 255 ký tự")
      private String address;
      @Pattern(regexp = "^\\d{10}$", message = "Số điện thoại phải là 10 chữ số")
      private String phone;

      private String avatar;

      public long getId() {
            return id;
      }

      public void setId(long id) {
            this.id = id;
      }

      public String getEmail() {
            return email;
      }

      public void setEmail(String email) {
            this.email = email;
      }

      public String getPassword() {
            return password;
      }

      public void setPassword(String password) {
            this.password = password;
      }

      public String getFullName() {
            return fullName;
      }

      public void setFullName(String fullName) {
            this.fullName = fullName;
      }

      public String getAddress() {
            return address;
      }

      public void setAddress(String address) {
            this.address = address;
      }

      public String getPhone() {
            return phone;
      }

      public void setPhone(String phone) {
            this.phone = phone;
      }

      public String getAvatar() {
            return avatar;
      }

      public void setAvatar(String avatar) {
            this.avatar = avatar;
      }

}
