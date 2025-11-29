package org.example.shoppingmall.controller;

import lombok.*;
import org.example.shoppingmall.Service.*;
import org.example.shoppingmall.domain.Member;
import org.example.shoppingmall.domain.MemberRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
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

}
