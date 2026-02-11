package vn.ecornomere.ecornomereAZ.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.ecornomere.ecornomereAZ.model.Cart;
import vn.ecornomere.ecornomereAZ.model.Order;
import vn.ecornomere.ecornomereAZ.model.OrderDetail;
import vn.ecornomere.ecornomereAZ.model.Product;
import vn.ecornomere.ecornomereAZ.model.Role;
import vn.ecornomere.ecornomereAZ.model.User;
import vn.ecornomere.ecornomereAZ.model.dto.ProductSales;
import vn.ecornomere.ecornomereAZ.model.dto.RegisterDTO;
import vn.ecornomere.ecornomereAZ.repository.CartRepository;
import vn.ecornomere.ecornomereAZ.repository.OrderDetailRepository;
import vn.ecornomere.ecornomereAZ.repository.OrderRepository;
import vn.ecornomere.ecornomereAZ.repository.ProductRepository;
import vn.ecornomere.ecornomereAZ.repository.UserRepository;
import vn.ecornomere.ecornomereAZ.utils.UploadFile;

import org.springframework.data.domain.Pageable;

@Service
public class UserService {
  final private UserRepository userRepository;
  @Autowired

  PasswordEncoder passwordEncoder;
  @Autowired
  RoleService roleService;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private OrderDetailRepository orderDetailRepository;

  @Autowired
  private ProductService productService;
  private UploadFile uploadFile = new UploadFile();

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Optional<User> findUserById(Long id) {
    return this.userRepository.findById(id);
  }

  @Transactional
  public void deleteUser(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User không hợp lệ.");
    }

    // Xử lý Order liên quan
    List<Order> orders = orderRepository.findByUserId(user.getId());
    if (!orders.isEmpty()) {
      for (Order order : orders) {
        order.setUser(null);
      }
      orderRepository.saveAll(orders);
    }

    // Xử lý Cart nếu có
    Cart cart = cartRepository.findByUser(user);
    if (cart != null) {
      cart.setUser(null);
      cartRepository.save(cart);
    }
    uploadFile.deleteImageFile(user.getAvatar(), "avatars");

    // Xóa user
    userRepository.delete(user);
  }

  public List<User> findAllUsers() {
    return this.userRepository.findAll();
  }

  public List<User> findAllUsersByEmailAndAddress(String email, String address) {
    return this.userRepository.findByEmailAndAddress(email, address);
  }

  public User handleSaveUser(User newUser) {
    return this.userRepository.save(newUser);
  }

  public User registertoDTO(RegisterDTO registerDTO) {
    User user = new User();
    user.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
    user.setEmail(registerDTO.getEmail());
    // user.setPassword(registerDTO.getPassword());
    return user;
  }

  public boolean existsByEmail(String email) {
    return this.userRepository.existsByEmail(email);

  }

  public User getbyEmail(String email) {
    return this.userRepository.findByEmail(email);
  }

  @Transactional
  public User createOAuth2User(String email, String fullName, String avatar) {
    User newUser = new User();
    newUser.setEmail(email);
    newUser.setFullName(fullName != null ? fullName : "");
    if (avatar != null && !avatar.isEmpty()) {
      avatar = "defaul.jpg";
      newUser.setAvatar(avatar);
    } else {
      newUser.setAvatar("defaul.jpg");
    }

    // Set password ngẫu nhiên cho OAuth2 user (user sẽ không sử dụng password này)
    String randomPassword = "OAUTH2_" + UUID.randomUUID().toString().substring(0, 8);
    newUser.setPassword(passwordEncoder.encode(randomPassword));

    // Set role mặc định
    Role userRole = roleService.findRoleByName("USER");
    newUser.setRole(userRole);

    // Set các trường bắt buộc với giá trị mặc định
    newUser.setAddress("No Locaition");
    newUser.setPhone("1122334455"); // Số điện thoại mặc định 10 chữ số

    return handleSaveUser(newUser);

  }

  @Transactional
  public User updateOAuth2User(User existingUser, String fullName, String avatar) {
    boolean needUpdate = false;

    // if (avatar != null && avatar.equals(existingUser.getAvatar())) {
    // existingUser.setAvatar(avatar);
    // needUpdate =true;
    // }

    if (fullName != null && !fullName.equals(existingUser.getFullName())) {
      existingUser.setFullName(fullName);
      needUpdate = true;
    }

    if (needUpdate) {
      return handleSaveUser(existingUser);
    }

    return existingUser;
  }

  public Long getCountUser() {
    return this.userRepository.count();
  }

  public Long getCountProduct() {
    return productRepository.count();
  }

  public Long getCountOrder() {
    return orderRepository.count();
  }

  public Page<User> getUserPaginated(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return userRepository.findAll(pageable);
  }

  public Page<Order> getlistHistory(User user, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return this.orderRepository.findByUser(user, pageable);
  }

  @Transactional
  public Map<String, Object> cancelOrderDetailAjax(long orderDetailId, long userId) {

    OrderDetail detail = orderDetailRepository.findById(orderDetailId)
        .orElseThrow(() -> new RuntimeException("Không tìm thấy OrderDetail"));

    Order order = detail.getOrder();

    if (order.getUser().getId() != userId) {
      throw new RuntimeException("Bạn không có quyền hủy chi tiết đơn này!");
    }

    // ---- Logic kiểm tra quyền hủy (COD / Online / Pending / Unpaid / Paid) ----
    String paymentMethod = order.getPaymentMethod();
    String orderStatus = order.getStatus();
    String paymentStatus = order.getPaymentStatus();

    boolean allowCancel = false;

    if (paymentMethod.equalsIgnoreCase("COD")) {
      if (orderStatus.equals("Pending"))
        allowCancel = true;
    } else {
      if (paymentStatus.equals("Unpaid"))
        allowCancel = true;
      else if (paymentStatus.equals("Paid") && orderStatus.equals("Pending"))
        allowCancel = true;
    }

    if (!allowCancel) {
      throw new RuntimeException("Không thể hủy chi tiết đơn hàng ở trạng thái hiện tại!");
    }

    // ======= ROLLBACK INVENTORY =======
    Product product = detail.getProduct();
    product.setQuantity(product.getQuantity() + detail.getQuantity()); // trả lại kho
    product.setSold(product.getSold() - detail.getQuantity()); // giảm sold
    productService.saveProduct(product);

    // ======= XÓA ORDERDETAIL =======
    orderDetailRepository.delete(detail);

    List<OrderDetail> remain = orderDetailRepository.findByOrder(order);

    Map<String, Object> response = new HashMap<>();

    if (remain.isEmpty()) {
      // Xóa luôn Order nếu không còn detail
      orderRepository.delete(order);
      response.put("orderDeleted", true);
      return response;
    }

    // Cập nhật tổng tiền
    double newTotal = remain.stream().mapToDouble(OrderDetail::getTotalPrice).sum();
    double newTotalShip = newTotal + 50000;

    response.put("orderDeleted", false);
    response.put("newTotal", newTotal);
    response.put("newTotalShip", newTotalShip);
    response.put("remainCount", remain.size());

    return response;
  }

  public void recalculateOrderPrice(Order order) {

    List<OrderDetail> details = orderDetailRepository.findByOrder(order);

    if (details.isEmpty()) {
      // Không còn chi tiết nào → Order sẽ bị xóa ở phần khác
      return;
    }

    double newTotal = details.stream()
        .mapToDouble(OrderDetail::getTotalPrice)
        .sum();

    order.setTotalPrice(newTotal);

    // Nếu có phí ship cố định (ví dụ 50k):
    double shipFee = 50000;

    order.setTotalPriceaddShip(newTotal + shipFee);

    orderRepository.save(order);
  }

  // Lấy tất cả đơn hàng
  public List<Order> getAllOrder() {
    return orderRepository.findAll();
  }

  public List<Order> getOrdersThisYear() {
    int currentYear = LocalDate.now().getYear();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    List<Order> ordersThisYear = orderRepository.findAll()
        .stream()
        .filter(o -> {
          try {
            LocalDate date = LocalDateTime.parse(o.getPaymentTime(), formatter).toLocalDate();
            return date.getYear() == currentYear;
          } catch (Exception e) {
            return false;
          }
        })
        .collect(Collectors.toList());

    return ordersThisYear;
  }

  public List<Double> getMonthlyRevenue() {
    List<Double> monthlyRevenue = new ArrayList<>(Collections.nCopies(12, 0.0));
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    List<Order> ordersThisYear = getOrdersThisYear();

    for (Order order : ordersThisYear) {
      try {
        LocalDate date = LocalDateTime.parse(order.getPaymentTime(), formatter).toLocalDate();
        int month = date.getMonthValue(); // 1-12
        monthlyRevenue.set(month - 1,
            monthlyRevenue.get(month - 1) + order.getTotalPriceaddShip());
      } catch (Exception e) {
        // bỏ qua nếu format sai
      }
    }

    System.out.println("Monthly Revenue: " + monthlyRevenue); // kiểm tra
    return monthlyRevenue;
  }

  // Đếm đơn hàng theo trạng thái
  public int countByStatus(String status) {
    return orderRepository.countByStatus(status);
  }

  public List<ProductSales> getTop5Products() {
    return orderDetailRepository.findTop5Products();
  }

}
