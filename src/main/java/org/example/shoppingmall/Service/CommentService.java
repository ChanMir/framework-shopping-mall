package org.example.shoppingmall.Service;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.domain.*;
import org.example.shoppingmall.domain.dto.CommentRequestDTO;
import org.example.shoppingmall.repository.CommentRepository;
import org.example.shoppingmall.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;

    // 댓글 작성
    public Long writeComment(Member member, CommentRequestDTO dto) {

        Review review = reviewRepository.findById(dto.getReviewId())
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        Comment comment = new Comment();
        comment.setReview(review);
        comment.setMember(member);
        comment.setContent(dto.getContent());

        commentRepository.save(comment);

        return review.getProduct().getId();
    }

    // 댓글 수정
    public Long updateComment(Member member, CommentRequestDTO dto) {

        Comment comment = commentRepository.findById(dto.getCommentId())
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        if (!comment.getMember().getId().equals(member.getId())) {
            throw new IllegalStateException("본인 댓글만 수정할 수 있습니다.");
        }

        comment.setContent(dto.getContent());
        commentRepository.save(comment);

        return comment.getReview().getProduct().getId();
    }

    // 댓글 삭제
    public Long deleteComment(Member member, Long commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        if (!comment.getMember().getId().equals(member.getId())) {
            throw new IllegalStateException("본인 댓글만 삭제할 수 있습니다.");
        }

        Long productId = comment.getReview().getProduct().getId();
        commentRepository.delete(comment);

        return productId;
    }

    // 특정 리뷰의 댓글 목록
    public List<Comment> getComments(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow();
        return commentRepository.findByReview(review);
    }
}
