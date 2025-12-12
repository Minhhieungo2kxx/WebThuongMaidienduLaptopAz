package vn.ecornomere.ecornomereAZ.controller.client;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.ecornomere.ecornomereAZ.model.CartDetail;
import vn.ecornomere.ecornomereAZ.model.Product;
import vn.ecornomere.ecornomereAZ.model.dto.PaymentDefault;

import vn.ecornomere.ecornomereAZ.service.ItemService;
import vn.ecornomere.ecornomereAZ.service.ProductService;
import vn.ecornomere.ecornomereAZ.service.Momo.MomoService;
import vn.ecornomere.ecornomereAZ.service.VnPay.VNPayService;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private ProductService productService;
    @Autowired
    private VNPayService vnPayService;
    @Autowired
    private MomoService momoService;

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
    public String SavePlaceOrder(@Valid @ModelAttribute("PaymentDefault") PaymentDefault paymentDefault,
            BindingResult result,
            HttpServletRequest request) {
        // Kiểm tra lỗi validation
        if (result.hasErrors()) {

            // Gán lại dữ liệu như bên GET va gui lai du lieu ve form thong bao loi
            HttpSession session = request.getSession();
            String email = (String) session.getAttribute("email");

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
            return "client/cart/checkout"; // ❗Không redirect!
        }
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        double sumtien = paymentDefault.getSummoney();
        // ====== COD ======
        if (paymentDefault.getPaymentMethod().equals("cod")) {
            // double sumtien = paymentDefault.getSummoney();
            itemService.SavePlaceOrder(email, paymentDefault, session);
            return "redirect:/payment-success"; // redirect lại trang giỏ hàng

        }
        // ====== MOMO ======
        if (paymentDefault.getPaymentMethod().equals("momo")) {

            session.setAttribute("tempPaymentDefault", paymentDefault);
            session.setAttribute("tempEmail", email);
            String payUrl = momoService.createPaymentRequest(String.valueOf((int) sumtien));
            return "redirect:" + payUrl;
        }
        // Lưu thông tin đơn hàng tạm thời vào session trước khi chuyển đến VNPay
        session.setAttribute("tempPaymentDefault", paymentDefault);
        session.setAttribute("tempEmail", email);
        // Tạo URL thanh toán VNPay
        String orderInfo = "Thanh toan don hang tai FPT Shop";
        String paymentUrl = vnPayService.createOrder(request, (int) sumtien, orderInfo, "");

        return "redirect:" + paymentUrl;

    }

    @GetMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, Model model) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        HttpSession session = request.getSession();

        if (paymentStatus == 1) {
            // Thanh toán thành công - Lưu đơn hàng vào database
            PaymentDefault paymentDefault = (PaymentDefault) session.getAttribute("tempPaymentDefault");
            String email = (String) session.getAttribute("tempEmail");
            session.setAttribute("paymentTime", paymentTime);

            if (paymentDefault != null && email != null) {
                itemService.SavePlaceOrder(email, paymentDefault, session);

                // Xóa dữ liệu tạm thời
                session.removeAttribute("tempPaymentDefault");
                session.removeAttribute("tempEmail");
                // Sau khi sử dụng paymentTime
                session.removeAttribute("paymentTime");

                model.addAttribute("orderInfo", orderInfo);
                model.addAttribute("totalPrice", totalPrice);
                model.addAttribute("paymentTime", paymentTime);
                model.addAttribute("transactionId", transactionId);

                return "client/vnpaynotification/succesful";
            }
        } else {
            // Thanh toán thất bại
            model.addAttribute("message", "Thanh toán thất bại");
            return "client/vnpaynotification/failpayment";
        }

        return "redirect:/cart";
    }

    @GetMapping("/momo-return")
    public String momoReturn(HttpServletRequest request, Model model) {
        // Lấy các tham số trả về từ MoMo
        String resultCode = request.getParameter("resultCode");
        String message = request.getParameter("message");
        String amount = request.getParameter("amount");
        String orderId = request.getParameter("orderId");
        String transId = request.getParameter("transId");
        String orderInfo = request.getParameter("orderInfo");
        String payType = request.getParameter("payType");
        HttpSession session = request.getSession();
        // ====== Thanh toán thành công resultCode = "0" ======
        if ("0".equals(resultCode)) {

            PaymentDefault paymentDefault = (PaymentDefault) session.getAttribute("tempPaymentDefault");
            String email = (String) session.getAttribute("tempEmail");
            // Tạo thời gian thanh toán theo format yyyyMMddHHmmss
            String paymentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            session.setAttribute("paymentTime", paymentTime);
            if (paymentDefault != null && email != null) {
                itemService.SavePlaceOrder(email, paymentDefault, session);
                // Xóa session tạm
                session.removeAttribute("tempPaymentDefault");
                session.removeAttribute("tempEmail");

                // Gửi dữ liệu sang màn hình thông báo thành công
                model.addAttribute("orderId", orderId);
                model.addAttribute("amount", amount);
                model.addAttribute("message", message);
                model.addAttribute("transId", transId);
                model.addAttribute("orderInfo", orderInfo);
                model.addAttribute("payType", payType);
                model.addAttribute("paymentTime", paymentTime);

                return "client/momonotification/succesful-momo";
            }
        }
        // ====== Thanh toán thất bại ======
        model.addAttribute("message", "Thanh toán MoMo thất bại: " + message);
        return "client/momonotification/failpayment-momo";
    }

}
