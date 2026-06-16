package vn.ecornormere.ecornomereAZ.services.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import vn.ecornomere.ecornomereAZ.dto.request.MomoCallbackRequest;
import vn.ecornomere.ecornomereAZ.dto.request.PaymentDefault;
import vn.ecornomere.ecornomereAZ.enums.PaymentMethod;
import vn.ecornomere.ecornomereAZ.enums.PaymentTransactionStatus;
import vn.ecornomere.ecornomereAZ.model.entity.*;
import vn.ecornomere.ecornomereAZ.repository.*;
import vn.ecornomere.ecornomereAZ.service.ItemService;
import vn.ecornomere.ecornomereAZ.service.ProductService;
import vn.ecornomere.ecornomereAZ.service.UserService;
import vn.ecornomere.ecornomereAZ.service.payments.MomoService;
import vn.ecornomere.ecornomereAZ.service.payments.VNPayService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

//JUnit 5 + Mockito + AssertJ
//Mẫu AAA (Arrange - Act - Assert)
//Arrange: chuẩn bị dữ liệu giả.
//Act: gọi hàm cần test.
//Assert: kiểm tra kết quả và hành vi.
@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Spy
    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserService userService;

    @Mock
    private ProductService productService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartDetailRepository cartDetailRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private VNPayService vnPayService;

    @Mock
    private MomoService momoService;

    @Mock
    private PaymentTransactionRepository paymentTransactionRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    void showDetailItemClient_ShouldReturnDetailView_WhenProductExists() {

        Long productId = 1L;

        Product product = new Product();
        product.setId(productId);

        Model model = new ExtendedModelMap();
        when(productService.getProductbyId(productId)).thenReturn(Optional.of(product));
        String result = itemService.ShowDetailItemClient(productId, model, null);
        assertThat(result).isEqualTo("client/product/Detailproduct");
        assertThat(model.getAttribute("detailProduct")).isEqualTo(product);
    }
    @Test
    void showDetailItemClient_ShouldThrowException_WhenProductNotFound() {
        Long productId = 9999L;
        when(productService.getProductbyId(productId)).thenReturn(Optional.empty());
        assertThatThrownBy(() ->
                itemService.ShowDetailItemClient(
                        productId,
                        new ExtendedModelMap(),
                        null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid Product Id");
    }
    @Test
    void addCartItemClient_ShouldRedirectLogin_WhenEmailNull() {

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("email")).thenReturn(null);
        String result = itemService.AddCartItemClient(1L, request);
        assertThat(result).isEqualTo("redirect:/login");
    }
    @Test
    void addCartItemClient_ShouldAddItem_WhenLoggedIn() {

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("email")).thenReturn("test@gmail.com");
        doNothing().when(itemService).addCartItem(anyLong(), anyString(), any());
        String result = itemService.AddCartItemClient(1L, request);
        assertThat(result).isEqualTo("redirect:/");
        verify(itemService).addCartItem(eq(1L), eq("test@gmail.com"), eq(session));
    }
    @Test
    void showCartDetailClient_ShouldRedirectLogin_WhenNotLoggedIn() {

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("email")).thenReturn(null);

        String result = itemService.ShowCartDetailClient(new ExtendedModelMap(), request);
        assertThat(result).isEqualTo("redirect:/login");
    }
    @Test
    void showCartDetailClient_ShouldCalculateTotalPrice() {

        String email = "user@gmail.com";

        CartDetail item1 = new CartDetail();
        item1.setPrice(100);
        item1.setQuantity(2);

        CartDetail item2 = new CartDetail();
        item2.setPrice(200);
        item2.setQuantity(1);
        List<CartDetail> list = List.of(item1, item2);

        doReturn(list).when(itemService).getbyCartDetails(email);
        HttpSession session = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("email"))
                .thenReturn(email);
        Model model = new ExtendedModelMap();
        String view = itemService.ShowCartDetailClient(model, request);
        assertThat(view).isEqualTo("client/cart/cartdetails");
        assertThat(model.getAttribute("sumPrice")).isEqualTo(400.0);
    }
    @Test
    void deleteProductClient_ShouldRedirectLogin_WhenNoSession() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("email")).thenReturn(null);
        String result = itemService.deleteProductClient(1L, request, null);
        assertThat(result).isEqualTo("redirect:/login");
    }
    @Test
    void deleteProductClient_ShouldDeleteCartDetail() {

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("email")).thenReturn("abc@gmail.com");
//        ItemService spy = Mockito.spy(itemService);
        doNothing().when(itemService).deleteCartDetail(anyLong(), any());
        String result = itemService.deleteProductClient(1L, request, null);
        assertThat(result).isEqualTo("redirect:/cart");
        verify(itemService).deleteCartDetail(eq(1L), eq(session));
    }
    @Test
    void savePlaceOrder_ShouldRedirectLogin_WhenSessionNull() {

        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getSession(false)).thenReturn(null);

        String result = itemService.savePlaceOrderClient(new PaymentDefault(), mock(BindingResult.class), request);
        assertThat(result).isEqualTo("redirect:/login");
    }
    @Test
    void savePlaceOrder_ShouldReturnCheckout_WhenValidationFails() {

        HttpSession session = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("email")).thenReturn("user@gmail.com");
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);
//        ItemService spy = Mockito.spy(itemService);
        doNothing().when(itemService).loadCheckoutData(anyString(), any());
        String view = itemService.savePlaceOrderClient(new PaymentDefault(), result, request);
        assertThat(view).isEqualTo("client/cart/checkout");
    }
    @Test
    void savePlaceOrder_ShouldRedirectCart_WhenCartEmpty() {

        String email = "user@gmail.com";

        HttpSession session = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("email")).thenReturn(email);
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(false);
//        ItemService spy = Mockito.spy(itemService);
        doReturn(Collections.emptyList()).when(itemService).getbyCartDetails(email);
        String result = itemService.savePlaceOrderClient(new PaymentDefault(), binding, request);
        assertThat(result).isEqualTo("redirect:/cart");
    }
    @Test
    void savePlaceOrder_ShouldCreateOrder_WhenCOD() {

        PaymentDefault payment = new PaymentDefault();
        payment.setPaymentMethod(PaymentMethod.COD);
        CartDetail item = new CartDetail();
        item.setPrice(100);
        item.setQuantity(1);
        doReturn(List.of(item)).when(itemService).getbyCartDetails(anyString());
        doNothing().when(itemService).SavePlaceOrder(anyString(),
                        any(),
                        any(),
                        anyDouble());
        HttpSession session = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(false);
        when(request.getSession(false)).thenReturn(session);

        when(session.getAttribute("email")).thenReturn("user@gmail.com");
        String result = itemService.savePlaceOrderClient(payment,
                        binding,
                        request);
        assertThat(result).isEqualTo("redirect:/payment-success");
    }
    @Test
    void savePlaceOrder_ShouldRedirectMomo() throws JsonProcessingException {

        PaymentDefault payment = new PaymentDefault();

        payment.setPaymentMethod(PaymentMethod.MOMO);
        CartDetail item = new CartDetail();
        item.setPrice(100);
        item.setQuantity(1);
//        ItemService spy = Mockito.spy(itemService);
        HttpServletRequest request = mock(HttpServletRequest.class);
        BindingResult binding = mock(BindingResult.class);
        HttpSession session = mock(HttpSession.class);
        when(binding.hasErrors()).thenReturn(false);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("email")).thenReturn("user@gmail.com");
        doReturn(List.of(item)).when(itemService).getbyCartDetails(anyString());
        when(momoService.createMomoPayment(anyString(), any(), any(), any())).thenReturn("https://momo.vn");
        String result = itemService.savePlaceOrderClient(payment, binding, request);
        assertThat(result).isEqualTo("redirect:https://momo.vn");
    }
    @Test
    void savePlaceOrder_ShouldRedirectVNPay() throws JsonProcessingException {
        PaymentDefault payment = new PaymentDefault();
        payment.setPaymentMethod(PaymentMethod.VNPAY);

        CartDetail item = new CartDetail();
        item.setPrice(100);
        item.setQuantity(1);

        HttpServletRequest request = mock(HttpServletRequest.class);
        BindingResult binding = mock(BindingResult.class);
        HttpSession session = mock(HttpSession.class);

        when(binding.hasErrors()).thenReturn(false);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("email")).thenReturn("user@gmail.com");

        doReturn(List.of(item)).when(itemService).getbyCartDetails(anyString());
        when(vnPayService.createVNPayPayment(anyString(), any(), any(), any())).thenReturn("https://sandbox.vnpay.vn");
        String result = itemService.savePlaceOrderClient(payment, binding, request);
        assertThat(result).isEqualTo("redirect:https://sandbox.vnpay.vn");

    }
    @Test
    void paymentCompletedVNPay_ShouldFail_WhenVerifyFail() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        BindingResult binding = mock(BindingResult.class);
        HttpSession session = mock(HttpSession.class);
        Model model = new ExtendedModelMap();

        when(vnPayService.orderReturn(any())).thenReturn(0);
        String result = itemService.paymentCompletedVNPay(request, model, session);
        assertThat(result).isEqualTo("client/vnpaynotification/failpayment");
    }
    @Test
    void momoReturnPayment_ShouldFail_WhenSignatureInvalid() {
        MomoCallbackRequest callback = new MomoCallbackRequest();
        Model model = new ExtendedModelMap();
        when(momoService.verifyMomoCallbackSignature(callback)).thenReturn(false);
        String result = itemService.momoReturnPayment(
                        callback,
                        model,
                        mock(HttpSession.class));
        assertThat(result).isEqualTo("client/momonotification/failpayment-momo");
        assertThat(model.getAttribute("message")).isNotNull();
    }
    @Test
    void momoReturnPayment_ShouldFail_WhenTransactionNotFound() {

        MomoCallbackRequest callback = new MomoCallbackRequest();

        when(momoService.verifyMomoCallbackSignature(callback)).thenReturn(true);
        when(paymentTransactionRepository.findByTxnRefForUpdate(any())).thenReturn(Optional.empty());
        String result = itemService.momoReturnPayment(
                        callback,
                        new ExtendedModelMap(),
                        mock(HttpSession.class));
        assertThat(result).contains("failpayment");
    }
    @Test
    void momoReturnPayment_ShouldReturnSuccess_WhenAlreadyProcessed() {
        MomoCallbackRequest callback = new MomoCallbackRequest();

        PaymentTransaction transaction = new PaymentTransaction();

        transaction.setStatus(PaymentTransactionStatus.SUCCESS);

        when(paymentTransactionRepository.findByTxnRefForUpdate(any())).thenReturn(Optional.of(transaction));
        when(momoService.verifyMomoCallbackSignature(any())).thenReturn(true);
        String result = itemService.momoReturnPayment(
                        callback,
                        new ExtendedModelMap(),
                        mock(HttpSession.class));

        assertThat(result)
                .contains("succesful");
    }
    @Test
    void momoReturnPayment_ShouldFail_WhenAmountMismatch() {

        PaymentTransaction transaction = new PaymentTransaction();

        transaction.setAmount(BigDecimal.valueOf(1000));
        transaction.setStatus(PaymentTransactionStatus.PENDING);

        MomoCallbackRequest callback =new MomoCallbackRequest();
        callback.setAmount("500");
        String result = itemService.momoReturnPayment(
                        callback,
                        new ExtendedModelMap(),
                        mock(HttpSession.class));
        assertThat(result).contains("failpayment");
    }
    @Test
    void momoReturnPayment_ShouldSuccess() throws Exception {
        MomoCallbackRequest callback = new MomoCallbackRequest();

        PaymentTransaction transaction = new PaymentTransaction();
        callback.setAmount("1000");
        transaction.setAmount(BigDecimal.valueOf(1000));
        transaction.setShippingInfoJson("{\"field\":\"value\"}");
        transaction.setEmail("anhhieu2k3@gmail.com");
        callback.setResponseTime("");

        when(paymentTransactionRepository.findByTxnRefForUpdate(any())).thenReturn(Optional.of(transaction));
        when(momoService.verifyMomoCallbackSignature(any())).thenReturn(true);
//
        doReturn(new PaymentDefault()).when(objectMapper).readValue(anyString(), eq(PaymentDefault.class));
//        ItemService spy = Mockito.spy(itemService);
        doNothing().when(itemService).SavePlaceOrderGateway(
                anyString(),
                        any(),
                        any(),
                        anyString());
        String result = itemService.momoReturnPayment(
                        callback,
                        new ExtendedModelMap(),
                        mock(HttpSession.class));
        assertThat(result).contains("succesful");
        verify(paymentTransactionRepository).save(any());
    }
    @Test
    void addCartItem_ShouldThrow_WhenUserNotFound() {
        when(userService.getbyEmail(any())).thenReturn(null);
        assertThatThrownBy(() -> itemService.addCartItem(
                        1L,
                        "abc@gmail.com",
                        mock(HttpSession.class)))
                .isInstanceOf(
                        IllegalArgumentException.class);
    }
    @Test
    void addCartItem_ShouldCreateCart_WhenCartNotExists() {

        User user = new User();

        Cart cart = null;

        Product product = new Product();
        product.setPrice(100D);

        when(userService.getbyEmail(any())).thenReturn(user);

        when(cartRepository.findByUser(user)).thenReturn(cart);

        when(productService.getProductbyId(any())).thenReturn(Optional.of(product));

        itemService.addCartItem(
                1L,
                "abc@gmail.com",
                mock(HttpSession.class));
        verify(cartRepository, atLeastOnce())
                .save(any(Cart.class));
    }
    @Test
    void addCartItem_ShouldIncreaseQuantity_WhenExists() {

        User user = new User();
        Product product = new Product();
        product.setPrice(100D);
        CartDetail detail = new CartDetail();
        detail.setQuantity(1);
        when(cartDetailRepository.findByCartAndProduct(any(), any())).thenReturn(detail);
        when(productService.getProductbyId(any())).thenReturn(Optional.of(product));
        when(userService.getbyEmail(any())).thenReturn(user);
        itemService.addCartItem(1L, "a@gmail.com", mock(HttpSession.class));
        assertThat(detail.getQuantity()).isEqualTo(2);
        verify(cartDetailRepository).save(detail);
    }
    @Test
    void addCartItem_ShouldCreateNewCartDetail() {
        User user = new User();

        Product product = new Product();

        when(userService.getbyEmail(any())).thenReturn(user);
        when(productService.getProductbyId(any())).thenReturn(Optional.of(product));

        when(cartDetailRepository
                .findByCartAndProduct(any(), any()))
                .thenReturn(null);

        itemService.addCartItem(
                1L,
                "a@gmail.com",
                mock(HttpSession.class));

        verify(cartDetailRepository)
                .save(any(CartDetail.class));
    }
    @Test
    void deleteCartDetail_ShouldThrow_WhenNotFound() {

        when(cartDetailRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() ->
                itemService.deleteCartDetail(
                        1L,
                        mock(HttpSession.class)))
                .isInstanceOf(RuntimeException.class);
    }
    @Test
    void deleteCartDetail_ShouldDecreaseSum() {

        Cart cart = new Cart();
        cart.setSum(2);

        CartDetail detail = new CartDetail();
        detail.setCart(cart);

        when(cartDetailRepository.findById(1L))
                .thenReturn(Optional.of(detail));

        itemService.deleteCartDetail(
                1L,
                mock(HttpSession.class));

        assertThat(cart.getSum())
                .isEqualTo(1);

        verify(cartRepository).save(cart);
    }
    @Test
    void deleteCartDetail_ShouldDeleteCart_WhenLastItem() {

        Cart cart = new Cart();
        cart.setSum(1);

        CartDetail detail = new CartDetail();
        detail.setCart(cart);

        when(cartDetailRepository.findById(1L))
                .thenReturn(Optional.of(detail));

        itemService.deleteCartDetail(
                1L,
                mock(HttpSession.class));

        verify(cartRepository)
                .delete(cart);
    }
    @Test
    void updateQuantity_ShouldSetOne_WhenLessThanOne() {

        Product product = new Product();
        product.setQuantity(10);

        CartDetail detail = new CartDetail();
        detail.setProduct(product);

        when(cartDetailRepository.findById(1L))
                .thenReturn(Optional.of(detail));

        itemService.updateQuantity(1L, 0);

        assertThat(detail.getQuantity())
                .isEqualTo(1);
    }
    @Test
    void updateQuantity_ShouldThrow_WhenNotFound() {

        when(cartDetailRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                itemService.updateQuantity(1L, 5))
                .isInstanceOf(
                        IllegalArgumentException.class);
    }
    @Test
    void updateQuantity_ShouldUseStock_WhenExceedStock() {

        Product product = new Product();
        product.setQuantity(3);

        CartDetail detail = new CartDetail();
        detail.setProduct(product);

        when(cartDetailRepository.findById(1L))
                .thenReturn(Optional.of(detail));

        itemService.updateQuantity(1L, 10);

        assertThat(detail.getQuantity())
                .isEqualTo(3);
    }
    @Test
    void checkout_ShouldThrow_WhenProductMissing() {

        CartDetail cd = new CartDetail();

        Product p = new Product();
        p.setId(1L);

        cd.setProduct(p);

        when(productService.getforUpdate(any()))
                .thenReturn(Collections.emptyList());

        assertThatThrownBy(() ->
                itemService.checkout(
                        new Order(),
                        List.of(cd)))
                .isInstanceOf(RuntimeException.class);
    }
    @Test
    void checkout_ShouldThrow_WhenStockNotEnough() {

        Product product = new Product();
        product.setId(1L);
        product.setQuantity(1);

        CartDetail cd = new CartDetail();
        cd.setProduct(product);
        cd.setQuantity(5);

        when(productService.getforUpdate(any())).thenReturn(List.of(product));
        assertThatThrownBy(() ->
                itemService.checkout(
                        new Order(),
                        List.of(cd)))
                .isInstanceOf(
                        IllegalArgumentException.class);
    }
    @Test
    void checkout_ShouldUpdateInventory() {

        Product product = new Product();
        product.setId(1L);
        product.setQuantity(10);
        product.setSold(0);

        CartDetail cd = new CartDetail();
        cd.setId(1L);
        cd.setProduct(product);
        cd.setQuantity(2);
        cd.setPrice(100);

        when(productService.getforUpdate(any())).thenReturn(List.of(product));

        itemService.checkout(
                new Order(),
                List.of(cd));

        assertThat(product.getQuantity()).isEqualTo(8);

        assertThat(product.getSold()).isEqualTo(2);

        verify(orderDetailRepository).save(any());
        verify(cartDetailRepository).deleteById(1L);

        verify(productService).SaveAll(any());
    }












}
