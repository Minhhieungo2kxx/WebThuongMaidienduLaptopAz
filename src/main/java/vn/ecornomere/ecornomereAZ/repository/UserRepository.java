package vn.ecornomere.ecornomereAZ.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.ecornomere.ecornomereAZ.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User save(User newUser);

  // find by email and address
  List<User> findByEmailAndAddress(String email, String address);

  User findById(long id);

  void delete(User user);

  boolean existsByEmail(String email);

  User findByEmail(String email);

}
