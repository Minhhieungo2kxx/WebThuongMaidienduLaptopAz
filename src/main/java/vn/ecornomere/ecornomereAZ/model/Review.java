package vn.ecornomere.ecornomereAZ.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reviews")
public class Review {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private long id;

      @ManyToOne
      @JoinColumn(name = "user_id")
      private User user; // Người đánh giá

      @ManyToOne
      @JoinColumn(name = "product_id")
      private Product product; // Sản phẩm được đánh giá

      private int rating; // Đánh giá (1-5 sao)
      private String comment; // Bình luận

      private String reviewDate; // Ngày đánh giá

      public long getId() {
            return id;
      }

      public void setId(long id) {
            this.id = id;
      }

      public User getUser() {
            return user;
      }

      public void setUser(User user) {
            this.user = user;
      }

      public Product getProduct() {
            return product;
      }

      public void setProduct(Product product) {
            this.product = product;
      }

      public int getRating() {
            return rating;
      }

      public void setRating(int rating) {
            this.rating = rating;
      }

      public String getComment() {
            return comment;
      }

      public void setComment(String comment) {
            this.comment = comment;
      }

      public String getReviewDate() {
            return reviewDate;
      }

      public void setReviewDate(String reviewDate) {
            this.reviewDate = reviewDate;
      }

}
