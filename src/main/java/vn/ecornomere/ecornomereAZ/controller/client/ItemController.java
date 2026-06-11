package vn.ecornomere.ecornomereAZ.controller.client;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.ecornomere.ecornomereAZ.enums.PaymentMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.ecornomere.ecornomereAZ.enums.PaymentTransactionStatus;
import vn.ecornomere.ecornomereAZ.dto.request.MomoCallbackRequest;
import vn.ecornomere.ecornomereAZ.dto.request.PaymentDefault;
import vn.ecornomere.ecornomereAZ.model.entity.CartDetail;
import vn.ecornomere.ecornomereAZ.model.entity.PaymentTransaction;
import vn.ecornomere.ecornomereAZ.model.entity.Product;
import vn.ecornomere.ecornomereAZ.repository.PaymentTransactionRepository;
import vn.ecornomere.ecornomereAZ.service.ItemService;
import vn.ecornomere.ecornomereAZ.service.ProductService;
import vn.ecornomere.ecornomereAZ.service.payments.MomoService;
import vn.ecornomere.ecornomereAZ.service.payments.VNPayService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;


    @GetMapping("/product/detail/{id}")
    public String ShowDetailItem(@PathVariable Long id, Model model, HttpServletRequest request) {
       return itemService.ShowDetailItemClient(id,model,request);
    }

    @GetMapping("/")
    public String showHomePage(@RequestParam(name = "page", defaultValue = "0") String pageParam, Model model) {
        return itemService.showHomePageClient(pageParam,model);
    }

    @PostMapping("/add-cart/{id}")
    public String AddCartItem(@PathVariable Long id, HttpServletRequest request) {
        return itemService.AddCartItemClient(id,request);
    }

    @PostMapping("/add-cart-filter/{id}")
    public String AddCartItemFilter(@PathVariable Long id, HttpServletRequest request) {
        return itemService.AddCartItemFilterClient(id,request);
    }

    @GetMapping("/cart")
    public String ShowCartDetail(Model model, HttpServletRequest request) {

        return itemService.ShowCartDetailClient(model,request);
    }
    // Delete CartDetail

    @PostMapping("/delete-cart-product/{id}")
    public String deleteProduct(@PathVariable Long id, HttpServletRequest request,
                                RedirectAttributes redirectAttributes) {
        return itemService.deleteProductClient(id,request,redirectAttributes);
    }

    @GetMapping("/check-out")
    public String ShowCheckout(Model model, HttpServletRequest request) {

        return itemService.ShowCheckoutClient(model,request);
    }

    @GetMapping("/payment-success")
    public String ShowPaymentSuccess(Model model, HttpServletRequest request) {

        return "client/cart/payment-success";
    }

    @PostMapping("/cart/update")
    public String updateCartQuantity(@RequestParam("cartDetailId") Long cartDetailId,
                                     @RequestParam("quantity") int quantity, RedirectAttributes redirectAttributes) {
        // Cập nhật lại trong database
        itemService.updateQuantity(cartDetailId, quantity);

        return "redirect:/cart"; // redirect lại trang giỏ hàng
    }

    @PostMapping("/place-order")
    public String savePlaceOrder(@Valid @ModelAttribute("PaymentDefault") PaymentDefault paymentDefault, BindingResult result,
                                 HttpServletRequest request) {
        return itemService.savePlaceOrderClient(paymentDefault,result,request);
    }



    @GetMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, Model model, HttpSession session) {
        return itemService.paymentCompletedVNPay(request,model,session);
    }


    @GetMapping("/momo-return")
    public String momoReturn(MomoCallbackRequest callback, Model model, HttpSession session) {
        return itemService.momoReturnPayment(callback,model,session);

    }
}





