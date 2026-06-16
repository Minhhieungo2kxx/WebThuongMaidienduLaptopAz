package vn.ecornormere.ecornomereAZ.services.admin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.ecornomere.ecornomereAZ.dto.response.OrderHistoryDTO;
import vn.ecornomere.ecornomereAZ.model.entity.Order;
import vn.ecornomere.ecornomereAZ.model.entity.OrderDetail;
import vn.ecornomere.ecornomereAZ.repository.OrderDetailRepository;
import vn.ecornomere.ecornomereAZ.repository.OrderRepository;
import vn.ecornomere.ecornomereAZ.service.ItemService;
import vn.ecornomere.ecornomereAZ.service.OrderService;
import vn.ecornomere.ecornomereAZ.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private ItemService itemService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private OrderService orderService;

    @Test
    void getHomeOrderAdmin_ShouldReturnOrderIndexView_WhenPageIsValid() {

        Order order = new Order();
        order.setId(1L);
        OrderHistoryDTO dto = new OrderHistoryDTO();
        Page<Order> page = new PageImpl<>(List.of(order));
        when(itemService.getOrderPaginated(0, 5)).thenReturn(page);
        when(userService.toDTO(order)).thenReturn(dto);
        String result = orderService.getHomeOrderAdmin("0", model);
        assertEquals("admin/order/order_index", result);
        verify(model).addAttribute(eq("Listorder"), any());
        verify(model).addAttribute("currentPage", 0);
        verify(model).addAttribute("totalPages", 1);
    }
    @Test
    void getHomeOrderAdmin_ShouldDefaultToPageZero_WhenPageIsInvalid() {
        Page<Order> page = new PageImpl<>(Collections.emptyList());
        when(itemService.getOrderPaginated(0,5)).thenReturn(page);
        String result = orderService.getHomeOrderAdmin("abc", model);
        assertEquals("admin/order/order_index", result);
        verify(itemService).getOrderPaginated(0,5);
    }
    @Test
    void getHomeOrderAdmin_ShouldDefaultToZero_WhenPageIsNegative() {
        Page<Order> page = new PageImpl<>(Collections.emptyList());
        when(itemService.getOrderPaginated(0,5)).thenReturn(page);
        orderService.getHomeOrderAdmin("-5", model);
        verify(itemService).getOrderPaginated(0,5);
    }
    @Test
    void showDetailFormAdmin_ShouldReturnOrderDetailView() {

        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);
        List<OrderDetail> details = List.of(new OrderDetail());
        when(itemService.getAllOrderdetail(orderId)).thenReturn(details);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        String result = orderService.showDetailFormAdmin(orderId, model);
        assertEquals("admin/order/order_detail", result);
        verify(model).addAttribute("orderId", orderId);
        verify(model).addAttribute("listOrderDetail", details);
    }
    @Test
    void showDetailFormAdmin_ShouldThrowException_WhenOrderNotFound() {
        when(orderRepository.findById(10L))
                .thenReturn(Optional.empty());
        assertThrows(
                NoSuchElementException.class,
                () -> orderService.showDetailFormAdmin(10L, model)
        );
    }
    @Test
    void getEditOrderAdmin_ShouldLoadOrderAndStatusList() {

        Order order = new Order();
        order.setId(1L);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        String result = orderService.getEditOrderAdmin(1L, model);
        assertEquals("admin/order/order_edit", result);
        verify(model).addAttribute("order", order);
        verify(model).addAttribute(eq("statusList"), any());
    }
    @Test
    void editOrderAdmin_ShouldUpdateStatusSuccessfully() {

        Order dbOrder = new Order();
        dbOrder.setId(1L);
        Order request = new Order();
        request.setId(1L);
        request.setStatus("COMPLETED");
        request.setPaymentStatus("Paid");
        when(orderRepository.findById(1L)).thenReturn(Optional.of(dbOrder));

        String result = orderService.editOrderAdmin(request, redirectAttributes);
        assertEquals("redirect:/admin/order", result);
        assertEquals("COMPLETED", dbOrder.getStatus());
        assertEquals("Paid", dbOrder.getPaymentStatus());
        verify(orderRepository).save(dbOrder);
        verify(redirectAttributes).addFlashAttribute(
                        "successMessage",
                        "Edit status thành công!"
                );
    }
    @Test
    void editOrderAdmin_ShouldThrowException_WhenOrderNotFound() {
        Order request = new Order();
        request.setId(1L);
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> orderService.editOrderAdmin(
                        request,
                        redirectAttributes
                )
        );
    }
    @Test
    void deleteProductAdmin_ShouldDeleteOrderAndDetails() {

        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);
        OrderDetail d1 = new OrderDetail();
        OrderDetail d2 = new OrderDetail();
        List<OrderDetail> details = List.of(d1, d2);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        when(itemService.getAllOrderdetail(orderId)).thenReturn(details);

        String result = orderService.deleteProductAdmin(
                        orderId,
                        redirectAttributes
                );
        assertEquals(
                "redirect:/admin/order",
                result
        );
        verify(orderDetailRepository).delete(d1);
        verify(orderDetailRepository).delete(d2);
        verify(orderRepository).delete(order);
    }
    @Test
    void deleteProductAdmin_ShouldDeleteOrder_WhenNoOrderDetails() {

        Long orderId = 8386L;
        Order order = new Order();
        order.setId(orderId);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        when(itemService.getAllOrderdetail(orderId)).thenReturn(Collections.emptyList());
        orderService.deleteProductAdmin(
                orderId,
                redirectAttributes
        );
        verify(orderRepository).delete(order);

        verify(orderDetailRepository,
                never())
                .delete(any());
    }


}
