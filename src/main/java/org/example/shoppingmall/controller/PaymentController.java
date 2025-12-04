package org.example.shoppingmall.controller;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.Service.OrderService;
import org.example.shoppingmall.Service.PaymentService;
import org.example.shoppingmall.domain.Member;
import org.example.shoppingmall.domain.Order;
import org.example.shoppingmall.domain.PaymentMethod;
import org.example.shoppingmall.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final OrderService orderService;
    private final PaymentService paymentService;

    // 결제 페이지
    @GetMapping("/payment/checkout/{orderId}")
    public String checkout(@PathVariable Long orderId, Model model) {
        model.addAttribute("order", orderService.findById(orderId));
        return "payment/checkout";
    }

    @PostMapping("/payment/complete")
    public String completePayment(
            @RequestParam String address,
            @RequestParam PaymentMethod method,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        if (user == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        Member member = user.getMember();

        // 1) 주문 생성 + 배송 자동 생성
        Order order = orderService.createOrder(member, address);

        // 2) 결제 저장 및 상태 변경
        paymentService.createPayment(order.getId(), method);

        return "redirect:/order/" + order.getId();
    }

    @GetMapping("/payment/select")
    public String selectPayment(
            @RequestParam String address,
            @AuthenticationPrincipal CustomUserDetails user,
            Model model
    ) {
        if (user == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        model.addAttribute("address", address);
        model.addAttribute("member", user.getMember());
        return "order/PayCheck";
    }

}
