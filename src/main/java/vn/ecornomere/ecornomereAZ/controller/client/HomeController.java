package vn.ecornomere.ecornomereAZ.controller.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.ecornomere.ecornomereAZ.dto.request.ForgotPasswordDTO;
import vn.ecornomere.ecornomereAZ.dto.response.OrderHistoryDTO;
import vn.ecornomere.ecornomereAZ.dto.request.RegisterDTO;
import vn.ecornomere.ecornomereAZ.dto.request.Userupdate;
import vn.ecornomere.ecornomereAZ.model.entity.Order;
import vn.ecornomere.ecornomereAZ.model.entity.User;
import vn.ecornomere.ecornomereAZ.service.ForgotPasswordService;

import vn.ecornomere.ecornomereAZ.service.HomeService;
import vn.ecornomere.ecornomereAZ.service.RoleService;
import vn.ecornomere.ecornomereAZ.service.UploadFile.TemporaryUpload;
import vn.ecornomere.ecornomereAZ.service.UserService;
import vn.ecornomere.ecornomereAZ.utils.UploadFile;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/register")
    public String ShowRegister(Model model) {
        model.addAttribute("newRegister", new RegisterDTO());
        return "client/authentication/register"; //
    }

    // Save
    @PostMapping("/register/create")
    public String createRegister(@Valid @ModelAttribute("newRegister") RegisterDTO registerDTO
            , BindingResult result
            , Model model) {
        return homeService.createRegisterClient(registerDTO, result, model);

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
        return homeService.processForgotPasswordClient(forgotPasswordDTO, result, model, redirectAttributes);

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
        return homeService.showUserupdateClient(model, request);
    }


    @PostMapping("/setting-user")
    public String SettingUser(@ModelAttribute("Userupdate") @Valid Userupdate userupdate,
                              BindingResult result, HttpServletRequest request) {
        return homeService.SettingUserClient(userupdate, result, request);
    }

    @GetMapping("/order-history")
    public String showOrderHistory(@RequestParam(name = "page", defaultValue = "0") String pageParam, Model model,
                                   HttpServletRequest request) {
        return homeService.showOrderHistoryClient(pageParam, model, request);

    }

    @PostMapping("/api/order-detail/cancel/{id}")
    @ResponseBody
    public ResponseEntity<?> cancelOrderDetailAjax(
            @PathVariable long id,
            HttpServletRequest request) {

        return homeService.cancelOrderDetailAjaxClient(id, request);

    }
}
