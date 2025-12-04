package org.example.shoppingmall.Service;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.domain.Comment;
import org.example.shoppingmall.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCommentService {

    private final CommentRepository commentRepository;

    public List<Comment> getCommentsByReview(Long reviewId) {
        return commentRepository.findByReviewIdOrderByCreatedAtDesc(reviewId);
    }

    public Comment getComment(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    public void disableComment(Long id) {
        Comment c = getComment(id);
        c.setActive(false);
        commentRepository.save(c);
    }

    public void enableComment(Long id) {
        Comment c = getComment(id);
        c.setActive(true);
        commentRepository.save(c);
    }
}
