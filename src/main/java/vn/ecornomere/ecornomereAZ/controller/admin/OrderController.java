package vn.ecornomere.ecornomereAZ.controller.admin;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
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

import vn.ecornomere.ecornomereAZ.model.dto.OrderHistoryDTO;
import vn.ecornomere.ecornomereAZ.service.ItemService;

import vn.ecornomere.ecornomereAZ.model.entity.Order;
import vn.ecornomere.ecornomereAZ.model.entity.OrderDetail;
import vn.ecornomere.ecornomereAZ.repository.OrderDetailRepository;
import vn.ecornomere.ecornomereAZ.repository.OrderRepository;
import vn.ecornomere.ecornomereAZ.service.UserService;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final ItemService itemService;

    private final OrderRepository orderRepository;

    private final OrderDetailRepository orderDetailRepository;
    private final UserService userService;

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
        List<OrderHistoryDTO> dtoList = orderPage.getContent()
                .stream()
                .map(order -> userService.toDTO(order))
                .toList();
        model.addAttribute("Listorder",dtoList);
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
    public String getEditOrder(@PathVariable Long id, Model model) {

        Order order = orderRepository.findById(id).orElse(null);

        model.addAttribute("order", order);

        Map<String, String> statusList = new LinkedHashMap<>();
        statusList.put("PENDING", "PENDING");
        statusList.put("SHIPPING", "SHIPPING");
        statusList.put("CANCELLED", "CANCELLED");
        statusList.put("COMPLETED", "COMPLETED");

        model.addAttribute("statusList", statusList);

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
