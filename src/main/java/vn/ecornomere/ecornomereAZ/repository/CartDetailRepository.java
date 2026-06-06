package vn.ecornomere.ecornomereAZ.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.ecornomere.ecornomereAZ.model.entity.Cart;
import vn.ecornomere.ecornomereAZ.model.entity.CartDetail;
import vn.ecornomere.ecornomereAZ.model.entity.Product;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    CartDetail findByCartAndProduct(Cart cart, Product product);

    List<CartDetail> findByCart(Cart cart); // thêm dòng này

    CartDetail findById(long id);

    CartDetail findByProduct(Product product);

    List<CartDetail> findByProductId(long id);

}
