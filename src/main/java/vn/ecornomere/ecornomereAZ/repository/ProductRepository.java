package vn.ecornomere.ecornomereAZ.repository;



import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.ecornomere.ecornomereAZ.model.entity.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

      @Query("SELECT p FROM Product p WHERE "
                  + "(LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) "
                  + "OR LOWER(p.detailDesc) LIKE LOWER(CONCAT('%', :searchTerm, '%')) "
                  + "OR LOWER(p.shortDesc) LIKE LOWER(CONCAT('%', :searchTerm, '%')) "
                  + "OR LOWER(p.factory) LIKE LOWER(CONCAT('%', :searchTerm, '%')) "
                  + "OR LOWER(p.target) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) ")
      Page<Product> searchProducts(@Param("searchTerm") String searchTerm, Pageable pageable);

      @Lock(LockModeType.PESSIMISTIC_WRITE)
      @Query("""
            select p
            from Product p
            where p.id in :ids
            order by p.id
            """)
      List<Product> findAllForUpdate(List<Long> ids);

      List<Product> findTop10ByNameContainingIgnoreCase(String keyword);
//      @Query("""
//        SELECT p FROM Product p
//        WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
//        OR LOWER(p.shortDesc) LIKE LOWER(CONCAT('%', :keyword, '%'))
//        OR LOWER(p.detailDesc) LIKE LOWER(CONCAT('%', :keyword, '%'))
//    """)
//      List<Product> searchProducts(@Param("keyword") String keyword);

      Page<Product> findAll(Pageable pageable);

}
