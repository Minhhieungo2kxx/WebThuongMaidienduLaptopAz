package vn.ecornomere.ecornomereAZ.dto.response;

import lombok.*;
import vn.ecornomere.ecornomereAZ.dto.response.OrderDetailDTO;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderHistoryDTO {
    private Long id;

    private String receiverName;
    private String receiverAddress;
    private String receiverPhone;

    private double totalPrice;
    private double totalPriceAddShip;

    private String status;
    private String paymentStatus;
    private String paymentMethod;

    private LocalDateTime paymentTime;
    private String paymentTimeDisplay;

    public boolean isAllowCancel;

    private List<OrderDetailDTO> orderDetails;
}
