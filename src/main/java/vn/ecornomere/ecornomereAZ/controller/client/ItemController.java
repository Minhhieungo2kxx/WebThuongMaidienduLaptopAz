package vn.ecornomere.ecornomereAZ.controller.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vn.ecornomere.ecornomereAZ.model.Product;
import vn.ecornomere.ecornomereAZ.service.ItemService;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/product/{id}")
    public String ShowDetailItem(@PathVariable Long id, Model model) {
        return "client/product/Detailproduct"; //
    }

    @GetMapping("/")
    public String showHomePage(Model model) {
        // Lấy tất cả sản phẩm
        List<Product> allProducts = itemService.getAllItems();
        model.addAttribute("allProducts", allProducts);
        // Lấy sản phẩm theo từng loại
        model.addAttribute("gamingProducts", itemService.listNameItems("Gaming"));
        model.addAttribute("officeProducts", itemService.listNameItems("Van phong"));
        model.addAttribute("designProducts", itemService.listNameItems("Thiet ke do hoa"));
        List<String> targets = Arrays.asList("Mong nhe", "Doanh nhan");
        List<Product> filtered = itemService.getBytargetIn(targets);
        model.addAttribute("personalProducts", filtered);

        return "client/homepage/home";
    }

}
