package org.example.shoppingmall.Service;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.domain.Review;
import org.example.shoppingmall.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminReviewService {

    private final ReviewRepository reviewRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.findAllByOrderByCreatedAtDesc();
    }

    public Review getReview(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없음"));
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    public void deactivateReview(Long id) {
        Review review = getReview(id);
        review.setActive(false);
        reviewRepository.save(review);
    }

    public void activateReview(Long id) {
        Review review = getReview(id);
        review.setActive(true);
        reviewRepository.save(review);
    }
}
