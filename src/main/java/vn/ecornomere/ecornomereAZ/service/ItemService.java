package vn.ecornomere.ecornomereAZ.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.*;

import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.ecornomere.ecornomereAZ.dto.record.OrderPlacedEvent;
import vn.ecornomere.ecornomereAZ.dto.request.MomoCallbackRequest;
import vn.ecornomere.ecornomereAZ.enums.PaymentMethod;
import vn.ecornomere.ecornomereAZ.enums.PaymentTransactionStatus;
import vn.ecornomere.ecornomereAZ.dto.request.PaymentDefault;
import vn.ecornomere.ecornomereAZ.model.entity.*;
import vn.ecornomere.ecornomereAZ.repository.*;
import vn.ecornomere.ecornomereAZ.service.payments.MomoService;
import vn.ecornomere.ecornomereAZ.service.payments.VNPayService;
import vn.ecornomere.ecornomereAZ.utils.PaymentTimeParser;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;

    private final UserService userService;

    private final ProductService productService;
    private final CartRepository cartRepository;

    private final CartDetailRepository cartDetailRepository;

    private final OrderRepository orderRepository;

    private final OrderDetailRepository orderDetailRepository;

    private final ApplicationEventPublisher eventPublisher;

    private final VNPayService vnPayService;
    private final MomoService momoService;
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final ObjectMapper objectMapper;

    public String ShowDetailItemClient(Long id, Model model, HttpServletRequest request) {
        Product detail = productService.getProductbyId(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Product Id: " + id));
        model.addAttribute("detailProduct", detail);
        return "client/product/Detailproduct"; //
    }
    public String showHomePageClient(String pageParam, Model model) {
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
        model.addAttribute("allProducts", getAllItems());
        model.addAttribute("gamingProducts", listNameItems("Gaming"));
        model.addAttribute("officeProducts", listNameItems("Van phong"));
        model.addAttribute("designProducts", listNameItems("Thiet ke do hoa"));
        model.addAttribute("personalProducts", getBytargetIn(targets));
        // Lấy tất cả sản phẩm
        Page<Product> productPage = getAllItemsPaginated(page, pageSize);
        model.addAttribute("allProducts", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        // Lấy sản phẩm theo từng loại
        Page<Product> productPageGamming = pagelistNameItems("Gaming", page, pageSize);
        model.addAttribute("gamingProducts", productPageGamming.getContent());
        model.addAttribute("currentPage1", page);
        model.addAttribute("totalPages1", productPageGamming.getTotalPages());

        // Lấy sản phẩm theo từng loại
        Page<Product> productPageOffice = pagelistNameItems("Van phong", page, pageSize);
        model.addAttribute("officeProducts", productPageOffice.getContent());
        model.addAttribute("currentPage2", page);
        model.addAttribute("totalPages2", productPageOffice.getTotalPages());

        // Lấy sản phẩm theo từng loại
        Page<Product> productPagedesign = pagelistNameItems("Thiet ke do hoa", page, pageSize);
        model.addAttribute("designProducts", productPagedesign.getContent());
        model.addAttribute("currentPage3", page);
        model.addAttribute("totalPages3", productPageOffice.getTotalPages());

        // Lấy sản phẩm theo từng loại + phan trang

        Page<Product> productPersonal = getBytargetInPaginated(targets, page, pageSize);
        model.addAttribute("personalProducts", productPersonal.getContent());
        model.addAttribute("currentPage4", page);
        model.addAttribute("totalPages4", productPageOffice.getTotalPages());
        return "client/homepage/home";
    }
    @Transactional
    public String AddCartItemClient(Long id, HttpServletRequest request) {
        // Set thông tin vào session
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        if (email == null || email.isEmpty()) {
            return "redirect:/login"; // Nếu chưa đăng nhập thì chuyển đến trang đăng nhập
        } else {
            // Gọi service để thêm sản phẩm vào giỏ hàng
            addCartItem(id, email, session);
        }

        return "redirect:/"; // Sau khi lưu thì chuyển về
    }
    @Transactional
    public String AddCartItemFilterClient(Long id, HttpServletRequest request) {
        // Set thông tin vào session
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        if (email == null || email.isEmpty()) {
            return "redirect:/login"; // Nếu chưa đăng nhập thì chuyển đến trang đăng nhập
        } else {
            // Gọi service để thêm sản phẩm vào giỏ hàng
            addCartItem(id, email, session);
        }

        return "redirect:/products"; // Sau khi lưu thì chuyển về
    }
    public String ShowCartDetailClient(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        if (email == null || email.isEmpty()) {
            return "redirect:/login";
        }

        List<CartDetail> list =getbyCartDetails(email);
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
    @Transactional
    public String deleteProductClient(Long id, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();

        if (session == null || session.getAttribute("email") == null) {
            return "redirect:/login";
        }
        deleteCartDetail(id, session);
        return "redirect:/cart";
    }
    @Transactional
    public String savePlaceOrderClient(PaymentDefault paymentDefault, BindingResult result, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/login";
        }
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return "redirect:/login";
        }
        if (result.hasErrors()) {
            log.warn("Checkout validation failed. Email={}", email);
            loadCheckoutData(email, request);
            return "client/cart/checkout";
        }
        try {
            List<CartDetail> cartDetails = getbyCartDetails(email);
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
                    SavePlaceOrder(email, paymentDefault, session, totalAmount.doubleValue());
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
    public String ShowCheckoutClient(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        if (email == null || email.isEmpty()) {
            return "redirect:/login";
        }

        List<CartDetail> list = getbyCartDetails(email);
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
    public void loadCheckoutData(String email, HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<CartDetail> list = getbyCartDetails(email);
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

    @Transactional
    public String paymentCompletedVNPay(HttpServletRequest request, Model model, HttpSession session) {
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
            SavePlaceOrderGateway(transaction.getEmail(), paymentDefault, amountFromVNPay, request.getParameter("vnp_PayDate"));
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
    @Transactional
    public String momoReturnPayment(MomoCallbackRequest callback, Model model, HttpSession session) {
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
            SavePlaceOrderGateway(transaction.getEmail(), paymentDefault, amountFromVNPay, callback.getResponseTime());
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


    public List<Product> listNameItems(String name) {
        return this.itemRepository.findByTarget(name);

    }

    public Page<Product> pagelistNameItems(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.itemRepository.findByTarget(name, pageable);
    }

    public List<Product> getAllItems() {
        return this.itemRepository.findAll();

    }

    public Page<Product> getAllItemsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return itemRepository.findAll(pageable);
    }

    public Page<Product> getAllItemsPaginatedSorted(int page, int size, String sortOption) {
        Sort sort;

        switch (sortOption) {
            case "price-asc":
                sort = Sort.by("price").ascending();
                break;
            case "price-desc":
                sort = Sort.by("price").descending();
                break;
            case "name-asc":
                sort = Sort.by("name").ascending();
                break;
            default:
                sort = Sort.unsorted();
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        return itemRepository.findAll(pageable);
    }

    public List<Product> getBytargetIn(List<String> listtarget) {
        return this.itemRepository.findByTargetIn(listtarget);

    }

    public Page<Product> getBytargetInPaginated(List<String> listtarget, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.itemRepository.findByTargetIn(listtarget, pageable);
    }

    @Transactional
    public void addCartItem(Long id, String email, HttpSession session) {

        // 1. Lấy user từ email
        User user = userService.getbyEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }

        // 2. Lấy giỏ hàng hiện có hoặc tạo mới nếu chưa có
        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setSum(0); // bạn có thể cập nhật sau nếu cần
            cartRepository.save(cart);
        }

        // 3. Lấy sản phẩm
        Product product = productService.getProductbyId(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id: " + id));

        // 4. Kiểm tra xem CartDetail đã tồn tại chưa
        CartDetail existingDetail = cartDetailRepository.findByCartAndProduct(cart, product);

        if (existingDetail != null) {
            // Nếu đã tồn tại thì tăng số lượng và tổng tiền
            long currentQuantity = existingDetail.getQuantity();
            existingDetail.setQuantity(currentQuantity + 1);
            cartDetailRepository.save(existingDetail);

        } else {
            // Nếu chưa có thì tạo mới
            CartDetail cartDetail = new CartDetail();
            cartDetail.setProduct(product);
            cartDetail.setCart(cart);
            cartDetail.setPrice(product.getPrice());
            cartDetail.setQuantity(1);
            cartDetailRepository.save(cartDetail);
            // Cập nhật số lượng sản phẩm trong giỏ hàng nếu cần
            cart.setSum(cart.getSum() + 1);
            cartRepository.save(cart);
            session.setAttribute("sum", cart.getSum()); // Cập nhật giỏ hàng vào session
        }

    }

    public List<CartDetail> getbyCartDetails(String email) {
        User user = userService.getbyEmail(email);
        Cart cart = cartRepository.findByUser(user);
        List<CartDetail> listCarts = cartDetailRepository.findByCart(cart);
        return listCarts;

    }

    @Transactional
    public void deleteCartDetail(Long id, HttpSession session) {

        CartDetail detail = cartDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy cart detail với ID: " + id));

        Cart cart = detail.getCart();

        // Xóa CartDetail
        cartDetailRepository.delete(detail);

        // Cập nhật giỏ hàng
        if (cart.getSum() > 1) {
            int sumcart = cart.getSum() - 1;
            cart.setSum(cart.getSum() - 1);
            session.setAttribute("sum", sumcart);
            cartRepository.save(cart);
        } else {
            // Nếu còn 0, xóa giỏ hàng
            cartRepository.delete(cart);
            session.setAttribute("sum", 0);

        }

    }

    @Transactional
    public CartDetail updateQuantity(Long cartDetailId, int newQuantity) {
        if (newQuantity < 1) {
            newQuantity = 1; // Không cho phép nhỏ hơn 1
        }

        Optional<CartDetail> optional = cartDetailRepository.findById(cartDetailId);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("CartDetail not found with ID: " + cartDetailId);
        }

        CartDetail cartDetail = optional.get();
        Product product = cartDetail.getProduct();

        long stockQuantity = product.getQuantity();
        if ((long) newQuantity > stockQuantity) {
            newQuantity = (int) stockQuantity;
        }

        cartDetail.setQuantity(newQuantity);
        cartDetail.setTotalPrice(newQuantity * cartDetail.getPrice());

        return cartDetailRepository.save(cartDetail);
    }

    @Transactional
    public void SavePlaceOrder(String email, PaymentDefault paymentDefault, HttpSession session,double totalAmount) {
        User user = getUser(email);
        Cart cart = getCart(user);
        List<CartDetail> cartDetails = getCartDetails(cart);
        // ====== Tạo đơn hàng ======
        Order order = createOrder(user, paymentDefault,BigDecimal.valueOf(totalAmount),null);
        Order savedOrder = orderRepository.save(order);
        log.info("Order created id={}", savedOrder.getId());
//         * CHUYỂN CART → ORDER DETAIL
        checkout(savedOrder,cartDetails);

        cartRepository.deleteById(cart.getId());
        session.setAttribute("sum", 0);
        eventPublisher.publishEvent(new OrderPlacedEvent(savedOrder.getId(),email));
    }
    @Transactional
    public void SavePlaceOrderGateway(String email, PaymentDefault paymentDefault, BigDecimal totalAmount, String paymentTime) {
        User user = getUser(email);
        Cart cart = getCart(user);
        List<CartDetail> cartDetails = getCartDetails(cart);
        Order order = createOrder(user, paymentDefault, totalAmount, paymentTime);
        Order savedOrder = orderRepository.save(order);
        log.info("Order created id={}", savedOrder.getId());
//         * CHUYỂN CART → ORDER DETAIL
        checkout(savedOrder,cartDetails);
        cartRepository.deleteById(cart.getId());
        // ====== Gửi email xác nhận ======
        eventPublisher.publishEvent(new OrderPlacedEvent(savedOrder.getId(),email));
        log.info("Email sent to {}", email);
    }
    @Transactional
    public void checkout(Order savedOrder, List<CartDetail> cartDetails) {
        // 1. Lấy danh sách productId duy nhất và sắp xếp
        List<Long> productIds = cartDetails.stream().map(cd -> cd.getProduct().getId())
                .distinct().sorted().toList();
        // 2. Lock toàn bộ product cần xử lý
        List<Product> lockedProducts =
                productService.getforUpdate(productIds);
        // 3. Convert sang Map để lookup nhanh
        Map<Long, Product> productMap = lockedProducts.stream().collect(Collectors.toMap(
                        Product::getId,
                        Function.identity()));
        // 4. Xử lý checkout
        for (CartDetail cd : cartDetails) {
            Product product = productMap.get(cd.getProduct().getId());
            if (product == null) {
                throw new RuntimeException("Không tìm thấy sản phẩm: " + cd.getProduct().getId());
            }
            // Kiểm tra tồn kho
            if (product.getQuantity() < cd.getQuantity()) {throw new IllegalArgumentException("Sản phẩm "
                                + product.getName()
                                + " không đủ số lượng trong kho.");
            }
            // Trừ kho
            product.setQuantity(
                    product.getQuantity() - cd.getQuantity());
            // Tăng số lượng bán
            product.setSold(
                    product.getSold() + cd.getQuantity());
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(savedOrder);
            orderDetail.setProduct(product);
            orderDetail.setPrice(cd.getPrice());
            orderDetail.setQuantity(cd.getQuantity());
            orderDetail.setTotalPrice(
                    cd.getPrice() * cd.getQuantity());
            orderDetailRepository.save(orderDetail);
            cartDetailRepository.deleteById(cd.getId());
        }
        // 5. Save tất cả product một lần
        productService.SaveAll(lockedProducts);
    }

    public List<Order> getAllOrder() {
        return orderRepository.findAll();

    }

    public Page<Order> getOrderPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findAll(pageable);
    }

    public List<OrderDetail> getAllOrderdetail(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        Order neworder = order.get();
        return orderDetailRepository.findByOrder(neworder);
    }
    private Order createOrder(User user, PaymentDefault paymentDefault, BigDecimal totalAmount, String paymentTime) {
        Order order = new Order();
        order.setUser(user);
        order.setReceiverName(paymentDefault.getReceiverName());
        order.setReceiverAddress(paymentDefault.getReceiverAddress());
        order.setReceiverPhone(paymentDefault.getReceiverPhone());
        order.setTotalPrice(totalAmount.subtract(BigDecimal.valueOf(50000)).doubleValue());
        order.setTotalPriceaddShip(totalAmount.doubleValue());
        order.setStatus(PaymentTransactionStatus.PENDING.name());
        order.setPaymentMethod(paymentDefault.getPaymentMethod().name());
        // =========================
        // CASE 1: COD
        // =========================
        if (paymentDefault.getPaymentMethod() == PaymentMethod.COD ) {
            order.setPaymentStatus("UNPAID");
            order.setPaymentTime(null);
            return order;
        }
        // =========================
        // CASE 2: ONLINE PAYMENT (MOMO / VNPAY)
        // =========================
        LocalDateTime paymentDateTime = PaymentTimeParser.parse(paymentTime,paymentDefault.getPaymentMethod());
        if (paymentDateTime != null) {
            order.setPaymentTime(paymentDateTime);
            order.setPaymentStatus("PAID");
        } else {
            order.setPaymentStatus("UNPAID");
        }
        return order;
    }
    private User getUser(String email) {

        User user = userService.getbyEmail(email);

        if (user == null) {
            throw new RuntimeException(
                    "User not found");
        }

        return user;
    }
    private Cart getCart(User user) {

        Cart cart = cartRepository.findByUser(user);

        if (cart == null) {
            throw new RuntimeException(
                    "Cart not found");
        }

        return cart;
    }
    private List<CartDetail> getCartDetails(Cart cart) {

        List<CartDetail> cartDetails =
                cartDetailRepository.findByCart(cart);

        if (cartDetails.isEmpty()) {
            throw new RuntimeException(
                    "Cart is empty");
        }

        return cartDetails;
    }

}
