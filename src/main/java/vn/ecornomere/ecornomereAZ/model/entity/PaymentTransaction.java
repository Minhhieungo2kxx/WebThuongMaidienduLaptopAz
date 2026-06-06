package vn.ecornomere.ecornomereAZ.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.ecornomere.ecornomereAZ.model.Enum.PaymentMethod;
import vn.ecornomere.ecornomereAZ.model.Enum.PaymentTransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_transactions")
@Getter
@Setter
public class PaymentTransaction {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true, nullable = false)
        private String txnRef;

        private String email;

        @Column(nullable = false)
        private BigDecimal amount;

        @Enumerated(EnumType.STRING)
        private PaymentMethod paymentMethod;

        @Enumerated(EnumType.STRING)
        private PaymentTransactionStatus status;

        @Column(columnDefinition = "TEXT")
        private String shippingInfoJson;

        private String transactionNo;

        private LocalDateTime paidAt;

        private LocalDateTime createdAt;
}
