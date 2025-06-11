package vn.ecornomere.ecornomereAZ.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;
import vn.ecornomere.ecornomereAZ.model.User;
import vn.ecornomere.ecornomereAZ.model.dto.RegisterDTO;
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

}
