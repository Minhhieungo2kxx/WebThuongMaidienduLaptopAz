package vn.ecornomere.ecornomereAZ.controller.admin;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import vn.ecornomere.ecornomereAZ.model.User;
import vn.ecornomere.ecornomereAZ.service.UserService;
import vn.ecornomere.ecornomereAZ.utils.UploadFile;
import vn.ecornomere.ecornomereAZ.service.RoleService;

import vn.ecornomere.ecornomereAZ.model.Role;

@Controller
// @PropertySource("classpath:application.properties")
public class UserController {
  // dung cai nay se testcase de hon thay vi dung @autowired
  // private final UserService userService;
  // public UserController(UserService userService) {
  // this.userService = userService;
  // }
  @Autowired
  private UserService userService;
  @Autowired
  private RoleService roleService;
  @Autowired
  private PasswordEncoder passwordEncoder;

  private UploadFile uploadFile = new UploadFile();

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

  // Xử lý cập nhật
  @PostMapping("/admin/user/update")
  public String updateUser(@ModelAttribute("updatedUser") User updatedUser,
      @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile)
      throws IOException {
    User existingUser = userService.findUserById(updatedUser.getId())
        .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + updatedUser.getId()));
    ModelMapper map = new ModelMapper();
    map.typeMap(User.class, User.class)
        .addMappings(mapper -> mapper.skip(
            User::setAvatar))
        .addMappings(mapper -> mapper.skip(User::setId))
        .addMappings(mapper -> mapper.skip(User::setPassword))
        .addMappings(mapper -> mapper.skip(User::setRole));

    map.map(updatedUser, existingUser);

    // Mã hóa mật khẩu trước khi lưu
    if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
      String encodedPassword = passwordEncoder.encode(updatedUser.getPassword());
      existingUser.setPassword(encodedPassword);
    }
    if (!avatarFile.isEmpty()) {
      existingUser.setAvatar(uploadFile.getnameFile(avatarFile, "avatars"));

    } else {
      // Người dùng không chọn ảnh mới → giữ ảnh cũ
      User oldUser = userService.findUserById(updatedUser.getId())
          .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + updatedUser.getId()));
      existingUser.setAvatar(oldUser.getAvatar());
    }

    userService.handleSaveUser(existingUser);
    return "redirect:/admin/list/user";

  }

  // Hiển thị danh sách user
  @GetMapping("/admin/list/user")
  public String listUsers(Model model) {
    model.addAttribute("users", userService.findAllUsers());
    return "admin/user/listuser"; // tạo 1 file JSP hiển thị danh sách
  }

  // Save user
  @GetMapping("/admin/user/create")
  public String showCreateForm(Model model) {
    model.addAttribute("newuser", new User());
    return "admin/user/create"; // Tên JSP: create_user.jsp
  }

  // Khi submit form Luu

  @PostMapping("/admin/user/create")
  public String createUser(
      @ModelAttribute("newuser") @Valid User user, BindingResult result,
      @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
      @RequestParam("role_id") Long role_id) throws IOException {

    // Kiểm tra lỗi validation
    if (result.hasErrors()) {
      return "admin/user/create"; // Trả về form với lỗi
    }

    // Ánh xạ role từ ID
    Role role = roleService.findRoleId(role_id);
    if (role == null) {
      throw new IllegalArgumentException("Invalid role Id: " + role_id);
    }
    user.setRole(role);

    // Mã hóa mật khẩu trước khi lưu
    if (user.getPassword() != null && !user.getPassword().isEmpty()) {
      String encodedPassword = passwordEncoder.encode(user.getPassword());
      user.setPassword(encodedPassword);
    }

    user.setAvatar(uploadFile.getnameFile(avatarFile, "avatars"));
    // Lưu user vào database
    userService.handleSaveUser(user);

    return "redirect:/admin/list/user"; // Sau khi lưu thì chuyển về danh sách user
  }

  // // Trong service hoặc controller đăng nhập giai ma
  // public boolean verifyPassword(String rawPassword, String encodedPassword) {
  // return passwordEncoder.matches(rawPassword, encodedPassword);
  // }

}
