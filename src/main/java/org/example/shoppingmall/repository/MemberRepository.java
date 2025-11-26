package org.example.shoppingmall.repository;

import org.example.shoppingmall.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository <Member, Long>{
}
