package org.example.shoppingmall.Service;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.domain.Inquiry;
import org.example.shoppingmall.domain.InquiryStatus;
import org.example.shoppingmall.domain.Member;
import org.example.shoppingmall.repository.InquiryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    // 문의 등록
    public Inquiry createInquiry(Member member, String title, String content) {
        Inquiry i = new Inquiry();
        i.setMember(member);
        i.setTitle(title);
        i.setContent(content);
        i.setStatus(InquiryStatus.PENDING);
        return inquiryRepository.save(i);
    }

    // 회원 본인의 문의 목록
    public List<Inquiry> getMyInquiries(Member member) {
        return inquiryRepository.findByMember(member);
    }

    // 관리자: 전체 문의 조회
    public List<Inquiry> findAll() {
        return inquiryRepository.findAll();
    }

    // 문의 상세
    public Inquiry findById(Long id) {
        return inquiryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("문의가 없습니다."));
    }

    // 관리자 답변 처리
    public void answerInquiry(Long id, String answer) {
        Inquiry i = findById(id);
        i.setAnswer(answer);
        i.setStatus(InquiryStatus.ANSWERED);
        i.setAnsweredAt(LocalDateTime.now());
        inquiryRepository.save(i);
    }

    public void delete(Long id) {
        inquiryRepository.deleteById(id);
    }
    public Inquiry getInquiry(Long id) {
        return inquiryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("문의가 존재하지 않습니다."));
    }

}
