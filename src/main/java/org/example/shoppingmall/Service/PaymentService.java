package org.example.shoppingmall.Service;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.domain.*;
import org.example.shoppingmall.repository.OrderRepository;
import org.example.shoppingmall.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    // 결제 생성
    public Payment createPayment(Long orderId, PaymentMethod method) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setMethod(method);
        payment.setStatus(PaymentStatus.SUCCESS); // 실제 결제라면 결제 API 결과 따라 변경

        // 주문 상태도 결제완료로 갱신
        order.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);

        return paymentRepository.save(payment);
    }

    public Payment getPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }
}
