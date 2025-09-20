package vn.ecornomere.ecornomereAZ.controller.admin;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.ecornomere.ecornomereAZ.service.ItemService;

import vn.ecornomere.ecornomereAZ.model.*;
import vn.ecornomere.ecornomereAZ.repository.OrderDetailRepository;
import vn.ecornomere.ecornomereAZ.repository.OrderRepository;

@Controller
public class OrderController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    // Hiển thị danh sách
    @GetMapping("/admin/order")
    public String getHomeOrder(@RequestParam(name = "page", defaultValue = "0") String pageParam, Model model) {
        int page = 0;
        int pageSize = 5;

        try {
            page = Integer.parseInt(pageParam);
            if (page < 0)
                page = 0;
        } catch (NumberFormatException e) {
            // Nếu người dùng nhập sai, mặc định về trang đầu
            page = 0;
        }
        Page<Order> orderPage = itemService.getOrderPaginated(page, pageSize);
        model.addAttribute("Listorder", orderPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orderPage.getTotalPages());
        return "admin/order/order_index";
    }

    // Hiển thị danh sách

    // Detail :
    @GetMapping("/admin/order/detail/{id}")
    public String showDetailForm(@PathVariable("id") Long id, Model model) {
        List<OrderDetail> listcart = itemService.getAllOrderdetail(id);
        Optional<Order> orderid = orderRepository.findById(id);
        Order order = orderid.get();
        model.addAttribute("orderId", order.getId());
        model.addAttribute("listOrderDetail", listcart);
        return "admin/order/order_detail"; //
    }

    // Hiển thị danh sách
    @GetMapping("/admin/order/edit/{id}")
    public String getEditOrder(@PathVariable("id") Long id, Model model) {
        Optional<Order> orderid = orderRepository.findById(id);
        Order order = orderid.get();
        // Danh sách trạng thái
        List<String> statusList = List.of("Pending", "Shipping", "Cancelled", "Completed");
        List<String> statuspaymentsList = List.of("Unpaid", "Paid");
        model.addAttribute("order", order);
        model.addAttribute("statusList", statusList);
        model.addAttribute("statuspaymentList", statuspaymentsList);
        return "admin/order/order_edit";
    }

    // Xử lý cập nhật
    @PostMapping("/admin/order-edit")
    public String editOrder(@ModelAttribute("order") Order order,
            RedirectAttributes redirectAttributes)
            throws IOException {
        Optional<Order> orderid = orderRepository.findById(order.getId());
        Order newoOrder = orderid.get();
        newoOrder.setStatus(order.getStatus());
        newoOrder.setPaymentStatus(order.getPaymentStatus());
        orderRepository.save(newoOrder);
        redirectAttributes.addFlashAttribute("successMessage", "Edit status thành công!");
        return "redirect:/admin/order"; // Sau khi lưu thì chuyển về danh sách Product
    }

    @GetMapping("/admin/order/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Order> orderid = orderRepository.findById(id);
        Order newoOrder = orderid.get();
        List<OrderDetail> listdetail = itemService.getAllOrderdetail(newoOrder.getId());
        for (OrderDetail or : listdetail) {
            orderDetailRepository.delete(or);
        }
        orderRepository.delete(newoOrder);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa Order  thành công!");
        return "redirect:/admin/order";

    }

}
