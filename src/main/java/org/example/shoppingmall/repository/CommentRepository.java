package org.example.shoppingmall.repository;

import org.example.shoppingmall.domain.Comment;
import org.example.shoppingmall.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByReview(Review review);
}
