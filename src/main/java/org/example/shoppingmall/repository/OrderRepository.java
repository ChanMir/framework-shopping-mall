package org.example.shoppingmall.repository;

import org.example.shoppingmall.domain.Order;
import org.example.shoppingmall.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByMember(Member member);
}
