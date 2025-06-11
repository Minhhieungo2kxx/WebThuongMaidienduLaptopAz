package vn.ecornomere.ecornomereAZ.model.dto;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import vn.ecornomere.ecornomereAZ.config.annotation.checkemail.UniqueEmail;
import vn.ecornomere.ecornomereAZ.config.annotation.checkpassword.PasswordMatch;

@PasswordMatch
public class RegisterDTO implements Serializable {
    @NotBlank(message = "Tên không được để trống")
    @Size(max = 50, message = "Tên không được vượt quá 50 ký tự")
    @Pattern(regexp = "^[a-zA-Z\\p{L}\\s]+$", message = "Tên chỉ được chứa chữ cái và dấu cách")
    private String firstName;

    @NotBlank(message = "Họ không được để trống")
    @Size(max = 50, message = "Họ không được vượt quá 50 ký tự")
    @Pattern(regexp = "^[a-zA-Z\\p{L}\\s]+$", message = "Họ chỉ được chứa chữ cái và dấu cách")
    private String lastName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    @Size(max = 100, message = "Email không được vượt quá 100 ký tự")
    @UniqueEmail(message = "Email đã tồn tại")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 8, max = 100, message = "Mật khẩu phải từ 8 đến 100 ký tự")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]+$", message = "Mật khẩu phải chứa ít nhất một chữ hoa, một chữ thường và một số")
    private String password;

    @NotBlank(message = "Xác nhận mật khẩu không được để trống")
    @Size(min = 8, max = 100, message = "Xác nhận mật khẩu phải từ 8 đến 100 ký tự")
    private String confirmpassword;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
