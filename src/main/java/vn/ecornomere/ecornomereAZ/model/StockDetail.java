package vn.ecornomere.ecornomereAZ.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "stock_details")

public class StockDetail {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private long id; // ID của chi tiết nhập kho

      @ManyToOne
      @JoinColumn(name = "stock_id")
      private Stock stock;

      @ManyToOne
      @JoinColumn(name = "product_id")
      private Product product;

      private long quantity;

      @NotNull(message = "Giá nhập không được để trống")
      private double price;

      private double totalPrice;

      public long getId() {
            return id;
      }

      public void setId(long id) {
            this.id = id;
      }

      public Stock getStock() {
            return stock;
      }

      public void setStock(Stock stock) {
            this.stock = stock;
      }

      public Product getProduct() {
            return product;
      }

      public void setProduct(Product product) {
            this.product = product;
      }

      public long getQuantity() {
            return quantity;
      }

      public void setQuantity(long quantity) {
            this.quantity = quantity;
      }

      public double getPrice() {
            return price;
      }

      public void setPrice(double price) {
            this.price = price;
      }

      public double getTotalPrice() {
            return totalPrice;
      }

      public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
      }

}
