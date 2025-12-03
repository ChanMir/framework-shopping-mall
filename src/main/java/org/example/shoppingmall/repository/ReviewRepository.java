package org.example.shoppingmall.repository;

import org.example.shoppingmall.domain.Review;
import org.example.shoppingmall.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProduct(Product product);
}
