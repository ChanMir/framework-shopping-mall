package org.example.shoppingmall.repository;

import org.example.shoppingmall.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {}
