package vn.ecornomere.ecornomereAZ.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/admin")
    public String getHomePage(Model model) {
        return "admin/dashboard/index_admin";
    }
}
