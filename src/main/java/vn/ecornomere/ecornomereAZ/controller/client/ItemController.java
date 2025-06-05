package vn.ecornomere.ecornomereAZ.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ItemController {
    @GetMapping("/product/{id}")
    public String ShowDetailItem(@PathVariable Long id, Model model) {
        return "client/product/Detailproduct"; //
    }

}
