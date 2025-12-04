package org.example.shoppingmall.controller;

import lombok.*;
import org.example.shoppingmall.Service.*;
import org.example.shoppingmall.domain.Member;
import org.example.shoppingmall.domain.MemberRegisterRequest;
import org.example.shoppingmall.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final ProductService productService;


    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("products", productService.findAll());
        return "mainpage";
    }

    @GetMapping("/member/login")
    public String login_page() {
        return "member/login";
    }

    @GetMapping("/member/register")
    public String register_page(Model model) {
        model.addAttribute("member", new MemberRegisterRequest());
        return "member/register";
    }


    @PostMapping("/member/register")
    public String register(
            @ModelAttribute("member") MemberRegisterRequest request,
            Model model
    ) {
        try {
            memberService.register(request);
            model.addAttribute("success", "회원가입이 완료되었습니다! 로그인해주세요.");
            return "member/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "member/register";
        }
    }
    // 아이디 찾기 화면
    @GetMapping("/member/find-id")
    public String findIdForm() {
        return "member/find-id";
    }

    // 아이디 찾기 처리
    @PostMapping("/member/find-id")
    public String findId(@RequestParam String phone, Model model) {

        String username = memberService.findIdByPhone(phone);

        if (username == null) {
            model.addAttribute("message", "해당 전화번호로 등록된 아이디가 없습니다.");
            return "member/find-id-result";
        }

        model.addAttribute("username", username);
        return "member/find-id-result";
    }

    // 비밀번호 찾기 화면
    @GetMapping("/member/find-password")
    public String findPasswordForm() {
        return "member/find-password";
    }

    // 비밀번호 찾기 처리
    @PostMapping("/member/find-password")
    public String findPassword(
            @RequestParam String username,
            @RequestParam String phone,
            Model model
    ) {
        String tempPw = memberService.resetPassword(username, phone);

        if (tempPw == null) {
            model.addAttribute("message", "입력한 정보와 일치하는 계정이 없습니다.");
            return "member/find-password-result";
        }

        model.addAttribute("tempPassword", tempPw);
        return "member/find-password-result";
    }
    @GetMapping("/member/mypage")
    public String myPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {

        Member loginMember = userDetails.getMember();
        model.addAttribute("member", loginMember);

        return "member/mypage";
    }

    @GetMapping("/member/mypage/edit")
    public String editPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {

        Member member = userDetails.getMember();
        model.addAttribute("member", member);

        return "member/mypage-edit";
    }

    @PostMapping("/member/mypage/update")
    public String updateMember(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam String username,
            @RequestParam String phone,
            @RequestParam String email
    ) {

        Member member = userDetails.getMember();
        memberService.updateMemberInfo(member, username, phone, email);

        return "redirect:/member/mypage";
    }

}
