package org.example.shoppingmall.Service;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.domain.Member;
import org.example.shoppingmall.domain.Product;
import org.example.shoppingmall.domain.Review;
import org.example.shoppingmall.domain.dto.ReviewRequestDTO;
import org.example.shoppingmall.repository.ProductRepository;
import org.example.shoppingmall.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    // 리뷰 작성
    public void writeReview(Member member, ReviewRequestDTO dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        Review review = new Review();
        review.setMember(member);
        review.setProduct(product);
        review.setRating(dto.getRating());
        review.setContent(dto.getContent());

        reviewRepository.save(review);
    }

    // 리뷰 수정
    public Long updateReview(Member member, ReviewRequestDTO dto) {

        Review review = reviewRepository.findById(dto.getReviewId())
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));

        // 본인 리뷰인지 확인
        if (!review.getMember().getId().equals(member.getId())) {
            throw new IllegalStateException("자신의 리뷰만 수정할 수 있습니다.");
        }

        review.setRating(dto.getRating());
        review.setContent(dto.getContent());
        reviewRepository.save(review);

        return review.getProduct().getId();
    }

    // 리뷰 삭제
    public Long deleteReview(Member member, Long reviewId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));

        // 작성자 본인 확인
        if (!review.getMember().getId().equals(member.getId())) {
            throw new IllegalStateException("자신의 리뷰만 삭제할 수 있습니다.");
        }

        Long productId = review.getProduct().getId();

        reviewRepository.delete(review);

        return productId;
    }

    public List<Review> getReviews(Long productId) {
        Product p = productRepository.findById(productId)
                .orElseThrow();
        return reviewRepository.findByProduct(p);
    }
}
