package org.example.shoppingmall.controller;

import org.example.shoppingmall.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShoppingMallController {

    @GetMapping("/")
    public String main_page() {
        return "mainpage";
    }
    @GetMapping("/login")
    public String login_page() {
        return "login";
    }
    @GetMapping("/register")
    public String register_page() {
        return "register";
    }
    @GetMapping("/product")
    public String product_page() {
        return "product";
    }

    // 서비스
    @Autowired
    private MemberService memberService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;

    // 컨트롤러
}
