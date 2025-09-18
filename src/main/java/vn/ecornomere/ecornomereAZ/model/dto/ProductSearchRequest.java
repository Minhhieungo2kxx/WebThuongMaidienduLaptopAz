package vn.ecornomere.ecornomereAZ.model.dto;

public class ProductSearchRequest {
      private String searchTerm;
      private int page = 0;
      private int size = 8;

      // Getters và Setters
      public String getSearchTerm() {
            return searchTerm;
      }

      public void setSearchTerm(String searchTerm) {
            this.searchTerm = searchTerm;
      }

      public int getPage() {
            return page;
      }

      public void setPage(int page) {
            this.page = page;
      }

      public int getSize() {
            return size;
      }

      public void setSize(int size) {
            this.size = size;
      }

}
