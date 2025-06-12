package vn.ecornomere.ecornomereAZ.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import vn.ecornomere.ecornomereAZ.model.User;
import vn.ecornomere.ecornomereAZ.model.dto.ForgotPasswordDTO;
import vn.ecornomere.ecornomereAZ.model.dto.RegisterDTO;
import vn.ecornomere.ecornomereAZ.service.ForgotPasswordService;
import vn.ecornomere.ecornomereAZ.service.RoleService;
import vn.ecornomere.ecornomereAZ.service.UserService;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @GetMapping("/register")
    public String ShowRegister(Model model) {
        model.addAttribute("newRegister", new RegisterDTO());
        return "client/authentication/register"; //
    }

    // Save
    @PostMapping("/register/create")
    public String createRegister(
            @Valid @ModelAttribute("newRegister") RegisterDTO registerDTO, BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("newRegister", registerDTO);
            return "client/authentication/register"; // Trả về form với lỗi
        }
        User user = userService.registertoDTO(registerDTO);
        // Mã hóa mật khẩu trước khi lưu
        if (registerDTO.getPassword() != null && !registerDTO.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(registerDTO.getPassword());
            user.setPassword(encodedPassword);
        }
        user.setRole(roleService.findRoleByName("USER"));
        userService.handleSaveUser(user);
        return "redirect:/login"; // Sau khi lưu thì chuyển về
    }

    // Hiển thị danh sách user
    @GetMapping("/login")
    public String listUsers(Model model) {
        return "client/authentication/login";
    }

    // Hiển thị danh sách user
    @GetMapping("/denyaccess")
    public String getDenytAccess(Model model) {
        return "client/authentication/denyAccess";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("forgotPasswordDTO", new ForgotPasswordDTO());
        return "client/authentication/forgot-password";
    }

    // gui email forget password
    @PostMapping("/forgot-password")
    public String processForgotPassword(@Valid @ModelAttribute("forgotPasswordDTO") ForgotPasswordDTO forgotPasswordDTO,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "client/authentication/forgot-password";
        }

        boolean success = forgotPasswordService.processForgotPassword(forgotPasswordDTO.getEmail());

        if (success) {

            model.addAttribute("successMessage",
                    "Mật khẩu mới đã được gửi đến email của bạn. Vui lòng kiểm tra hộp thư.");
            return "client/authentication/forgot-password";
        } else {
            model.addAttribute("errorMessage", "Email không tồn tại trong hệ thống.");
            return "client/authentication/forgot-password";
        }
    }

}
