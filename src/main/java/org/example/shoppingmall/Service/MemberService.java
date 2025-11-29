package org.example.shoppingmall.Service;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.domain.Member;
import org.example.shoppingmall.domain.MemberRegisterRequest;
import org.example.shoppingmall.domain.Role;
import org.example.shoppingmall.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
}
