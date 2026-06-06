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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chat_messages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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



}
