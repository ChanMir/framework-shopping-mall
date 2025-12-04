package org.example.shoppingmall.controller;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.Service.InquiryReplyService;
import org.example.shoppingmall.Service.InquiryService;
import org.example.shoppingmall.domain.Inquiry;
import org.example.shoppingmall.domain.Member;
import org.example.shoppingmall.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/inquiry")
public class    InquiryController {

    private final InquiryService inquiryService;
    private final InquiryReplyService inquiryReplyService;

    /** ✔ 나의 문의 목록 */
    @GetMapping("")
    public String myInquiries(@AuthenticationPrincipal CustomUserDetails user, Model model) {

        if (user == null) {
            return "redirect:/member/login";
        }

        Member member = user.getMember();

        model.addAttribute(
                "inquiries", inquiryService.getMyInquiries(member)
        );

        return "inquiry/list";
    }

    /** ✔ 문의 작성 폼 */
    @GetMapping("/new")
    public String newInquiryForm(@AuthenticationPrincipal CustomUserDetails user) {
        if (user == null) {
            return "redirect:/member/login";
        }
        return "inquiry/new";
    }

    /** ✔ 문의 작성 처리 */
    @PostMapping("/new")
    public String submitInquiry(@AuthenticationPrincipal CustomUserDetails user,
                                @RequestParam String title,
                                @RequestParam String content) {

        if (user == null) {
            return "redirect:/member/login";
        }

        inquiryService.createInquiry(user.getMember(), title, content);
        return "redirect:/inquiry";
    }

    /** ✔ 문의 상세 페이지 */
    @GetMapping("/{id}")
    public String inquiryDetail(@PathVariable Long id,
                                @AuthenticationPrincipal CustomUserDetails user,
                                Model model) {

        if (user == null) {
            return "redirect:/member/login";
        }

        Inquiry inquiry = inquiryService.getInquiry(id);

        model.addAttribute("inquiry", inquiry);
        model.addAttribute("replies", inquiryReplyService.getReplies(id));  // 답글 있다면 출력용

        return "inquiry/detail";
    }
}
