package vn.ecornomere.ecornomereAZ.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double totalPrice;
    private double totalPriceaddShip;
    private String receiverName;
    private String receiverAddress;
    private String receiverPhone;
    private String status;
    private LocalDateTime paymentTime;

    private String paymentStatus; // Ví dụ: "Paid", "Unpaid"

    private String paymentMethod; // Ví dụ: "Online", "COD"

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;



}
