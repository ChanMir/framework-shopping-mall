package org.example.shoppingmall.controller;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.Service.ReviewService;
import org.example.shoppingmall.domain.Member;
import org.example.shoppingmall.domain.dto.ReviewRequestDTO;
import org.example.shoppingmall.security.CustomUserDetails;
import org.example.shoppingmall.security.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    private Member getLoginMember() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return null; // 필요하면 여기서 예외 던져도 됨
        }

        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();


        return user.getMember();
    }

    // 리뷰 작성
    @PostMapping("/write")
    public String writeReview(@ModelAttribute ReviewRequestDTO dto) {
        Member member = getLoginMember();
        reviewService.writeReview(member, dto);
        return "redirect:/product/" + dto.getProductId();
    }

    // 리뷰 수정
    @PostMapping("/update")
    public String updateReview(@ModelAttribute ReviewRequestDTO dto) {
        Member member = getLoginMember();
        Long productId = reviewService.updateReview(member, dto);
        return "redirect:/product/" + productId;
    }

    // 리뷰 삭제
    @PostMapping("/delete/{reviewId}")
    public String deleteReview(@PathVariable Long reviewId) {
        Member member = getLoginMember();
        Long productId = reviewService.deleteReview(member, reviewId);
        return "redirect:/product/" + productId;
    }
}
