package vn.hoidanit.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.hoidanit.laptopshop.domain.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
  User save(User newUser);
  List<User> findByEmail(String email);
  // find by email and address
  List<User> findByEmailAndAddress(String email, String address);

}
