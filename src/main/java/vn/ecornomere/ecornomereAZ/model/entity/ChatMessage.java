package vn.ecornomere.ecornomereAZ.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import vn.ecornomere.ecornomereAZ.model.User;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

      private String sessionId;

      @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name = "user_id")
      private User user;

      @Column(columnDefinition = "MEDIUMTEXT")
      private String message;

      @Column(columnDefinition = "MEDIUMTEXT")
      private String response;

      @Column(name = "created_at")
      private LocalDateTime createdAt = LocalDateTime.now();

      public Long getId() {
            return id;
      }

      public void setId(Long id) {
            this.id = id;
      }

      public String getSessionId() {
            return sessionId;
      }

      public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
      }

      public User getUser() {
            return user;
      }

      public void setUser(User user) {
            this.user = user;
      }

      public String getMessage() {
            return message;
      }

      public void setMessage(String message) {
            this.message = message;
      }

      public String getResponse() {
            return response;
      }

      public void setResponse(String response) {
            this.response = response;
      }

      public LocalDateTime getCreatedAt() {
            return createdAt;
      }

      public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
      }

}
