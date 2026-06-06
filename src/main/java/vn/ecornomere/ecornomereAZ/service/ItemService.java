package vn.ecornomere.ecornomereAZ.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import vn.ecornomere.ecornomereAZ.model.Enum.OrderStatus;
import vn.ecornomere.ecornomereAZ.model.Enum.PaymentMethod;
import vn.ecornomere.ecornomereAZ.model.Enum.PaymentTransactionStatus;
import vn.ecornomere.ecornomereAZ.model.dto.PaymentDefault;
import vn.ecornomere.ecornomereAZ.model.entity.Cart;
import vn.ecornomere.ecornomereAZ.model.entity.CartDetail;
import vn.ecornomere.ecornomereAZ.model.entity.Order;
import vn.ecornomere.ecornomereAZ.model.entity.OrderDetail;
import vn.ecornomere.ecornomereAZ.model.entity.Product;
import vn.ecornomere.ecornomereAZ.model.entity.User;
import vn.ecornomere.ecornomereAZ.repository.CartDetailRepository;
import vn.ecornomere.ecornomereAZ.repository.CartRepository;
import vn.ecornomere.ecornomereAZ.repository.ItemRepository;
import vn.ecornomere.ecornomereAZ.repository.OrderDetailRepository;
import vn.ecornomere.ecornomereAZ.repository.OrderRepository;
import vn.ecornomere.ecornomereAZ.service.SendEmail.ApplicationEmailService;
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

    private final ApplicationEmailService applicationEmailService;



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
        User user = userService.getbyEmail(email);
        if (user == null)
            return;

        Cart cart = cartRepository.findByUser(user);
        if (cart == null)
            return;

        List<CartDetail> cartDetails = cartDetailRepository.findByCart(cart);
        if (cartDetails == null || cartDetails.isEmpty())
            return;

        // ====== Tạo đơn hàng ======
        Order order = new Order();
        order.setUser(user);
        order.setReceiverName(paymentDefault.getReceiverName());
        order.setReceiverAddress(paymentDefault.getReceiverAddress());
        order.setReceiverPhone(paymentDefault.getReceiverPhone());
        order.setTotalPrice(totalAmount - 50000);
        order.setTotalPriceaddShip(totalAmount);
        order.setStatus("PENDING"); // trạng thái mặc định

        String method = paymentDefault.getPaymentMethod().toString(); // "cod", "vnpay", "momo", "zalopay"
        order.setPaymentMethod(method); // Lưu đúng tên cổng thanh toán
        String paymentTimeSession = (String) session.getAttribute("paymentTime");
        order.setPaymentStatus("UNPAID");
        order.setPaymentTime(null);

        // ====== Lưu đơn hàng ======
        Order savedOrder = orderRepository.save(order);
        session.removeAttribute("paymentTime");
        /*
         * ================================
         * CHUYỂN CART → ORDER DETAIL
         * ================================
         */

        for (CartDetail cd : cartDetails) {

            Product product = cd.getProduct();
            if (product.getQuantity() < cd.getQuantity()) {
                throw new IllegalArgumentException(
                        "Sản phẩm " + product.getName() + " không đủ số lượng trong kho.");
            }

            product.setQuantity(product.getQuantity() - cd.getQuantity());
            product.setSold(cd.getQuantity());
            productService.saveProduct(product);

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(savedOrder);
            orderDetail.setProduct(product);
            orderDetail.setPrice(cd.getPrice());
            orderDetail.setQuantity(cd.getQuantity());
            orderDetail.setTotalPrice(cd.getPrice() * cd.getQuantity());

            orderDetailRepository.save(orderDetail);

            cartDetailRepository.deleteById(cd.getId());
        }
        cartRepository.deleteById(cart.getId());
        session.setAttribute("sum", 0);
        // ====== Gửi email xác nhận ======
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(savedOrder);
        applicationEmailService.sendOrder(email, savedOrder, orderDetails);
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
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(savedOrder);
        applicationEmailService.sendOrder(email, savedOrder, orderDetails);
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
