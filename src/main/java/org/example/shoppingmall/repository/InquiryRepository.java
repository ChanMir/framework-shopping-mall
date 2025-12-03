package org.example.shoppingmall.repository;

import org.example.shoppingmall.domain.Inquiry;
import org.example.shoppingmall.domain.Member;
import org.example.shoppingmall.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryRepository extends JpaRepository <Inquiry, Long>{
    List<Inquiry> findByMember(Member member);
}
