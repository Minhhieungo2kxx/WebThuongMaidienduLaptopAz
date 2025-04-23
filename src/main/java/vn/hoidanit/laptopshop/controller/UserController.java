package vn.hoidanit.laptopshop.controller;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UserService;


@Controller
@PropertySource("classpath:application.properties")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

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

  // Detail user
  @GetMapping("/admin/user/{id}")
  public String getDetailUser(@PathVariable Long id) {
    System.out.println(id);
    return "home";
  }

  // Delete user
  @GetMapping("/admin/user/delete/{id}")
  public String deleteUser(@PathVariable Long id) {

   
  this.userService.findUserById(id).ifPresent(user -> {
    this.userService.deleteUser(user);
  });

  return "redirect:/admin/user";
  }

  // Update user
  @GetMapping("/admin/user/update/{id}")
  public String getUpdateUser(Model model, @PathVariable Long id) {
    var currentUser = this.userService.findUserById(id);
    
    model.addAttribute("updatedUser", currentUser);
    System.out.println(id);
    return "admin/user/update";
  }

  @PostMapping("/admin/user/update")
  public String updateUser(Model model, @ModelAttribute User updatedUser) {
    var dbUserOptional = this.userService.findUserById(updatedUser.getId());

    if (dbUserOptional.isPresent()) {
      var dbUser = dbUserOptional.get();
      dbUser.setAddress(updatedUser.getAddress());
      dbUser.setPhone(updatedUser.getPhone());
      dbUser.setFullName(updatedUser.getFullName());

      this.userService.handleSaveUser(dbUser);
    }

    return "redirect:/admin/user";
  }

  // Create user
  @GetMapping("/admin/user/create")
  public String getCreateUserPage(Model model) {
    model.addAttribute("newUser", new User());
    return "admin/user/create";
  }

  @PostMapping("/admin/user/create")
  public String createUser(Model model, @ModelAttribute User newUser) {
    this.userService.handleSaveUser(newUser);
    return "redirect:/admin/user";
  }

}
