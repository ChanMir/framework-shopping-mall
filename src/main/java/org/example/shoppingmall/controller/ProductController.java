package org.example.shoppingmall.controller;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.Service.CommentService;
import org.example.shoppingmall.Service.ProductService;
import org.example.shoppingmall.Service.ReviewService;
import org.example.shoppingmall.domain.Comment;
import org.example.shoppingmall.domain.Product;
import org.example.shoppingmall.domain.Review;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ReviewService reviewService;
    private final CommentService commentService;

    // 상품 리스트 페이지
    @GetMapping
    public String productList(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product/list";
    }

    // 상품 상세
    @GetMapping("{id}")
    public String productDetail(@PathVariable Long id, Model model) {

        Product product = productService.findById(id);
        List<Review> reviews = reviewService.getReviews(id);

        // 각 리뷰별 댓글도 불러와야 함
        Map<Long, List<Comment>> commentMap = new HashMap<>();
        for (Review r : reviews) {
            List<Comment> comments = commentService.getComments(r.getId());
            commentMap.put(r.getId(), comments);
        }

        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);
        model.addAttribute("commentMap", commentMap);

        return "product/detail";
    }

}
