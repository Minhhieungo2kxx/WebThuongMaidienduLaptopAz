package vn.ecornomere.ecornomereAZ.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {
    @GetMapping("/admin/product")
    public String getHomePage(Model model) {
        return "admin/product/product_index";
    }

}
