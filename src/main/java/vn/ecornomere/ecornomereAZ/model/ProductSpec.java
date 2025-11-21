package vn.ecornomere.ecornomereAZ.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_specs")

public class ProductSpec {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private long id;

      @ManyToOne
      @JoinColumn(name = "product_id")
      private Product product;

      @Column(columnDefinition = "MEDIUMTEXT")
      private String screenSize; // Kích thước màn hình

      @Column(columnDefinition = "MEDIUMTEXT")
      private String cpu; // Bộ vi xử lý

      private String ram; // Dung lượng RAM
      private String storage; // Dung lượng ổ cứng

      private String graphicsCard; // Card đồ họa

      @Column(columnDefinition = "MEDIUMTEXT")
      private String battery; // Thông tin pin

      @Column(columnDefinition = "MEDIUMTEXT")
      private String operatingSystem; // Hệ điều hành

      public long getId() {
            return id;
      }

      public void setId(long id) {
            this.id = id;
      }

      public Product getProduct() {
            return product;
      }

      public void setProduct(Product product) {
            this.product = product;
      }

      public String getScreenSize() {
            return screenSize;
      }

      public void setScreenSize(String screenSize) {
            this.screenSize = screenSize;
      }

      public String getCpu() {
            return cpu;
      }

      public void setCpu(String cpu) {
            this.cpu = cpu;
      }

      public String getRam() {
            return ram;
      }

      public void setRam(String ram) {
            this.ram = ram;
      }

      public String getStorage() {
            return storage;
      }

      public void setStorage(String storage) {
            this.storage = storage;
      }

      public String getGraphicsCard() {
            return graphicsCard;
      }

      public void setGraphicsCard(String graphicsCard) {
            this.graphicsCard = graphicsCard;
      }

      public String getBattery() {
            return battery;
      }

      public void setBattery(String battery) {
            this.battery = battery;
      }

      public String getOperatingSystem() {
            return operatingSystem;
      }

      public void setOperatingSystem(String operatingSystem) {
            this.operatingSystem = operatingSystem;
      }

}
