package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class UserService {
  final private UserRepository userRepository;

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

  public List<User> findAllUsersByEmail(String email) {
    return this.userRepository.findByEmail(email);
  }

  public List<User> findAllUsersByEmailAndAddress(String email, String address) {
    return this.userRepository.findByEmailAndAddress(email, address);
  }

  public User handleSaveUser(User newUser) {
    return this.userRepository.save(newUser);
  }
}
