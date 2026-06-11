package vn.ecornomere.ecornomereAZ.controller.admin;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.ecornomere.ecornomereAZ.dto.response.OrderHistoryDTO;
import vn.ecornomere.ecornomereAZ.service.ItemService;

import vn.ecornomere.ecornomereAZ.model.entity.Order;
import vn.ecornomere.ecornomereAZ.model.entity.OrderDetail;
import vn.ecornomere.ecornomereAZ.repository.OrderDetailRepository;
import vn.ecornomere.ecornomereAZ.repository.OrderRepository;
import vn.ecornomere.ecornomereAZ.service.OrderService;
import vn.ecornomere.ecornomereAZ.service.UserService;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // Hiển thị danh sách
    @GetMapping("/admin/order")
    public String getHomeOrder(@RequestParam(name = "page", defaultValue = "0") String pageParam, Model model) {
        return orderService.getHomeOrderAdmin(pageParam,model);
    }


    // Detail :
    @GetMapping("/admin/order/detail/{id}")
    public String showDetailForm(@PathVariable("id") Long id, Model model) {
        return orderService.showDetailFormAdmin(id,model);
    }

    // Hiển thị danh sách
    @GetMapping("/admin/order/edit/{id}")
    public String getEditOrder(@PathVariable Long id, Model model) {
        return orderService.getEditOrderAdmin(id,model);
    }

    // Xử lý cập nhật
    @PostMapping("/admin/order-edit")
    public String editOrder(@ModelAttribute("order") Order order,
            RedirectAttributes redirectAttributes) {
        return orderService.editOrderAdmin(order,redirectAttributes);
    }

    @GetMapping("/admin/order/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
       return orderService.deleteProductAdmin(id,redirectAttributes);
    }

}
