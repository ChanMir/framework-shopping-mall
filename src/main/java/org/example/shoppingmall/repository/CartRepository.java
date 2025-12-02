package org.example.shoppingmall.repository;

import org.example.shoppingmall.domain.Cart;
import org.example.shoppingmall.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    // 로그인한 회원의 전체 장바구니 목록
    List<Cart> findByMember(Member member);

    // 특정 회원 + 특정 상품이 이미 장바구니에 있는지
    Optional<Cart> findByMemberAndProductId(Member member, Long productId);
}
