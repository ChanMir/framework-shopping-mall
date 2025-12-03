package org.example.shoppingmall.controller;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.Service.OrderService;
import org.example.shoppingmall.Service.PaymentService;
import org.example.shoppingmall.domain.Member;
import org.example.shoppingmall.domain.Order;
import org.example.shoppingmall.domain.Payment;
import org.example.shoppingmall.domain.PaymentMethod;
import org.example.shoppingmall.security.SecurityUser;
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
            @AuthenticationPrincipal SecurityUser user
    ) {
        Member member = user.getMember();

        // 1) 주문 생성 + 배송 자동 생성
        Order order = orderService.createOrder(member, address);

        // 2) 결제 생성 + 주문 상태 SUCCESS 처리
        paymentService.createPayment(order.getId(), method);

        return "redirect:/order/" + order.getId();
    }


    @GetMapping("/payment/select")
    public String selectPayment(
            @RequestParam String address,
            @AuthenticationPrincipal SecurityUser user,
            Model model
    ) {
        model.addAttribute("address", address);
        model.addAttribute("member", user.getMember());
        return "order/PayCheck";
    }

}
