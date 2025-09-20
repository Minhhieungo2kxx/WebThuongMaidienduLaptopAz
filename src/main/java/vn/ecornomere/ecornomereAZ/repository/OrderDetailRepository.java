package vn.ecornomere.ecornomereAZ.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.ecornomere.ecornomereAZ.model.Order;
import vn.ecornomere.ecornomereAZ.model.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

      List<OrderDetail> findByOrder(Order order);

      List<OrderDetail> findByProductId(Long productId);

}
