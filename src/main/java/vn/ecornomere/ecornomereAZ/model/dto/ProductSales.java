package vn.ecornomere.ecornomereAZ.model.dto;

import vn.ecornomere.ecornomereAZ.model.Product;

public class ProductSales {
      private Product product;
      private Long totalSold;

      public Product getProduct() {
            return product;
      }

      public Long getTotalSold() {
            return totalSold;
      }

      public ProductSales(Product product, Long totalSold) {
            this.product = product;
            this.totalSold = totalSold;
      }

}
