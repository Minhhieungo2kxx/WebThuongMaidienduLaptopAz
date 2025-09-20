package vn.ecornomere.ecornomereAZ.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "suppliers")
public class Supplier {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private long id; // ID của nhà cung cấp

      @NotBlank(message = "Tên nhà cung cấp không được để trống")
      private String name; // Tên nhà cung cấp

      private String address; // Địa chỉ nhà cung cấp

      private String phone; // Số điện thoại nhà cung cấp

      private String email; // Email nhà cung cấp

      @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
      private List<Stock> stocks; // Các phiếu nhập kho liên quan đến nhà cung cấp này

      public long getId() {
            return id;
      }

      public void setId(long id) {
            this.id = id;
      }

      public String getName() {
            return name;
      }

      public void setName(String name) {
            this.name = name;
      }

      public String getAddress() {
            return address;
      }

      public void setAddress(String address) {
            this.address = address;
      }

      public String getPhone() {
            return phone;
      }

      public void setPhone(String phone) {
            this.phone = phone;
      }

      public String getEmail() {
            return email;
      }

      public void setEmail(String email) {
            this.email = email;
      }

      public List<Stock> getStocks() {
            return stocks;
      }

      public void setStocks(List<Stock> stocks) {
            this.stocks = stocks;
      }

}
