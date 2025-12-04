package org.example.shoppingmall.Service;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.domain.Member;
import org.example.shoppingmall.domain.MemberRegisterRequest;
import org.example.shoppingmall.domain.Role;
import org.example.shoppingmall.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    public Member register(MemberRegisterRequest request) {

        Member member = new Member();
        member.setUsername(request.getUsername());
        member.setPassword(encoder.encode(request.getPassword())); // 암호화
        member.setEmail(request.getEmail());
        member.setPhone(request.getPhone());
        member.setRole(Role.USER);

        return memberRepository.save(member);
    }
    // 아이디 찾기
    public String findIdByPhone(String phone) {
        return memberRepository.findByPhone(phone)
                .map(Member::getUsername)
                .orElse(null);
    }

    // 임시 비밀번호 생성
    public String createTempPassword() {
        return String.valueOf((int)(Math.random() * 900000) + 100000); // 6자리 숫자
    }

    // 비밀번호 재설정
    public String resetPassword(String username, String phone) {

        Member member = memberRepository
                .findByUsernameAndPhone(username, phone)
                .orElse(null);

        if (member == null) return null;

        String tempPw = createTempPassword();
        member.setPassword(encoder.encode(tempPw));
        memberRepository.save(member);

        return tempPw;
    }
    public Optional<Member> getLoginMember(String username) {
        return memberRepository.findByUsername(username);
    }

    public void updateMemberInfo(Member member, String newUsername, String phone, String email) {
        member.setUsername(newUsername);
        member.setPhone(phone);
        member.setEmail(email);
        memberRepository.save(member);
    }
}
