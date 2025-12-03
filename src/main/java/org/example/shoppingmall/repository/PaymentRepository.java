package org.example.shoppingmall.repository;

import org.example.shoppingmall.domain.Order;
import org.example.shoppingmall.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByOrderId(Long orderId);
    Payment findByOrder(Order order);
}
