package org.example.shoppingmall.controller;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.Service.CommentService;
import org.example.shoppingmall.domain.Member;
import org.example.shoppingmall.domain.dto.CommentRequestDTO;
import org.example.shoppingmall.security.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    private Member getLoginMember() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ((SecurityUser) auth.getPrincipal()).getMember();
    }

    // 댓글 작성
    @PostMapping("/write")
    public String writeComment(@ModelAttribute CommentRequestDTO dto) {
        Member member = getLoginMember();
        Long productId = commentService.writeComment(member, dto);
        return "redirect:/product/" + productId;
    }

    // 댓글 수정
    @PostMapping("/update")
    public String updateComment(@ModelAttribute CommentRequestDTO dto) {
        Member member = getLoginMember();
        Long productId = commentService.updateComment(member, dto);
        return "redirect:/product/" + productId;
    }

    // 댓글 삭제
    @PostMapping("/delete/{id}")
    public String deleteComment(@PathVariable Long id) {
        Member member = getLoginMember();
        Long productId = commentService.deleteComment(member, id);
        return "redirect:/product/" + productId;
    }
}
