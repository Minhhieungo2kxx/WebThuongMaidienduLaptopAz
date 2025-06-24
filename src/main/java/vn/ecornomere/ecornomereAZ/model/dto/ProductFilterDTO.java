package vn.ecornomere.ecornomereAZ.model.dto;

import java.util.List;

public class ProductFilterDTO {
      private List<String> factory;
      private List<String> target;
      private List<String> price;
      private List<String> sort;
      private String page;

      public List<String> getFactory() {
            return factory;
      }

      public void setFactory(List<String> factory) {
            this.factory = factory;
      }

      public List<String> getTarget() {
            return target;
      }

      public void setTarget(List<String> target) {
            this.target = target;
      }

      public List<String> getPrice() {
            return price;
      }

      public void setPrice(List<String> price) {
            this.price = price;
      }

      public List<String> getSort() {
            return sort;
      }

      public void setSort(List<String> sort) {
            this.sort = sort;
      }

      public String getPage() {
            return page;
      }

      public void setPage(String page) {
            this.page = page;
      }

}
