package vn.ecornomere.ecornomereAZ.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import vn.ecornomere.ecornomereAZ.model.Cart;
import vn.ecornomere.ecornomereAZ.model.CartDetail;
import vn.ecornomere.ecornomereAZ.model.Order;
import vn.ecornomere.ecornomereAZ.model.OrderDetail;
import vn.ecornomere.ecornomereAZ.model.User;
import vn.ecornomere.ecornomereAZ.model.dto.PaymentDefault;
import vn.ecornomere.ecornomereAZ.model.Product;
import vn.ecornomere.ecornomereAZ.repository.CartDetailRepository;
import vn.ecornomere.ecornomereAZ.repository.CartRepository;
import vn.ecornomere.ecornomereAZ.repository.ItemRepository;
import vn.ecornomere.ecornomereAZ.repository.OrderDetailRepository;
import vn.ecornomere.ecornomereAZ.repository.OrderRepository;
import vn.ecornomere.ecornomereAZ.service.SendEmail.ApplicationEmailService;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartDetailRepository cartDetailRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ApplicationEmailService applicationEmailService;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
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

    // @Transactional
    // public void SavePlaceOrder(String email, PaymentDefault paymentDefault,
    // HttpSession session) {
    // // Lấy user từ email
    // User user = userService.getbyEmail(email);
    // if (user == null) {
    // return; // hoặc throw new IllegalArgumentException("User not found");
    // }

    // // Lấy giỏ hàng của người dùng
    // Cart cart = cartRepository.findByUser(user);
    // if (cart == null) {
    // return; // Không có giỏ hàng, không cần tiếp tục
    // }

    // // Lấy chi tiết giỏ hàng
    // List<CartDetail> cartDetails = cartDetailRepository.findByCart(cart);
    // if (cartDetails == null || cartDetails.isEmpty()) {
    // return; // Không có sản phẩm để đặt hàng
    // }

    // // Tạo đơn hàng mới
    // Order order = new Order();
    // order.setUser(user);
    // order.setReceiverName(paymentDefault.getReceiverName());
    // order.setReceiverAddress(paymentDefault.getReceiverAddress());
    // order.setReceiverPhone(paymentDefault.getReceiverPhone());
    // order.setTotalPrice(paymentDefault.getSummoney() - 50000);
    // order.setTotalPriceaddShip(paymentDefault.getSummoney());
    // order.setStatus("Pending");
    // order.setPaymentMethod(paymentDefault.getPaymentMethod().equals("cod") ?
    // "COD" : "Online");
    // order.setPaymentStatus(paymentDefault.getPaymentMethod().equals("cod") ?
    // "Unpaid" : "Paid");

    // // Lưu thông tin thanh toán vào đơn hàng
    // String Time_payment = (String) session.getAttribute("paymentTime");
    // DateTimeFormatter inputFormatter =
    // DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    // DateTimeFormatter outputFormatter =
    // DateTimeFormatter.ofPattern("yyyyMMddHHmmss"); // giữ nguyên định dạng
    // if (Time_payment != null && !Time_payment.isEmpty()) {
    // LocalDateTime paymentTime = LocalDateTime.parse(Time_payment,
    // inputFormatter);
    // order.setPaymentTime(paymentTime.format(outputFormatter)); // Chuỗi đúng định
    // dạng JSP parse
    // } else {
    // order.setPaymentTime(LocalDateTime.now().format(outputFormatter));
    // }

    // // Lưu đơn hàng vào cơ sở dữ liệu
    // Order savedOrder = orderRepository.save(order);
    // session.removeAttribute("paymentTime");

    // // Lặp qua từng CartDetail và chuyển nó thành OrderDetail
    // for (CartDetail cd : cartDetails) {
    // // Kiểm tra tồn kho sản phẩm
    // Product product = cd.getProduct();
    // if (product.getQuantity() < cd.getQuantity()) {
    // throw new IllegalArgumentException("Sản phẩm " + product.getName() + " không
    // đủ số lượng trong kho.");
    // }

    // // Giảm số lượng tồn kho sau khi mua
    // product.setQuantity(product.getQuantity() - cd.getQuantity());
    // product.setSold(cd.getQuantity());
    // productService.saveProduct(product);

    // // Tạo OrderDetail và lưu vào cơ sở dữ liệu
    // OrderDetail orderDetail = new OrderDetail();
    // orderDetail.setOrder(savedOrder);
    // orderDetail.setProduct(cd.getProduct());
    // orderDetail.setPrice(cd.getPrice());
    // orderDetail.setQuantity(cd.getQuantity());
    // orderDetail.setTotalPrice(cd.getPrice() * cd.getQuantity());

    // orderDetailRepository.save(orderDetail);

    // // Xoá CartDetail sau khi đã chuyển sang OrderDetail
    // cartDetailRepository.deleteById(cd.getId());
    // }

    // // Xoá giỏ hàng sau khi đã đặt hàng
    // cartRepository.deleteById(cart.getId());

    // // Reset số lượng trong session
    // session.setAttribute("sum", 0);
    // // GỬI EMAIL XÁC NHẬN ĐƠN HÀNG

    // // Lấy danh sách sản phẩm đã đặt để đưa vào email
    // List<OrderDetail> orderDetails =
    // orderDetailRepository.findByOrder(savedOrder);

    // // Gọi phương thức gửi email
    // applicationEmailService.sendOrder(email, savedOrder, orderDetails);
    // }
    @Transactional
    public void SavePlaceOrder(String email, PaymentDefault paymentDefault, HttpSession session) {

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
        order.setTotalPrice(paymentDefault.getSummoney() - 50000);
        order.setTotalPriceaddShip(paymentDefault.getSummoney());
        order.setStatus("Pending"); // trạng thái mặc định

        /*
         * ================================
         * XỬ LÝ PHƯƠNG THỨC THANH TOÁN
         * ================================
         */
        String method = paymentDefault.getPaymentMethod(); // "cod", "vnpay", "momo", "zalopay"
        method = method.toUpperCase(); // chuẩn hóa tên

        order.setPaymentMethod(method); // Lưu đúng tên cổng thanh toán

        String paymentTimeSession = (String) session.getAttribute("paymentTime");

        // COD → luôn Unpaid
        if (method.equals("COD")) {
            order.setPaymentStatus("Unpaid");

        } else {
            // CÁC CỔNG ONLINE: VNPay, MoMo, ZaloPay, ...
            if (paymentTimeSession != null) {
                order.setPaymentStatus("Paid"); // thanh toán thành công
            } else {
                order.setPaymentStatus("Unpaid"); // thất bại hoặc chưa thanh toán
            }
        }

        // ====== Lưu paymentTime ======
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        if (paymentTimeSession != null) {
            LocalDateTime paymentTime = LocalDateTime.parse(paymentTimeSession, formatter);
            order.setPaymentTime(paymentTime.format(formatter));
        } else {
            order.setPaymentTime(LocalDateTime.now().format(formatter));
        }

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

}
