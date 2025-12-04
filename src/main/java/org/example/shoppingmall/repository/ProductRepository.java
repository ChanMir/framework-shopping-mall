package org.example.shoppingmall.repository;

import org.example.shoppingmall.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 카테고리별 상품 조회
    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByCategoryIdIn(List<Long> categoryIds);

}
