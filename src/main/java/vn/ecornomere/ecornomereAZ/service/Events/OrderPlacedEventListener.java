package vn.ecornomere.ecornomereAZ.service.Events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import vn.ecornomere.ecornomereAZ.dto.record.OrderPlacedEvent;
import vn.ecornomere.ecornomereAZ.model.entity.Order;
import vn.ecornomere.ecornomereAZ.model.entity.OrderDetail;
import vn.ecornomere.ecornomereAZ.repository.OrderDetailRepository;
import vn.ecornomere.ecornomereAZ.repository.OrderRepository;
import vn.ecornomere.ecornomereAZ.service.SendEmail.EmailService;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderPlacedEventListener {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final EmailService emailService;

    @Async("taskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(OrderPlacedEvent event) {

        try {
            log.info("Received event {}", event.orderId());

            Order order = orderRepository.findById(event.orderId())
                    .orElseThrow();
            List<OrderDetail> details =
                    orderDetailRepository.findByOrder(order);
            emailService.sendOrderConfirmationEmail(
                    event.email(),
                    order,
                    details
            );
            log.info("Email sent");

        } catch (Exception e) {
            log.error("Email failed", e);
        }
    }
}
