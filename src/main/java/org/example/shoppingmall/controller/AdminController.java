package org.example.shoppingmall.controller;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.repository.MemberRepository;
import org.example.shoppingmall.repository.ProductRepository;
import org.example.shoppingmall.repository.OrderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        long memberCount = memberRepository.count();
        long productCount = productRepository.count();
        long orderCount = orderRepository.count();

        model.addAttribute("memberCount", memberCount);
        model.addAttribute("productCount", productCount);
        model.addAttribute("orderCount", orderCount);

        return "admin/dashboard";
    }
}
