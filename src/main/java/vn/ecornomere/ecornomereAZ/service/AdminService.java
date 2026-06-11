package vn.ecornomere.ecornomereAZ.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import vn.ecornomere.ecornomereAZ.dto.request.ProductSales;
import vn.ecornomere.ecornomereAZ.model.entity.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserService userService;

    private final ItemService itemService;

    public String getHomePageAdmin(Model model) {
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
            throw new  RuntimeException(e.getMessage());
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
            model.addAttribute("productNamesJson", "[]");
            model.addAttribute("productQuantitiesJson", "[]");
            e.printStackTrace(); // in log lỗi
            throw new  RuntimeException(e.getMessage());
        }

        // Ngày hiện tại
        model.addAttribute("currentDate", java.sql.Date.valueOf(LocalDate.now()));

        return "admin/dashboard/index_admin";
    }

}
