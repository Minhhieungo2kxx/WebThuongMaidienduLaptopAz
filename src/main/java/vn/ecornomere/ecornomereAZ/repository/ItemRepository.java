package vn.ecornomere.ecornomereAZ.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.ecornomere.ecornomereAZ.model.Product;

@Repository
public interface ItemRepository extends JpaRepository<Product, Long> {
    // Tìm sản phẩm có tên khớp chính xác
    List<Product> findByName(String name);

    // Hoặc: tìm sản phẩm có tên chứa chuỗi (giống LIKE %name%)
    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByTarget(String target); // ➕ thêm dòng này

    List<Product> findByTargetIn(List<String> targets);

}
