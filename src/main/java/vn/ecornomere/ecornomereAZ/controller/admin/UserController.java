package vn.ecornomere.ecornomereAZ.controller.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.ecornomere.ecornomereAZ.model.User;
import vn.ecornomere.ecornomereAZ.service.UserService;
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
  RoleService roleService;

  @GetMapping("/login")
  public String getHomePage(Model model) {

    return "home";
  }

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
      throws IllegalStateException, IOException {
    User existingUser = userService.findUserById(updatedUser.getId())
        .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + updatedUser.getId()));
    existingUser.setEmail(updatedUser.getEmail());
    existingUser.setPassword(updatedUser.getPassword());
    existingUser.setPhone(updatedUser.getPhone());
    existingUser.setFullName(updatedUser.getFullName());
    existingUser.setAddress(updatedUser.getAddress());

    String uploadDir = "D:/Ngominhhieu/Back_end_java/spring-mvc-ecornomere-laptopaz/myapp/uploads/avatars";
    Path uploadPath = Paths.get(uploadDir);
    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath); // Tạo thư mục nếu chưa tồn tại
    }
    String fileName = avatarFile.getOriginalFilename();
    Path filePath = uploadPath.resolve(fileName);
    avatarFile.transferTo(filePath.toFile());
    existingUser.setAvatar(fileName);
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
  public String createUser(@Valid @ModelAttribute("newuser") User user,
      @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
      @RequestParam("role_id") Long role_id) throws IllegalStateException, IOException {

    // ánh xạ role từ ID
    Role role = roleService.findRoleId(role_id);
    if (role == null) {
      throw new IllegalArgumentException("Invalid role Id: " + role_id);
    }
    user.setRole(role);

    String uploadDir = "D:/Ngominhhieu/Back_end_java/spring-mvc-ecornomere-laptopaz/myapp/uploads/avatars";
    Path uploadPath = Paths.get(uploadDir);

    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath); // Tạo thư mục nếu chưa tồn tại
    }

    String fileName = avatarFile.getOriginalFilename();
    Path filePath = uploadPath.resolve(fileName);
    avatarFile.transferTo(filePath.toFile());

    user.setAvatar(fileName);
    userService.handleSaveUser(user);

    return "redirect:/admin/list/user"; // Sau khi lưu thì chuyển về listuser.jsp
  }

}
