package vn.ecornomere.ecornomereAZ.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.ecornomere.ecornomereAZ.model.User;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
  @SuppressWarnings({ "null", "unchecked" })
  User save(User newUser);
  List<User> findByEmail(String email);
  // find by email and address
  List<User> findByEmailAndAddress(String email, String address);

}
