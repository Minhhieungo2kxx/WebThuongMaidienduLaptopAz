package vn.ecornomere.ecornomereAZ.model.document;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDocument {
    private Long id;

    private String name;

    private String shortDesc;

    private String detailDesc;

    private String factory;

    private String target;

    private Double price;

    private Long quantity;

    private Long sold;

}
