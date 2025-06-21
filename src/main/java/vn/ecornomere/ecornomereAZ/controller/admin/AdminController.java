package vn.ecornomere.ecornomereAZ.controller.admin;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.ecornomere.ecornomereAZ.model.Order;
import vn.ecornomere.ecornomereAZ.service.ItemService;
import vn.ecornomere.ecornomereAZ.service.UserService;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;

    @GetMapping("/admin")
    public String getHomePage(Model model) {
        model.addAttribute("Countuser", userService.getCountUser());
        model.addAttribute("Countproduct", userService.getCountProduct());
        model.addAttribute("CountOrder", userService.getCountOrder());
        double summoney = 0;
        List<Order> listorder = itemService.getAllOrder();
        for (Order or : listorder) {
            summoney += or.getTotalPriceaddShip();
        }
        model.addAttribute("sumorder_money", summoney);
        model.addAttribute("currentDate", java.sql.Date.valueOf(LocalDate.now()));

        return "admin/dashboard/index_admin";
    }
}
