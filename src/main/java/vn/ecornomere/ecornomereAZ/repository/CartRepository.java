package vn.ecornomere.ecornomereAZ.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.ecornomere.ecornomereAZ.model.entity.Cart;
import vn.ecornomere.ecornomereAZ.model.entity.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);

}
