package org.example.shoppingmall.Service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.shoppingmall.domain.Category;
import org.example.shoppingmall.domain.Product;
import org.example.shoppingmall.repository.CategoryRepository;
import org.example.shoppingmall.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }
    public void save(Product product) {
        productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }


    public List<Product> findByCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) return List.of();

        // 최종 검색 ID 목록
        List<Long> ids = new ArrayList<>();
        ids.add(categoryId);

        // 하위 카테고리 추가
        for (Category child : category.getChildren()) {
            ids.add(child.getId());
        }

        return productRepository.findByCategoryIdIn(ids);
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

}
