package org.example.shoppingmall.repository;

import org.example.shoppingmall.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository <Product, Long>{

}
