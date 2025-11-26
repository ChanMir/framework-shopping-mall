package org.example.shoppingmall.Service;

import org.example.shoppingmall.domain.Member;
import org.example.shoppingmall.domain.MemberRegisterRequest;
import org.example.shoppingmall.domain.MemberUpdateRequest;
import org.example.shoppingmall.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class MemberService{

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member register(MemberRegisterRequest request) {
        Member member = new Member();
        member.setUsername(request.getUsername());
        member.setPassword(request.getPassword());
        member.setEmail(request.getEmail());
        member.setPhone(request.getPhone());
        member.setRole("MEMBER");
        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public Optional<Member> login(String username, String password) {
        return memberRepository.findByUsernameAndPassword(username, password);
    }

    @Transactional(readOnly = true)
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public void updateMember(Long id, MemberUpdateRequest request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        member.setEmail(request.getEmail());
        member.setPhone(request.getPhone());
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean isAdmin(Long memberId) {
        return memberRepository.findById(memberId)
                .map(m -> "ADMIN".equals(m.getRole()))
                .orElse(false);
    }
}