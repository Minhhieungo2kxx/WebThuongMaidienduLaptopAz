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

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    private final UploadFile uploadFile = new UploadFile();

    private final TemporaryUpload temporaryUpload;

    // Detail User:

    @GetMapping("/admin/user/detail/{id}")
    public String showDetailForm(@PathVariable("id") Long id, Model model) {
        User userdetail = userService.findUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));
        model.addAttribute("detailUser", userdetail);
        return "admin/user/detailuser"; //
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

        User existingUser = userService.findUserById(updatedUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id"));

        String oldPublicId = existingUser.getAvatarPublicId();

        ModelMapper map = new ModelMapper();
        map.typeMap(User.class, User.class)
                .addMappings(m -> m.skip(User::setId))
                .addMappings(m -> m.skip(User::setPassword))
                .addMappings(m -> m.skip(User::setReviews))
                .addMappings(m -> m.skip(User::setStocks))
                .addMappings(m -> m.skip(User::setChatMessages))
                .addMappings(m -> m.skip(User::setRole));

        map.map(updatedUser, existingUser);
        // password
        if (updatedUser.getPassword() != null
                && !updatedUser.getPassword().isBlank()) {

            if (!updatedUser.getPassword().equals(existingUser.getPassword())) {

                existingUser.setPassword(
                        passwordEncoder.encode(updatedUser.getPassword()));
            }
        }

        // avatar update
        if (updatedUser.getAvatarPublicId() != null &&
                !updatedUser.getAvatarPublicId().isEmpty()) {

            existingUser.setAvatar(updatedUser.getAvatar());
            existingUser.setAvatarPublicId(updatedUser.getAvatarPublicId());
            existingUser.setAvatarResourceType(updatedUser.getAvatarResourceType());

            // đánh dấu file cũ là unused
            temporaryUpload.markAsUnused(oldPublicId);
        }
        userService.handleSaveUser(existingUser);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thành công!");
        return "redirect:/admin/list/user";
    }

    // Hiển thị danh sách user
    @GetMapping("/admin/list/user")
    public String listUsers(@RequestParam(name = "page", defaultValue = "0") String pageParam, Model model) {
        int page = 0;
        int pageSize = 5;
        try {
            page = Integer.parseInt(pageParam);
            if (page < 0)
                page = 0;
        } catch (NumberFormatException e) {
            // Nếu người dùng nhập sai, mặc định về trang đầu
            page = 0;
        }
        Page<User> userPage = userService.getUserPaginated(page, pageSize);
        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        return "admin/user/listuser"; // tạo 1 file JSP hiển thị danh sách
    }

    // Save user
    @GetMapping("/admin/user/create")
    public String showCreateForm(Model model) {
        model.addAttribute("newuser", new User());
        return "admin/user/create"; // Tên JSP: create_user.jsp
    }


    @PostMapping("/admin/user/create")
    public String createUser(
            @ModelAttribute("newuser")
            @Valid User user,
            BindingResult result,
            @RequestParam("role_id") Long roleId) {

        if (result.hasErrors()) {
            return "admin/user/create";
        }

        Role role = roleService.findRoleId(roleId);

        if (role == null) {
            throw new IllegalArgumentException(
                    "Invalid role id : " + roleId);
        }

        user.setRole(role);

        if (user.getPassword() != null
                && !user.getPassword().isEmpty()) {

            user.setPassword(
                    passwordEncoder.encode(
                            user.getPassword()));
        }

        userService.handleSaveUser(user);
        temporaryUpload.markAsUsed(user.getAvatarPublicId());

        return "redirect:/admin/list/user";
    }

}
