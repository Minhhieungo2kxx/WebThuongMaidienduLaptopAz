package vn.ecornomere.ecornomereAZ.repository;

import vn.ecornomere.ecornomereAZ.model.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
      List<Order> findByUserId(Long id);

      Page<Order> findByUser(User user, Pageable pageable);

      

      // Đếm đơn hàng theo trạng thái
      int countByStatus(String status);

}
