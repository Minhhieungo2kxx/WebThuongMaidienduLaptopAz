package vn.ecornomere.ecornomereAZ.repository;

import vn.ecornomere.ecornomereAZ.model.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
      List<Order> findByUser(User user);

}
