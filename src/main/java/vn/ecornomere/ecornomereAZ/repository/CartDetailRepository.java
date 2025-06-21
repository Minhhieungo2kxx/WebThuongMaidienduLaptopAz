package vn.ecornomere.ecornomereAZ.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.ecornomere.ecornomereAZ.model.Cart;
import vn.ecornomere.ecornomereAZ.model.CartDetail;
import vn.ecornomere.ecornomereAZ.model.Product;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    CartDetail findByCartAndProduct(Cart cart, Product product);

    List<CartDetail> findByCart(Cart cart); // ➕ thêm dòng này

    CartDetail findById(long id);

    CartDetail findByProduct(Product product);

}
