package org.example.shoppingmall.controller;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.Service.InquiryReplyService;
import org.example.shoppingmall.domain.Member;
import org.example.shoppingmall.domain.dto.InquiryReplyRequestDTO;
import org.example.shoppingmall.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/inquiry/reply")
public class InquiryReplyController {

    private final InquiryReplyService inquiryReplyService;

    @PostMapping("/write")
    public String writeReply(@AuthenticationPrincipal CustomUserDetails user,
                             InquiryReplyRequestDTO dto) {

        Member member = user.getMember();
        inquiryReplyService.writeReply(member, dto);

        return "redirect:/inquiry/" + dto.getInquiryId();
    }

    @PostMapping("/update")
    public String updateReply(@AuthenticationPrincipal CustomUserDetails user,
                              InquiryReplyRequestDTO dto) {

        Member member = user.getMember();
        inquiryReplyService.updateReply(member, dto);

        return "redirect:/inquiry/" + dto.getInquiryId();
    }

    @PostMapping("/delete/{id}")
    public String deleteReply(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long id,
            @RequestParam Long inquiryId) {

        inquiryReplyService.deleteReply(user.getMember(), id);

        return "redirect:/inquiry/" + inquiryId;
    }
}
