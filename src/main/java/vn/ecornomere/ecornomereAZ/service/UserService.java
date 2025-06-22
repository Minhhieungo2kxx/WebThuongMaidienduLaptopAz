package vn.ecornomere.ecornomereAZ.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.ecornomere.ecornomereAZ.model.Role;
import vn.ecornomere.ecornomereAZ.model.User;
import vn.ecornomere.ecornomereAZ.model.dto.RegisterDTO;
import vn.ecornomere.ecornomereAZ.repository.OrderRepository;
import vn.ecornomere.ecornomereAZ.repository.ProductRepository;
import vn.ecornomere.ecornomereAZ.repository.UserRepository;

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

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Optional<User> findUserById(Long id) {
    return this.userRepository.findById(id);
  }

  public void deleteUser(User user) {
    this.userRepository.delete(user);
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

}
