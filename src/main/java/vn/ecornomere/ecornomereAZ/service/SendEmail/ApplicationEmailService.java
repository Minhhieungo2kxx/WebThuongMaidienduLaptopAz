package vn.ecornomere.ecornomereAZ.service.SendEmail;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import vn.ecornomere.ecornomereAZ.model.Order;
import vn.ecornomere.ecornomereAZ.model.OrderDetail;
import vn.ecornomere.ecornomereAZ.service.EmailService;

@Service
public class ApplicationEmailService {
      @Autowired
      private EmailService emailService;

      @Async("taskExecutor")
      public void sendOrder(String email, Order savedOrder, List<OrderDetail> orderDetails) {
            emailService.sendOrderConfirmationEmail(email, savedOrder, orderDetails);

      }

}
