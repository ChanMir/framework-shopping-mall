package org.example.shoppingmall.controller.admin;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.Service.AdminInquiryService;
import org.example.shoppingmall.Service.InquiryReplyService;
import org.example.shoppingmall.Service.InquiryService;
import org.example.shoppingmall.domain.Inquiry;
import org.example.shoppingmall.domain.InquiryStatus;
import org.example.shoppingmall.repository.InquiryRepository;
import org.example.shoppingmall.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/inquiry")
public class AdminInquiryController {

    private final AdminInquiryService adminInquiryService;
    private final InquiryService inquiryService;
    private final InquiryReplyService replyService;

    /** 관리자 문의 목록 */
    @GetMapping("")
    public String list(Model model) {

        model.addAttribute("inquiries",
                adminInquiryService.getAllInquiries());

        return "admin/inquiry-list";
    }

    /** 상세 */
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {

        Inquiry inquiry = adminInquiryService.getInquiry(id);

        model.addAttribute("inquiry", inquiry);
        model.addAttribute("replies", replyService.getReplies(id));

        return "admin/inquiry-detail";
    }

    /** 답변 등록 */
    @PostMapping("/{id}/reply")
    public String reply(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails admin,
            @RequestParam String content
    ) {
        Inquiry inquiry = adminInquiryService.getInquiry(id);
        adminInquiryService.addReply(inquiry, admin.getMember(), content);

        return "redirect:/admin/inquiry/" + id;
    }

    /** 상태 변경 */
    @PostMapping("/{id}/status")
    public String updateStatus(
            @PathVariable Long id,
            @RequestParam InquiryStatus status
    ) {
        adminInquiryService.updateStatus(id, status);
        return "redirect:/admin/inquiry/" + id;
    }
}
