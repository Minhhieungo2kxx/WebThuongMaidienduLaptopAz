package vn.ecornomere.ecornomereAZ.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.ecornomere.ecornomereAZ.dto.request.ProductSales;
import vn.ecornomere.ecornomereAZ.model.entity.Order;
import vn.ecornomere.ecornomereAZ.model.entity.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

      List<OrderDetail> findByOrder(Order order);

      List<OrderDetail> findByProductId(Long productId);

      @Query("SELECT new vn.ecornomere.ecornomereAZ.dto.request.ProductSales(od.product, SUM(od.quantity)) " +
                  "FROM OrderDetail od " +
                  "GROUP BY od.product " +
                  "ORDER BY SUM(od.quantity) DESC")
      List<ProductSales> findTopProducts(Pageable pageable);

      default List<ProductSales> findTop5Products() {
            return findTopProducts(PageRequest.of(0, 5));
      }

}
