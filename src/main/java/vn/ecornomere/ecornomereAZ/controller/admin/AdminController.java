package vn.ecornomere.ecornomereAZ.controller.admin;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.ecornomere.ecornomereAZ.model.Order;
import vn.ecornomere.ecornomereAZ.model.dto.ProductSales;
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

        long countUser = userService.getCountUser();
        long countProduct = userService.getCountProduct();
        long countOrder = userService.getCountOrder();

        // Tính tổng tiền
        double totalRevenue = itemService.getAllOrder()
                .stream()
                .mapToDouble(Order::getTotalPriceaddShip)
                .sum();

        model.addAttribute("Countuser", countUser);
        model.addAttribute("Countproduct", countProduct);
        model.addAttribute("CountOrder", countOrder);
        model.addAttribute("sumorder_money", totalRevenue);

        List<Double> monthlyRevenue = userService.getMonthlyRevenue();
        ObjectMapper mapper1 = new ObjectMapper();
        String monthlyRevenueJson = "[]"; // default nếu lỗi
        try {
            monthlyRevenueJson = mapper1.writeValueAsString(monthlyRevenue);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // in log lỗi
        }
        model.addAttribute("monthlyRevenueJson", monthlyRevenueJson);

        // --- 3️ Tỷ lệ đơn hàng theo trạng thái ---
        int countPending = userService.countByStatus("PENDING");
        int countProcessing = userService.countByStatus("PROCESSING");
        int countCompleted = userService.countByStatus("COMPLETED");
        int countCancelled = userService.countByStatus("CANCELLED");

        model.addAttribute("countPending", countPending);
        model.addAttribute("countProcessing", countProcessing);
        model.addAttribute("countCompleted", countCompleted);
        model.addAttribute("countCancelled", countCancelled);

        List<ProductSales> topProducts = userService.getTop5Products();
        // Lấy tên sản phẩm
        List<String> productNames = topProducts.stream()
                .map(ps -> ps.getProduct().getName())
                .collect(Collectors.toList());

        // Lấy số lượng bán
        List<Long> productQuantities = topProducts.stream()
                .map(ProductSales::getTotalSold)
                .collect(Collectors.toList());
        ObjectMapper mapper2 = new ObjectMapper();

        try {
            model.addAttribute("productNamesJson",
                    mapper2.writeValueAsString(productNames));
            model.addAttribute("productQuantitiesJson",
                    mapper2.writeValueAsString(productQuantities));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            model.addAttribute("productNamesJson", "[]");
            model.addAttribute("productQuantitiesJson", "[]");
        }

        // Ngày hiện tại
        model.addAttribute("currentDate", java.sql.Date.valueOf(LocalDate.now()));

        return "admin/dashboard/index_admin";
    }

}
