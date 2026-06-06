package vn.ecornomere.ecornomereAZ.model.entity;

import java.util.List;


import jakarta.persistence.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "product")
    private List<CartDetail> cartDetails;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(min = 3, max = 100, message = "Tên sản phẩm phải từ 3 đến 100 ký tự")
    private String name;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @Min(value = 0, message = "Giá sản phẩm không được âm")
    private double price;

    @NotBlank(message = "URL không được để trống")
    private String image;


    @Column(name = "public_id")
    private String publicId;

    @Column(name = "resource_type")
    private String resourceType;

    // Mô tả chi tiết
    @NotBlank(message = "Mô tả chi tiết không được để trống")
    @Column(columnDefinition = "MEDIUMTEXT")
    private String detailDesc;

    // Mô tả ngắn
    @NotBlank(message = "Mô tả ngắn không được để trống")
    @Column(columnDefinition = "MEDIUMTEXT")
    private String shortDesc;

    @NotNull(message = "Số lượng sản phẩm không được để trống")
    @Min(value = 0, message = "Số lượng sản phẩm không được âm")
    private long quantity;

    @NotNull(message = "Số lượng đã bán không được để trống")
    @Min(value = 0, message = "Số lượng đã bán không được âm")
    private long sold;

    @NotBlank(message = "Nhà máy sản xuất không được để trống")
    private String factory;

    @NotBlank(message = "Đối tượng sử dụng không được để trống")
    private String target;

    @Version
    private Long version;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StockDetail> stockdetails;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProductSpec productSpecs;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;



}
