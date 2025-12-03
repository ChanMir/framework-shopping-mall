package org.example.shoppingmall.controller;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.Service.InquiryService;
import org.example.shoppingmall.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/inquiry")
public class InquiryController {

    private final InquiryService inquiryService;

    @PostMapping("/new")
    public String submitInquiry(@AuthenticationPrincipal CustomUserDetails user,
                                @RequestParam String title,
                                @RequestParam String content) {

        inquiryService.createInquiry(user.getMember(), title, content);
        return "redirect:/inquiry/list";
    }

    @GetMapping("/list")
    public String myInquiries(@AuthenticationPrincipal CustomUserDetails user, Model model) {
        model.addAttribute("inquiries", inquiryService.getMyInquiries(user.getMember()));
        return "inquiry/list";
    }
}
