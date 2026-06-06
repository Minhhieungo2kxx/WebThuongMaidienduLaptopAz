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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.ecornomere.ecornomereAZ.model.Enum.PaymentMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.ecornomere.ecornomereAZ.model.Enum.PaymentTransactionStatus;
import vn.ecornomere.ecornomereAZ.model.dto.MomoCallbackRequest;
import vn.ecornomere.ecornomereAZ.model.dto.PaymentDefault;
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
    private final ProductService productService;
    private final VNPayService vnPayService;
    private final MomoService momoService;
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final ObjectMapper objectMapper;

    @GetMapping("/product/detail/{id}")
    public String ShowDetailItem(@PathVariable Long id, Model model, HttpServletRequest request) {
        Product detail = productService.getProductbyId(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Product Id: " + id));
        model.addAttribute("detailProduct", detail);
        return "client/product/Detailproduct"; //
    }

    @GetMapping("/")
    public String showHomePage(@RequestParam(name = "page", defaultValue = "0") String pageParam, Model model) {
        int page = 0;
        int pageSize = 8;

        try {
            page = Integer.parseInt(pageParam);
            if (page < 0)
                page = 0;
        } catch (NumberFormatException e) {
            // Nếu người dùng nhập sai, mặc định về trang đầu
            page = 0;
        }
        List<String> targets = Arrays.asList("Mong nhe", "Doanh nhan");
        model.addAttribute("allProducts", itemService.getAllItems());
        model.addAttribute("gamingProducts", itemService.listNameItems("Gaming"));
        model.addAttribute("officeProducts", itemService.listNameItems("Van phong"));
        model.addAttribute("designProducts", itemService.listNameItems("Thiet ke do hoa"));
        model.addAttribute("personalProducts", itemService.getBytargetIn(targets));
        // Lấy tất cả sản phẩm
        Page<Product> productPage = itemService.getAllItemsPaginated(page, pageSize);
        model.addAttribute("allProducts", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        // Lấy sản phẩm theo từng loại
        Page<Product> productPageGamming = itemService.pagelistNameItems("Gaming", page, pageSize);
        model.addAttribute("gamingProducts", productPageGamming.getContent());
        model.addAttribute("currentPage1", page);
        model.addAttribute("totalPages1", productPageGamming.getTotalPages());

        // Lấy sản phẩm theo từng loại
        Page<Product> productPageOffice = itemService.pagelistNameItems("Van phong", page, pageSize);
        model.addAttribute("officeProducts", productPageOffice.getContent());
        model.addAttribute("currentPage2", page);
        model.addAttribute("totalPages2", productPageOffice.getTotalPages());

        // Lấy sản phẩm theo từng loại
        Page<Product> productPagedesign = itemService.pagelistNameItems("Thiet ke do hoa", page, pageSize);
        model.addAttribute("designProducts", productPagedesign.getContent());
        model.addAttribute("currentPage3", page);
        model.addAttribute("totalPages3", productPageOffice.getTotalPages());

        // Lấy sản phẩm theo từng loại + phan trang

        Page<Product> productPersonal = itemService.getBytargetInPaginated(targets, page, pageSize);
        model.addAttribute("personalProducts", productPersonal.getContent());
        model.addAttribute("currentPage4", page);
        model.addAttribute("totalPages4", productPageOffice.getTotalPages());
        return "client/homepage/home";
    }

    // Hiển thị danh sách

    @PostMapping("/add-cart/{id}")
    public String AddCartItem(@PathVariable Long id, HttpServletRequest request) {
        // Set thông tin vào session
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        if (email == null || email.isEmpty()) {
            return "redirect:/login"; // Nếu chưa đăng nhập thì chuyển đến trang đăng nhập
        } else {
            // Gọi service để thêm sản phẩm vào giỏ hàng
            itemService.addCartItem(id, email, session);
        }

        return "redirect:/"; // Sau khi lưu thì chuyển về
    }

    @PostMapping("/add-cart-filter/{id}")
    public String AddCartItemFilter(@PathVariable Long id, HttpServletRequest request) {
        // Set thông tin vào session
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        if (email == null || email.isEmpty()) {
            return "redirect:/login"; // Nếu chưa đăng nhập thì chuyển đến trang đăng nhập
        } else {
            // Gọi service để thêm sản phẩm vào giỏ hàng
            itemService.addCartItem(id, email, session);
        }

        return "redirect:/products"; // Sau khi lưu thì chuyển về
    }

    @GetMapping("/cart")
    public String ShowCartDetail(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        if (email == null || email.isEmpty()) {
            return "redirect:/login";
        }

        List<CartDetail> list = itemService.getbyCartDetails(email);
        if (list == null) {
            list = new ArrayList<>();

        }
        // Tính tổng tiền
        double totalPrice = 0.0;
        for (CartDetail detail : list) {
            totalPrice += detail.getQuantity() * detail.getPrice();
        }
        model.addAttribute("sumPrice", totalPrice); // Gửi tổng tiền xuống view
        model.addAttribute("listCartDetails", list);
        return "client/cart/cartdetails";
    }
    // Delete CartDetail

    @PostMapping("/delete-cart-product/{id}")
    public String deleteProduct(@PathVariable Long id, HttpServletRequest request,
                                RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();

        if (session == null || session.getAttribute("email") == null) {
            return "redirect:/login";
        }
        itemService.deleteCartDetail(id, session);
        return "redirect:/cart";
    }

    @GetMapping("/check-out")
    public String ShowCheckout(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        if (email == null || email.isEmpty()) {
            return "redirect:/login";
        }

        List<CartDetail> list = itemService.getbyCartDetails(email);
        if (list == null) {
            list = new ArrayList<>();

        }
        // Tính tổng tiền
        double totalPrice = 0.0;
        for (CartDetail detail : list) {
            totalPrice += detail.getQuantity() * detail.getPrice();
        }
        model.addAttribute("sumPrice", totalPrice); // Gửi tổng tiền xuống view
        model.addAttribute("listCartDetails", list);
        model.addAttribute("PaymentDefault", new PaymentDefault()); // hoặc đối tượng thật từ DB
        return "client/cart/checkout";
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
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/login";
        }
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return "redirect:/login";
        }
        if (result.hasErrors()) {
            log.warn(
                    "Checkout validation failed. Email={}",
                    email
            );
            loadCheckoutData(email, request);
            return "client/cart/checkout";
        }
        try {
            List<CartDetail> cartDetails = itemService.getbyCartDetails(email);
            if (cartDetails == null || cartDetails.isEmpty()) {
                log.warn("Cart is empty. Email={}", email);
                return "redirect:/cart";
            }
            BigDecimal totalAmount = cartDetails.stream().map(item -> BigDecimal.valueOf(item.getPrice())
                            .multiply(BigDecimal.valueOf(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .add(BigDecimal.valueOf(50000));
            PaymentMethod method = paymentDefault.getPaymentMethod();
            log.info("Start place order. Email={}, Method={}, Amount={}", email, method, totalAmount
            );
            switch (method) {
                case COD:
                    itemService.SavePlaceOrder(email, paymentDefault, session, totalAmount.doubleValue());
                    log.info("COD order created successfully. Email={}", email);
                    return "redirect:/payment-success";
                case MOMO:
                    String payUrl = momoService.createMomoPayment(email, totalAmount, request, paymentDefault);
                    log.info("MOMO payment url generated. Email={}", email);
                    return "redirect:" + payUrl;

                case VNPAY:
                    String paymentUrl = vnPayService.createVNPayPayment(email, totalAmount, request, paymentDefault);
                    log.info("VNPAY payment url generated. Email={}", email);
                    return "redirect:" + paymentUrl;
                default:
                    log.error("Unsupported payment method. Email={}, Method={}", email, method
                    );
                    throw new IllegalArgumentException("Unsupported payment method");
            }
        } catch (Exception ex) {
            log.error("Place order failed. Email={}", email, ex);
            return "redirect:/checkout";
        }
    }



    public void loadCheckoutData(String email, HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<CartDetail> list = itemService.getbyCartDetails(email);
        if (list == null) {
            list = new ArrayList<>();
        }

        double totalPrice = 0.0;
        for (CartDetail detail : list) {
            totalPrice += detail.getQuantity() * detail.getPrice();
        }

        request.setAttribute("listCartDetails", list);
        request.setAttribute("sumPrice", totalPrice);
    }

    @GetMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, Model model, HttpSession session) {
        int verifyResult = vnPayService.orderReturn(request);
        if (verifyResult != 1) {
            model.addAttribute("message", "Thanh toán thất bại");
            return "client/vnpaynotification/failpayment";
        }
        String txnRef = request.getParameter("vnp_TxnRef");
        PaymentTransaction transaction = paymentTransactionRepository.findByTxnRefForUpdate(txnRef).orElse(null);

        if (transaction == null) {
            model.addAttribute("message", "Không tìm thấy giao dịch");
            return "client/vnpaynotification/failpayment";
        }
//     Idempotency
        if (transaction.getStatus()
                == PaymentTransactionStatus.SUCCESS) {
            log.warn("Transaction already processed: {}", txnRef);
            return "client/vnpaynotification/succesful";
        }
//     * Verify Amount
        String amountParam = request.getParameter("vnp_Amount");
        BigDecimal amountFromVNPay = BigDecimal.valueOf(Long.parseLong(amountParam)).divide(BigDecimal.valueOf(100));
        if (transaction.getAmount().compareTo(amountFromVNPay) != 0) {
            log.error("Amount mismatch txnRef={}", txnRef);
            model.addAttribute("message", "Sai số tiền");
            return "client/vnpaynotification/failpayment";
        }
        try {
            PaymentDefault paymentDefault = objectMapper.readValue(transaction.getShippingInfoJson(), PaymentDefault.class);
            itemService.SavePlaceOrderGateway(transaction.getEmail(), paymentDefault, amountFromVNPay, request.getParameter("vnp_PayDate"));
            transaction.setStatus(PaymentTransactionStatus.SUCCESS);
            transaction.setTransactionNo(request.getParameter("vnp_TransactionNo"));
            transaction.setPaidAt(LocalDateTime.now());
            paymentTransactionRepository.save(transaction);
            log.info("Payment success txnRef={}", txnRef);
            model.addAttribute("orderInfo", request.getParameter("vnp_OrderInfo"));
            model.addAttribute("totalPrice", transaction.getAmount());
            model.addAttribute("paymentTime",request.getParameter("vnp_PayDate")
                    .formatted(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            model.addAttribute("transactionId", request.getParameter("vnp_TransactionNo"));
            session.setAttribute("sum", 0);
            model.addAttribute(
                    "transactionId", transaction.getTransactionNo());
            return "client/vnpaynotification/succesful";

        } catch (Exception ex) {
            log.error("Create order failed txnRef={}", txnRef, ex);
            model.addAttribute("message", "Tạo đơn hàng thất bại");
            return "client/vnpaynotification/failpayment";
        }
    }


    @GetMapping("/momo-return")
    public String momoReturn(MomoCallbackRequest callback, Model model, HttpSession session) {
        if (!momoService.verifyMomoCallbackSignature(callback)) {
            model.addAttribute("message", "Thanh toán MoMo thất bại: " + callback.getMessage());
            return "client/momonotification/failpayment-momo";
        }
        PaymentTransaction transaction = paymentTransactionRepository.findByTxnRefForUpdate(callback.getOrderId()).orElse(null);
        if (transaction == null) {
            model.addAttribute("message", "Không tìm thấy giao dịch");
            return "client/momonotification/failpayment-momo";
        }
//        Idempotency
        if (transaction.getStatus()
                == PaymentTransactionStatus.SUCCESS) {
            log.warn("Transaction already processed: {}",callback.getRequestId());
            return "client/momonotification/succesful-momo";
        }
//        * Verify Amount
        String amountParam = callback.getAmount();
        BigDecimal amountFromVNPay = BigDecimal.valueOf(Long.parseLong(amountParam));
        if (transaction.getAmount().compareTo(amountFromVNPay) != 0) {
            log.error("Amount mismatch txnRef={}", callback.getOrderInfo());
            model.addAttribute("message", "Sai số tiền");
            return "client/momonotification/failpayment-momo";
        }
        try {
            PaymentDefault paymentDefault = objectMapper.readValue(transaction.getShippingInfoJson(), PaymentDefault.class);
            itemService.SavePlaceOrderGateway(transaction.getEmail(), paymentDefault, amountFromVNPay, callback.getResponseTime());
            transaction.setStatus(PaymentTransactionStatus.SUCCESS);
            transaction.setTransactionNo(callback.getTransId());
            transaction.setPaidAt(LocalDateTime.now());
            paymentTransactionRepository.save(transaction);
            log.info("Payment success txnRef={}", callback.getRequestId());
            // Gửi dữ liệu sang màn hình thông báo thành công
            model.addAttribute("orderId", callback.getOrderId());
            model.addAttribute("amount", callback.getAmount());
            model.addAttribute("message", callback.getMessage());
            model.addAttribute("transId", callback.getTransId());
            model.addAttribute("orderInfo", callback.getOrderInfo());
            model.addAttribute("payType", callback.getPayType());
            model.addAttribute("paymentTime", callback.getResponseTime().formatted(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            session.setAttribute("sum", 0);
            return "client/momonotification/succesful-momo";

        } catch (Exception ex) {
            log.error("Create order failed txnRef={}", callback.getRequestId(), ex);
            model.addAttribute("message", "Thanh toán MoMo thất bại: ");
            return "client/momonotification/failpayment-momo";
        }


    }
}





