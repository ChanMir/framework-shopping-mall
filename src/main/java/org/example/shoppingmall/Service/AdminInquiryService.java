package org.example.shoppingmall.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.domain.Inquiry;
import org.example.shoppingmall.domain.InquiryReply;
import org.example.shoppingmall.domain.InquiryStatus;
import org.example.shoppingmall.domain.Member;
import org.example.shoppingmall.repository.InquiryReplyRepository;
import org.example.shoppingmall.repository.InquiryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminInquiryService {

    private final InquiryRepository inquiryRepository;
    private final InquiryReplyRepository replyRepository;

    /** 전체 문의 조회 */
    public List<Inquiry> getAllInquiries() {
        return inquiryRepository.findAll();
    }

    /** 특정 문의 조회 */
    public Inquiry getInquiry(Long id) {
        return inquiryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("문의 없음"));
    }

    /** 답변 등록 */
    @Transactional
    public void addReply(Inquiry inquiry, Member member, String content) {

        InquiryReply reply = new InquiryReply();
        reply.setInquiry(inquiry);
        reply.setMember(member);
        reply.setContent(content);

        replyRepository.save(reply);

        inquiry.setStatus(InquiryStatus.ANSWERED);
    }

    /** 문의 상태 변경 */
    @Transactional
    public void updateStatus(Long inquiryId, InquiryStatus status) {

        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new IllegalArgumentException("문의 없음"));

        inquiry.setStatus(status);
    }
}
