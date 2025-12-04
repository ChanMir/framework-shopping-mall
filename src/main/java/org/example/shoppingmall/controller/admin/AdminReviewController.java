package org.example.shoppingmall.controller.admin;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.Service.AdminCommentService;
import org.example.shoppingmall.Service.AdminReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/reviews")
@RequiredArgsConstructor
public class AdminReviewController {

    private final AdminReviewService adminReviewService;
    private final AdminCommentService adminCommentService;

    @GetMapping("")
    public String reviewList(Model model) {
        model.addAttribute("reviews", adminReviewService.getAllReviews());
        return "admin/review-list";
    }

    @GetMapping("/{id}")
    public String reviewDetail(@PathVariable Long id, Model model) {
        model.addAttribute("review", adminReviewService.getReview(id));
        model.addAttribute("comments", adminCommentService.getCommentsByReview(id));
        return "admin/review-detail";
    }

    // 🔥 리뷰 삭제
    @PostMapping("/{id}/delete")
    public String deleteReview(@PathVariable Long id) {
        adminReviewService.deleteReview(id);
        return "redirect:/admin/reviews";
    }

    // 🔥 리뷰 비활성화
    @PostMapping("/{id}/disable")
    public String disableReview(@PathVariable Long id) {
        adminReviewService.deactivateReview(id);
        return "redirect:/admin/reviews/" + id;
    }

    // 🔥 리뷰 활성화
    @PostMapping("/{id}/enable")
    public String enableReview(@PathVariable Long id) {
        adminReviewService.activateReview(id);
        return "redirect:/admin/reviews/" + id;
    }

    // 🔥 댓글 삭제
    @PostMapping("/{reviewId}/comments/{commentId}/delete")
    public String deleteComment(@PathVariable Long reviewId, @PathVariable Long commentId) {
        adminCommentService.deleteComment(commentId);
        return "redirect:/admin/reviews/" + reviewId;
    }

    // 🔥 댓글 비활성화
    @PostMapping("/{reviewId}/comments/{commentId}/disable")
    public String disableComment(@PathVariable Long reviewId, @PathVariable Long commentId) {
        adminCommentService.disableComment(commentId);
        return "redirect:/admin/reviews/" + reviewId;
    }

    // 🔥 댓글 활성화
    @PostMapping("/{reviewId}/comments/{commentId}/enable")
    public String enableComment(@PathVariable Long reviewId, @PathVariable Long commentId) {
        adminCommentService.enableComment(commentId);
        return "redirect:/admin/reviews/" + reviewId;
    }
}
