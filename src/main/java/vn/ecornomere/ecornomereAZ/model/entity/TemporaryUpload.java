package vn.ecornomere.ecornomereAZ.model.entity;

import jakarta.persistence.*;
import lombok.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Table(name = "temporary_uploads", indexes = {
            @Index(name = "idx_temp_used_created", columnList = "used, createdAt")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TemporaryUpload {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

      @Column(nullable = false, unique = true)
      private String publicId;

      @Column(nullable = false)
      private String url;

      @Column(nullable = false)
      private String resourceType;


      @Column(nullable = false)
      private Instant createdAt;

      @Column(nullable = false)
      private boolean used;

}
