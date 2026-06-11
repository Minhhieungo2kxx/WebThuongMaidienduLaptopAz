package vn.ecornomere.ecornomereAZ.controller.admin;

import java.io.IOException;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import vn.ecornomere.ecornomereAZ.model.entity.Role;
import vn.ecornomere.ecornomereAZ.model.entity.User;
import vn.ecornomere.ecornomereAZ.service.UploadFile.TemporaryUpload;
import vn.ecornomere.ecornomereAZ.service.UserService;
import vn.ecornomere.ecornomereAZ.utils.UploadFile;
import vn.ecornomere.ecornomereAZ.service.RoleService;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/admin/user/detail/{id}")
    public String showDetailForm(@PathVariable("id") Long id, Model model) {
       return userService.showDetailFormUser(id,model);
    }

    // Delete user
    @GetMapping("/admin/user/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.findUserById(id).ifPresent(userService::deleteUser);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa người dùng thành công!");
        return "redirect:/admin/list/user";
    }

    // Update User:
    // Hiển thị form cập nhật
    @GetMapping("/admin/user/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));
        model.addAttribute("updatedUser", user);
        return "admin/user/update"; // JSP tên update_user.jsp
    }

    @PostMapping("/admin/user/update")
    public String updateUser(
            @ModelAttribute("updatedUser") User updatedUser,
            RedirectAttributes redirectAttributes) {
        return userService.updateUserAD(updatedUser,redirectAttributes);
    }

    // Hiển thị danh sách user
    @GetMapping("/admin/list/user")
    public String listUsers(@RequestParam(name = "page", defaultValue = "0") String pageParam, Model model) {
        return userService.listUsersAd(pageParam,model);
    }

    // Save user
    @GetMapping("/admin/user/create")
    public String showCreateForm(Model model) {
        model.addAttribute("newuser", new User());
        return "admin/user/create"; // Tên JSP: create_user.jsp
    }


    @PostMapping("/admin/user/create")
    public String createUser(@ModelAttribute("newuser") @Valid User user
            ,BindingResult result,
            @RequestParam("role_id") Long roleId) {
        return userService.createUserAd(user,result,roleId);
    }

}
