package vn.ecornomere.ecornomereAZ.controller.client;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.ecornomere.ecornomereAZ.model.Order;
import vn.ecornomere.ecornomereAZ.model.OrderDetail;
import vn.ecornomere.ecornomereAZ.model.Role;
import vn.ecornomere.ecornomereAZ.model.User;
import vn.ecornomere.ecornomereAZ.model.dto.ForgotPasswordDTO;
import vn.ecornomere.ecornomereAZ.model.dto.RegisterDTO;
import vn.ecornomere.ecornomereAZ.model.dto.Userupdate;
import vn.ecornomere.ecornomereAZ.service.ForgotPasswordService;
import vn.ecornomere.ecornomereAZ.service.ItemService;
import vn.ecornomere.ecornomereAZ.service.RoleService;
import vn.ecornomere.ecornomereAZ.service.UserService;
import vn.ecornomere.ecornomereAZ.utils.UploadFile;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ForgotPasswordService forgotPasswordService;
    private UploadFile uploadFile = new UploadFile();

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

    @GetMapping("/news")
    public String showNews(Model model) {

        return "client/homepage/news";
    }

    @GetMapping("/contact")
    public String showContact(Model model) {
        return "client/homepage/contact";
    }

    @GetMapping("/review")
    public String showReview(Model model) {
        return "client/homepage/review";
    }

    @GetMapping("/update-user")
    public String showUserupdate(Model model, HttpServletRequest request) {
        // Set thông tin vào session
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        User olduser = userService.getbyEmail(email);
        Userupdate userupdate = new Userupdate();
        userupdate.setId(olduser.getId());
        userupdate.setFullName(olduser.getFullName());
        userupdate.setEmail(olduser.getEmail());
        userupdate.setPassword(olduser.getPassword());
        userupdate.setAddress(olduser.getAddress());
        userupdate.setPhone(olduser.getPhone());
        userupdate.setAvatar(olduser.getAvatar());
        model.addAttribute("Userupdate", userupdate);
        return "client/authentication/updateuser";
    }

    @PostMapping("/setting-user")
    public String SettingUser(
            @ModelAttribute("Userupdate") @Valid Userupdate userupdate, BindingResult result,
            @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
            HttpServletRequest request) throws IOException {

        // Kiểm tra lỗi validation
        if (result.hasErrors()) {
            return "client/authentication/updateuser"; // Trả về form với lỗi
        }
        User newUser = userService.getbyEmail(userupdate.getEmail().trim());
        newUser.setFullName(userupdate.getFullName());
        newUser.setAddress(userupdate.getAddress());
        newUser.setPhone(userupdate.getPhone());

        // Mã hóa mật khẩu trước khi lưu
        if (userupdate.getPasswordnew() != null && !userupdate.getPasswordnew().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(userupdate.getPasswordnew());
            newUser.setPassword(encodedPassword);
        }
        if (!avatarFile.isEmpty()) {
            newUser.setAvatar(uploadFile.getnameFile(avatarFile, "avatars"));

        } else {
            // Người dùng không chọn ảnh mới → giữ ảnh cũ
            User oldUser = userService.findUserById(userupdate.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userupdate.getId()));
            newUser.setAvatar(oldUser.getAvatar());
        }

        userService.handleSaveUser(newUser);
        HttpSession session = request.getSession(false);
        session.setAttribute("avatar", newUser.getAvatar().trim());

        return "redirect:/"; // Sau khi lưu thì chuyển về danh sách user
    }

    @GetMapping("/order-history")
    public String showOrderHistory(Model model, HttpServletRequest request) {
        // Set thông tin vào session
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        User user = userService.getbyEmail(email);
        List<Order> listOrder = user.getOrders();
        model.addAttribute("listOrderbyUser", listOrder);
        return "client/cart/orderhistory";
    }

}
