package org.example.shoppingmall.Service;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.domain.*;
import org.example.shoppingmall.repository.CartRepository;
import org.example.shoppingmall.repository.OrderItemRepository;
import org.example.shoppingmall.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final DeliveryService deliveryService;

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
}
