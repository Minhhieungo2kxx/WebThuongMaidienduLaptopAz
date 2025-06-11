package vn.ecornomere.ecornomereAZ.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.ecornomere.ecornomereAZ.model.User;
import vn.ecornomere.ecornomereAZ.model.dto.RegisterDTO;
import vn.ecornomere.ecornomereAZ.repository.UserRepository;

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

  // public User findUserById(long id) {
  // return this.userRepository.findById(id);
  // }

}
