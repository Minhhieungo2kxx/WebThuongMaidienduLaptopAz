package vn.ecornomere.ecornomereAZ.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailDTO {
    private Long id;

    private String productName;
    private String productImage;

    private double price;
    private int quantity;
    private double totalPrice;
}
