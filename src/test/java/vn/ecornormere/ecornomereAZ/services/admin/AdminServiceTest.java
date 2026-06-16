package vn.ecornormere.ecornomereAZ.services.admin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import vn.ecornomere.ecornomereAZ.dto.request.ProductSales;
import vn.ecornomere.ecornomereAZ.model.entity.Order;
import vn.ecornomere.ecornomereAZ.model.entity.Product;
import vn.ecornomere.ecornomereAZ.service.AdminService;
import vn.ecornomere.ecornomereAZ.service.ItemService;
import vn.ecornomere.ecornomereAZ.service.UserService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

//kiểm thử đơn vị (Unit Test) trong Java, sử dụng framework JUnit 5 kết hợp với Mockito
//cấu trúc kinh điển trong Unit Test: Given - When - Then (Giả lập -> Thực thi -> Kiểm tra).
@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private ItemService itemService;
    @Mock
    private Model model;
    @InjectMocks
    private AdminService adminService;

    @Test
    void getHomePageAdmin_ShouldReturnDashboardView() {

        when(userService.getCountUser()).thenReturn(10L);
        when(userService.getCountProduct()).thenReturn(20L);
        when(userService.getCountOrder()).thenReturn(30L);
        when(itemService.getAllOrder()).thenReturn(List.of());
        when(userService.getMonthlyRevenue())
                .thenReturn(List.of(100.0, 200.0));
        when(userService.countByStatus(anyString()))
                .thenReturn(1);
        when(userService.getTop5Products())
                .thenReturn(List.of());
        String result = adminService.getHomePageAdmin(model);
        assertThat(result)
                .isEqualTo("admin/dashboard/index_admin");
    }
    @Test
    void getHomePageAdmin_ShouldCalculateTotalRevenueCorrectly() {
        Order o1 = mock(Order.class);
        Order o2 = mock(Order.class);
        Order o3 = mock(Order.class);
        when(o1.getTotalPriceaddShip()).thenReturn(100.0);
        when(o2.getTotalPriceaddShip()).thenReturn(200.0);
        when(o3.getTotalPriceaddShip()).thenReturn(500.0);
        when(itemService.getAllOrder())
                .thenReturn(List.of(o1, o2, o3));
        when(userService.getCountUser()).thenReturn(1L);
        when(userService.getCountProduct()).thenReturn(1L);
        when(userService.getCountOrder()).thenReturn(1L);
        when(userService.getMonthlyRevenue()).thenReturn(List.of());
        when(userService.countByStatus(anyString())).thenReturn(0);
        when(userService.getTop5Products()).thenReturn(List.of());
        adminService.getHomePageAdmin(model);
        verify(model).addAttribute("sumorder_money", 800.0);
    }
    @Test
    void getHomePageAdmin_ShouldAddCountAttributesToModel() {
        when(userService.getCountUser()).thenReturn(50L);
        when(userService.getCountProduct()).thenReturn(100L);
        when(userService.getCountOrder()).thenReturn(150L);
        when(itemService.getAllOrder()).thenReturn(List.of());
        when(userService.getMonthlyRevenue()).thenReturn(List.of());
        when(userService.countByStatus(anyString())).thenReturn(0);
        when(userService.getTop5Products()).thenReturn(List.of());
        adminService.getHomePageAdmin(model);
        verify(model).addAttribute("Countuser", 50L);
        verify(model).addAttribute("Countproduct", 100L);
        verify(model).addAttribute("CountOrder", 150L);
    }
    @Test
    void getHomePageAdmin_ShouldConvertMonthlyRevenueToJson() {

        when(userService.getCountUser()).thenReturn(1L);
        when(userService.getCountProduct()).thenReturn(1L);
        when(userService.getCountOrder()).thenReturn(1L);
        when(itemService.getAllOrder()).thenReturn(List.of());
        when(userService.getMonthlyRevenue()).thenReturn(List.of(100.0, 200.0, 300.0));
        when(userService.countByStatus(anyString())).thenReturn(0);
        when(userService.getTop5Products()).thenReturn(List.of());
        adminService.getHomePageAdmin(model);
        verify(model)
                .addAttribute("monthlyRevenueJson",
                        "[100.0,200.0,300.0]");
    }
    @Test
    void getHomePageAdmin_ShouldAddOrderStatusCounts() {
        when(userService.getCountUser()).thenReturn(1L);
        when(userService.getCountProduct()).thenReturn(1L);
        when(userService.getCountOrder()).thenReturn(1L);
        when(itemService.getAllOrder()).thenReturn(List.of());
        when(userService.getMonthlyRevenue()).thenReturn(List.of());
        when(userService.countByStatus("PENDING")).thenReturn(5);
        when(userService.countByStatus("PROCESSING")).thenReturn(3);
        when(userService.countByStatus("COMPLETED")).thenReturn(10);
        when(userService.countByStatus("CANCELLED")).thenReturn(2);
        when(userService.getTop5Products()).thenReturn(List.of());
        adminService.getHomePageAdmin(model);
        verify(model).addAttribute("countPending", 5);
        verify(model).addAttribute("countProcessing", 3);
        verify(model).addAttribute("countCompleted", 10);
        verify(model).addAttribute("countCancelled", 2);
    }
    @Test
    void getHomePageAdmin_ShouldAddTopProductData() {
        Product product = mock(Product.class);
        when(product.getName()).thenReturn("Iphone 15");
        ProductSales sales = mock(ProductSales.class);
        when(sales.getProduct()).thenReturn(product);
        when(sales.getTotalSold()).thenReturn(100L);
        when(userService.getCountUser()).thenReturn(1L);
        when(userService.getCountProduct()).thenReturn(1L);
        when(userService.getCountOrder()).thenReturn(1L);
        when(itemService.getAllOrder()).thenReturn(List.of());
        when(userService.getMonthlyRevenue()).thenReturn(List.of());
        when(userService.countByStatus(anyString())).thenReturn(0);
        when(userService.getTop5Products()).thenReturn(List.of(sales));
        adminService.getHomePageAdmin(model);
        verify(model).addAttribute("productNamesJson",
                        "[\"Iphone 15\"]");
        verify(model).addAttribute("productQuantitiesJson",
                        "[100]");
    }
    @Test
    void getHomePageAdmin_ShouldHandleEmptyData() {
        when(userService.getCountUser()).thenReturn(0L);
        when(userService.getCountProduct()).thenReturn(0L);
        when(userService.getCountOrder()).thenReturn(0L);
        when(itemService.getAllOrder()).thenReturn(List.of());
        when(userService.getMonthlyRevenue()).thenReturn(List.of());
        when(userService.getTop5Products()).thenReturn(List.of());
        when(userService.countByStatus(anyString())).thenReturn(0);
        String result = adminService.getHomePageAdmin(model);
        assertThat(result).isEqualTo("admin/dashboard/index_admin");
        verify(model).addAttribute("sumorder_money", 0.0);
    }
    @Test
    void getHomePageAdmin_ShouldThrowException_WhenUserServiceFails() {
        when(userService.getCountUser()).thenThrow(new RuntimeException("Database error"));
        assertThatThrownBy(() ->
                adminService.getHomePageAdmin(model))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Database error");
    }





}
