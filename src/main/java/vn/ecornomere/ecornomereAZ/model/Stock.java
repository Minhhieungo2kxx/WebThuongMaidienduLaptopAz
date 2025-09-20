package vn.ecornomere.ecornomereAZ.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "stock")

public class Stock {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private long id;

      @NotNull(message = "Ngày nhập kho không được để trống")

      private String date;

      @ManyToOne
      @JoinColumn(name = "user_id")
      private User user;

      @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, orphanRemoval = true)
      private List<StockDetail> stockDetails; // Danh sách chi tiết nhập kho

      @ManyToOne
      @JoinColumn(name = "supplier_id")
      private Supplier supplier;

      @NotNull(message = "Tổng giá trị nhập kho không được để trống")
      private double totalValue; // Tổng giá trị của đợt nhập kho

      @Column(length = 500) // Giới hạn độ dài của ghi chú (nếu có)
      private String note; // Ghi chú (nếu có)

      public long getId() {
            return id;
      }

      public void setId(long id) {
            this.id = id;
      }

      public String getDate() {
            return date;
      }

      public void setDate(String date) {
            this.date = date;
      }

      public User getUser() {
            return user;
      }

      public void setUser(User user) {
            this.user = user;
      }

      public List<StockDetail> getStockDetails() {
            return stockDetails;
      }

      public void setStockDetails(List<StockDetail> stockDetails) {
            this.stockDetails = stockDetails;
      }

      public double getTotalValue() {
            return totalValue;
      }

      public void setTotalValue(double totalValue) {
            this.totalValue = totalValue;
      }

      public String getNote() {
            return note;
      }

      public void setNote(String note) {
            this.note = note;
      }

      public Supplier getSupplier() {
            return supplier;
      }

      public void setSupplier(Supplier supplier) {
            this.supplier = supplier;
      }

}
