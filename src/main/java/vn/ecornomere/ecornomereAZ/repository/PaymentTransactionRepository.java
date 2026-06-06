package vn.ecornomere.ecornomereAZ.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.ecornomere.ecornomereAZ.model.entity.PaymentTransaction;

import java.util.Optional;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
       select p
       from PaymentTransaction p
       where p.txnRef = :txnRef
       """)
    Optional<PaymentTransaction> findByTxnRefForUpdate(
            @Param("txnRef") String txnRef);

}
