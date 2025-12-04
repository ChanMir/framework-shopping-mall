package org.example.shoppingmall.repository;

import org.example.shoppingmall.domain.Inquiry;
import org.example.shoppingmall.domain.InquiryReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryReplyRepository extends JpaRepository<InquiryReply, Long> {

    List<InquiryReply> findByInquiry(Inquiry inquiry);
}
