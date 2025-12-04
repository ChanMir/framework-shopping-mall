package org.example.shoppingmall.repository;

import org.example.shoppingmall.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    Optional<Member> findByUsername(String username);
    // 아이디 찾기: 전화번호로 찾기
    Optional<Member> findByPhone(String phone);

    // 비밀번호 재설정: 아이디 + 전화번호로 찾기
    Optional<Member> findByUsernameAndPhone(String username, String phone);

}
