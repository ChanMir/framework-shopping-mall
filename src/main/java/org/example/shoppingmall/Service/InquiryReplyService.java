package org.example.shoppingmall.Service;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.domain.*;
import org.example.shoppingmall.domain.dto.InquiryReplyRequestDTO;
import org.example.shoppingmall.repository.InquiryReplyRepository;
import org.example.shoppingmall.repository.InquiryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryReplyService {

    private final InquiryReplyRepository inquiryReplyRepository;
    private final InquiryRepository inquiryRepository;

    public List<InquiryReply> getReplies(Long inquiryId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new IllegalArgumentException("문의글을 찾을 수 없습니다."));
        return inquiryReplyRepository.findByInquiry(inquiry);
    }

    public void writeReply(Member member, InquiryReplyRequestDTO dto) {

        Inquiry inquiry = inquiryRepository.findById(dto.getInquiryId())
                .orElseThrow(() -> new IllegalArgumentException("문의글을 찾을 수 없습니다."));

        InquiryReply reply = new InquiryReply();
        reply.setInquiry(inquiry);
        reply.setMember(member);
        reply.setContent(dto.getContent());

        inquiryReplyRepository.save(reply);

        inquiryRepository.save(inquiry);
    }

    public void updateReply(Member member, InquiryReplyRequestDTO dto) {

        InquiryReply reply = inquiryReplyRepository.findById(dto.getReplyId())
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        if (!reply.getMember().getId().equals(member.getId())
                && !member.getRole().name().equals("ADMIN")) {
            throw new IllegalStateException("수정 권한이 없습니다.");
        }

        reply.setContent(dto.getContent());
        inquiryReplyRepository.save(reply);
    }

    public void deleteReply(Member member, Long replyId) {

        InquiryReply reply = inquiryReplyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        if (!reply.getMember().getId().equals(member.getId())
                && !member.getRole().name().equals("ADMIN")) {
            throw new IllegalStateException("삭제 권한이 없습니다.");
        }

        inquiryReplyRepository.delete(reply);
    }
}
