package vn.ecornomere.ecornomereAZ.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {
    @GetMapping("/admin/order")
    public String getHomePage(Model model) {
        return "admin/order/order_index";
    }

}
