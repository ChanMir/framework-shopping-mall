package org.example.shoppingmall.controller;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.Service.DeliveryService;
import org.example.shoppingmall.Service.OrderService;
import org.example.shoppingmall.Service.PaymentService;
import org.example.shoppingmall.domain.Delivery;
import org.example.shoppingmall.domain.Member;
import org.example.shoppingmall.domain.Order;
import org.example.shoppingmall.domain.Payment;
import org.example.shoppingmall.security.CustomUserDetails;
import org.example.shoppingmall.security.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final DeliveryService deliveryService;
    private final PaymentService paymentService;

    private Member getLoginMember() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return null; // 필요하면 여기서 예외 던져도 됨
        }

        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();


        return user.getMember();
    }


    // 주문 목록
    @GetMapping
    public String list(Model model) {
        model.addAttribute("orders", orderService.getOrders(getLoginMember()));
        return "order/list";
    }

    @GetMapping("{id}")
    public String orderDetail(
            @PathVariable Long id,
            Model model
    ) {
        Order order = orderService.findById(id);
        Delivery delivery = deliveryService.findByOrderId(id);
        Payment payment = paymentService.getPaymentByOrderId(id);

        model.addAttribute("order", order);
        model.addAttribute("delivery", delivery);
        model.addAttribute("payment", payment);

        return "order/detail";
    }


    // 주문 정보 입력 페이지
    @GetMapping("/checkout")
    public String checkoutPage() {
        return "order/checkout";
    }

    @PostMapping("/checkout")
    public String processCheckout(
            @RequestParam String address,
            @AuthenticationPrincipal SecurityUser user
    ) {
        // 주소값을 결제 페이지로 넘김
        return "redirect:/payment/select?address=" + address;
    }

    @PostMapping("{id}/cancel")
    public String cancelOrder(@PathVariable Long id,
                              @AuthenticationPrincipal SecurityUser user,
                              RedirectAttributes redirectAttributes) {

        try {
            orderService.cancelOrder(id);
            redirectAttributes.addFlashAttribute("success", "주문이 취소되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/order/" + id;
    }


}
