package org.example.shoppingmall.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.domain.*;
import org.example.shoppingmall.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final DeliveryService deliveryService;
    private final PaymentRepository paymentRepository;
    private final DeliveryRepository deliveryRepository;
    public List<Order> findAll() {
        return orderRepository.findAll();
    }


    public Order createOrder(Member member, String address) {

        List<Cart> cartItems = cartRepository.findByMember(member);
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("장바구니가 비어 있습니다.");
        }

        int totalPrice = cartItems.stream()
                .mapToInt(c -> c.getProduct().getPrice().intValue() * c.getQuantity())
                .sum();

        // 주문 생성
        Order order = new Order();
        order.setMember(member);
        order.setTotalPrice(totalPrice);
        order.setStatus(OrderStatus.PENDING);
        orderRepository.save(order);

        // 주문 아이템 생성
        for (Cart cart : cartItems) {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(cart.getProduct());
            item.setQuantity(cart.getQuantity());
            item.setPrice(cart.getProduct().getPrice().doubleValue());
            orderItemRepository.save(item);
        }

        // 배송 자동 생성 — 주소를 넘김
        deliveryService.createDelivery(order, address);

        // 장바구니 비우기
        cartRepository.deleteAll(cartItems);

        return order;
    }


    public List<Order> getOrders(Member member) {
        return orderRepository.findByMember(member);
    }

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));
    }
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));
    }

    public List<Order> findByMember(Member member) {
        return orderRepository.findByMember(member);
    }

    @Transactional
    public void cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        Delivery delivery = deliveryRepository.findByOrder(order);
        Payment payment = paymentRepository.findByOrder(order);

        // 1) 배송이 시작된 경우 취소 불가
        if (delivery != null && delivery.isDeliveryStarted()) {
            throw new IllegalStateException("배송이 시작된 주문은 취소할 수 없습니다.");
        }

        // 2) 주문 상태 변경
        if (!order.isCancelable()) {
            throw new IllegalStateException("현재 상태에서는 주문 취소가 불가능합니다.");
        }
        order.setStatus(OrderStatus.CANCELLED);

        // 3) 배송 상태 변경
        if (delivery != null) {
            delivery.setState(DeliveryState.CANCELED);
        }

        // 4) 결제 취소 처리
        if (payment != null && payment.getStatus() == PaymentStatus.SUCCESS) {
            payment.setStatus(PaymentStatus.REFUNDED);
        }

        orderRepository.save(order);
    }
    // 주문 상태 변경
    @Transactional
    public void updateStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        order.setStatus(status);
        orderRepository.save(order);
    }


}
