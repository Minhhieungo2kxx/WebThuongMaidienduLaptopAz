package vn.ecornomere.ecornomereAZ.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.ecornomere.ecornomereAZ.model.User;
import vn.ecornomere.ecornomereAZ.model.entity.ChatMessage;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
      // Lấy lịch sử theo sessionId (cho user chưa đăng nhập)
      List<ChatMessage> findBySessionIdOrderByCreatedAtAsc(String sessionId);

      // Lấy lịch sử theo user (cho user đã đăng nhập)
      List<ChatMessage> findByUserOrderByCreatedAtAsc(User user);

      void deleteBySessionId(String sessionId);

      void deleteByUser(User user);

}