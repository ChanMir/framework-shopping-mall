package org.example.shoppingmall.repository;

import org.example.shoppingmall.domain.Delivery;
import org.example.shoppingmall.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Delivery findByOrderId(Long orderId);
    Delivery findByOrder(Order order);
}
