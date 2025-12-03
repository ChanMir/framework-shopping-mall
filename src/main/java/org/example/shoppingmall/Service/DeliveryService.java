package org.example.shoppingmall.Service;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.domain.*;
import org.example.shoppingmall.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public Delivery createDelivery(Order order, String address) {

        Delivery delivery = new Delivery();
        delivery.setOrder(order);
        delivery.setAddress(address);
        delivery.setState(DeliveryState.PREPARING);

        // 🔥 송장번호 자동 생성
        delivery.setTrackingNo(generateTrackingNo());

        return deliveryRepository.save(delivery);
    }

    // 🔥 송장번호 자동 생성 함수
    private String generateTrackingNo() {
        String timestamp = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        int random = (int)(Math.random() * 9000) + 1000; // 4자리 난수

        return "TRK-" + timestamp + "-" + random;
    }

    public Delivery findByOrderId(Long orderId) {
        return deliveryRepository.findByOrderId(orderId);
    }
}
