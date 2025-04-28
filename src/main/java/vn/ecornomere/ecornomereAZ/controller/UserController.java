package vn.ecornomere.ecornomereAZ.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.ecornomere.ecornomereAZ.model.User;
import vn.ecornomere.ecornomereAZ.service.UserService;

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

  @GetMapping("/")
  public String getHomePage(Model model) {
    var userArr = this.userService.findAllUsersByEmailAndAddress("asawholeandlove@gmail.com", "hn");
    System.out.println(userArr);

    model.addAttribute("data", "test");
    return "home";
  }

  // List user
  @GetMapping("/admin/user")
  public String getUserPage(Model model) {
    var users = this.userService.findAllUsers();
    model.addAttribute("users", users);
    return "admin/user/table-user";
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
  public String updateUser(@ModelAttribute("updatedUser") User updatedUser) {
    User existingUser = userService.findUserById(updatedUser.getId())
        .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + updatedUser.getId()));
    existingUser.setEmail(updatedUser.getEmail());
    existingUser.setPassword(updatedUser.getPassword());
    existingUser.setPhone(updatedUser.getPhone());
    existingUser.setFullName(updatedUser.getFullName());
    existingUser.setAddress(updatedUser.getAddress());
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

  // Khi submit form
  @PostMapping("/admin/user/create")
  public String createUser(@ModelAttribute("newuser") User adduser) {
    userService.handleSaveUser(adduser);
    System.out.println("Ok nha da save " + adduser);
    return "redirect:/admin/list/user"; // Sau khi lưu thì chuyển về home.jsp
  }

}
