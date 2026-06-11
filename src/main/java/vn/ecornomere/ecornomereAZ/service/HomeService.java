package vn.ecornomere.ecornomereAZ.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.ecornomere.ecornomereAZ.dto.request.ForgotPasswordDTO;
import vn.ecornomere.ecornomereAZ.dto.request.RegisterDTO;
import vn.ecornomere.ecornomereAZ.dto.request.Userupdate;
import vn.ecornomere.ecornomereAZ.dto.response.OrderHistoryDTO;
import vn.ecornomere.ecornomereAZ.model.entity.Order;
import vn.ecornomere.ecornomereAZ.model.entity.User;
import vn.ecornomere.ecornomereAZ.service.UploadFile.TemporaryUpload;
import vn.ecornomere.ecornomereAZ.utils.UploadFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class HomeService {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    private final ForgotPasswordService forgotPasswordService;
    private final TemporaryUpload temporaryUpload;
    private final UploadFile uploadFile = new UploadFile();

    @Transactional
    public String createRegisterClient(RegisterDTO registerDTO, BindingResult result, Model model) {
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

    @Transactional
    public String processForgotPasswordClient(ForgotPasswordDTO forgotPasswordDTO, BindingResult result,
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


    public String showUserupdateClient(Model model, HttpServletRequest request) {
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
    @Transactional
    public String SettingUserClient( Userupdate userupdate, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return "client/authentication/updateuser";
        }
        User user = userService.getbyEmail(userupdate.getEmail().trim());
        user.setFullName(userupdate.getFullName());
        user.setPhone(userupdate.getPhone());
        user.setAddress(userupdate.getAddress());
        if (userupdate.getPassword() != null && !userupdate.getPassword().isBlank()) {
            if (!userupdate.getPassword().equals(user.getPassword())) {
                user.setPassword(
                        passwordEncoder.encode(userupdate.getPassword()));
            }
        }

        if (userupdate.getAvatarPublicId() != null && !userupdate.getAvatarPublicId().isBlank()) {
            String oldPublicId = user.getAvatarPublicId();
            if (oldPublicId != null) {
                temporaryUpload.markAsUnused(oldPublicId);
            }
            user.setAvatar(userupdate.getAvatar());
            user.setAvatarPublicId(userupdate.getAvatarPublicId());
            user.setAvatarResourceType(userupdate.getAvatarResourceType());
            temporaryUpload.markAsUsed(userupdate.getAvatarPublicId());
        }
        userService.handleSaveUser(user);
        HttpSession session = request.getSession(false);
        session.setAttribute("avatar", user.getAvatar());
        return "redirect:/";
    }
    public String showOrderHistoryClient(String pageParam, Model model, HttpServletRequest request) {
        int page = 0;
        int pageSize = 6;

        try {
            page = Integer.parseInt(pageParam);
            if (page < 0)
                page = 0;
        } catch (NumberFormatException e) {
            // Nếu người dùng nhập sai, mặc định về trang đầu
            page = 0;
        }
        // Set thông tin vào session
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        User user = userService.getbyEmail(email);
        Page<Order> orderlistPage = userService.getlistHistory(user, page, pageSize);

        List<OrderHistoryDTO> dtoList = orderlistPage.getContent()
                .stream()
                .map(order -> userService.toDTO(order))
                .toList();
        model.addAttribute("listOrderbyUser", dtoList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orderlistPage.getTotalPages());
        return "client/cart/orderhistory";

    }
    @Transactional
    public ResponseEntity<?> cancelOrderDetailAjaxClient(long id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        User user = userService.getbyEmail(email);
        try {
            Map<String, Object> result = userService.cancelOrderDetailAjax(id, user.getId());
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
