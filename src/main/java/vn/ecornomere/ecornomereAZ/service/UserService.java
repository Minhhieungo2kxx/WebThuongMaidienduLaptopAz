package vn.ecornomere.ecornomereAZ.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.ecornomere.ecornomereAZ.enums.PaymentMethod;
import vn.ecornomere.ecornomereAZ.dto.response.OrderDetailDTO;
import vn.ecornomere.ecornomereAZ.dto.response.OrderHistoryDTO;
import vn.ecornomere.ecornomereAZ.dto.request.ProductSales;
import vn.ecornomere.ecornomereAZ.dto.request.RegisterDTO;
import vn.ecornomere.ecornomereAZ.model.entity.Cart;
import vn.ecornomere.ecornomereAZ.model.entity.Order;
import vn.ecornomere.ecornomereAZ.model.entity.OrderDetail;
import vn.ecornomere.ecornomereAZ.model.entity.Product;
import vn.ecornomere.ecornomereAZ.model.entity.Role;
import vn.ecornomere.ecornomereAZ.model.entity.User;
import vn.ecornomere.ecornomereAZ.repository.CartRepository;
import vn.ecornomere.ecornomereAZ.repository.OrderDetailRepository;
import vn.ecornomere.ecornomereAZ.repository.OrderRepository;
import vn.ecornomere.ecornomereAZ.repository.ProductRepository;
import vn.ecornomere.ecornomereAZ.repository.UserRepository;
import vn.ecornomere.ecornomereAZ.service.UploadFile.TemporaryUpload;
import vn.ecornomere.ecornomereAZ.utils.UploadFile;

import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final RoleService roleService;

  private final ProductRepository productRepository;

  private final OrderRepository orderRepository;

  private final CartRepository cartRepository;

  private final OrderDetailRepository orderDetailRepository;

  private final ProductService productService;

  private final TemporaryUpload temporaryUpload;
  private final UploadFile uploadFile = new UploadFile();



  public String showDetailFormUser(Long id, Model model) {
    User userdetail =findUserById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));
    model.addAttribute("detailUser", userdetail);
    return "admin/user/detailuser"; //
  }
  @Transactional
  public String updateUserAD(User updatedUser, RedirectAttributes redirectAttributes) {
    User existingUser = findUserById(updatedUser.getId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid user Id"));
    String oldPublicId = existingUser.getAvatarPublicId();

    ModelMapper map = new ModelMapper();
    map.typeMap(User.class, User.class)
            .addMappings(m -> m.skip(User::setId))
            .addMappings(m -> m.skip(User::setPassword))
            .addMappings(m -> m.skip(User::setReviews))
            .addMappings(m -> m.skip(User::setStocks))
            .addMappings(m -> m.skip(User::setChatMessages))
            .addMappings(m -> m.skip(User::setRole));

    map.map(updatedUser, existingUser);
    // password
    if (updatedUser.getPassword() != null
            && !updatedUser.getPassword().isBlank()) {

      if (!updatedUser.getPassword().equals(existingUser.getPassword())) {
        existingUser.setPassword(
                passwordEncoder.encode(updatedUser.getPassword()));
      }
    }

    // avatar update
    if (updatedUser.getAvatarPublicId() != null &&
            !updatedUser.getAvatarPublicId().isEmpty()) {

      existingUser.setAvatar(updatedUser.getAvatar());
      existingUser.setAvatarPublicId(updatedUser.getAvatarPublicId());
      existingUser.setAvatarResourceType(updatedUser.getAvatarResourceType());

      // đánh dấu file cũ là unused
      temporaryUpload.markAsUnused(oldPublicId);
    }
    handleSaveUser(existingUser);
    redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thành công!");
    return "redirect:/admin/list/user";
  }

  public String listUsersAd(String pageParam, Model model) {
    int page = 0;
    int pageSize = 5;
    try {
      page = Integer.parseInt(pageParam);
      if (page < 0)
        page = 0;
    } catch (NumberFormatException e) {
      // Nếu người dùng nhập sai, mặc định về trang đầu
      page = 0;
    }
    Page<User> userPage = getUserPaginated(page, pageSize);
    model.addAttribute("users", userPage.getContent());
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", userPage.getTotalPages());
    return "admin/user/listuser"; // tạo 1 file JSP hiển thị danh sách
  }
  @Transactional
  public String createUserAd(User user, BindingResult result, Long roleId) {
    if (result.hasErrors()) {
      return "admin/user/create";
    }
    Role role = roleService.findRoleId(roleId);

    if (role == null) {
      throw new IllegalArgumentException("Invalid role id : " + roleId);
    }
    user.setRole(role);
    if (user.getPassword() != null
            && !user.getPassword().isEmpty()) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
        handleSaveUser(user);
    } else {
        handleSaveUser(user);
    }
      temporaryUpload.markAsUsed(user.getAvatarPublicId());

    return "redirect:/admin/list/user";
  }


  public Optional<User> findUserById(Long id) {
    return this.userRepository.findById(id);
  }

  @Transactional
  public void deleteUser(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User không hợp lệ.");
    }
    // 1. Xử lý Order
    List<Order> orders = orderRepository.findByUserId(user.getId());
    if (!orders.isEmpty()) {
      for (Order order : orders) {
        order.setUser(null);
      }
      orderRepository.saveAll(orders);
    }
    // 2. Xử lý Cart
    Cart cart = cartRepository.findByUser(user);
    if (cart != null) {
      cart.setUser(null);
      cartRepository.save(cart);
    }
    // 3. KHÔNG xóa Cloudinary ngay → chỉ mark
    if (user.getAvatarPublicId() != null) {
      temporaryUpload.markAsUnused(user.getAvatarPublicId());
    }
    // 4. Xóa user
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
            LocalDate date = LocalDateTime.parse(o.getPaymentTime().toString(), formatter).toLocalDate();
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
        LocalDate date = LocalDateTime.parse(order.getPaymentTime().toString(), formatter).toLocalDate();
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
  public OrderHistoryDTO toDTO(Order order) {

    OrderHistoryDTO dto = new OrderHistoryDTO();

    dto.setId(order.getId());
    dto.setReceiverName(order.getReceiverName());
    dto.setReceiverAddress(order.getReceiverAddress());
    dto.setReceiverPhone(order.getReceiverPhone());

    dto.setTotalPrice(order.getTotalPrice());
    dto.setTotalPriceAddShip(order.getTotalPriceaddShip());

    dto.setStatus(order.getStatus());
    dto.setPaymentStatus(order.getPaymentStatus());
    dto.setPaymentMethod(order.getPaymentMethod());

    dto.setPaymentTime(order.getPaymentTime());
    if (Objects.equals(order.getPaymentMethod(), PaymentMethod.COD.name())) {
      dto.setPaymentTimeDisplay("Thanh toán khi nhận hàng");
    }
    else {
      dto.setPaymentTimeDisplay(
              order.getPaymentTime()
                      .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
    }

    List<OrderDetailDTO> details = order.getOrderDetails().stream()
            .map(od -> {
              OrderDetailDTO d = new OrderDetailDTO();
              d.setId(od.getId());
              d.setProductName(od.getProduct().getName());
              d.setProductImage(od.getProduct().getImage());
              d.setPrice(od.getPrice());
              d.setQuantity((int)od.getQuantity());
              d.setTotalPrice(od.getTotalPrice());
              return d;
            }).toList();

    dto.setOrderDetails(details);

    return dto;
  }

}
