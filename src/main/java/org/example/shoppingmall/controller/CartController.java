package org.example.shoppingmall.controller;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.Service.CartService;
import org.example.shoppingmall.domain.Member;
import org.example.shoppingmall.security.CustomUserDetails;
import org.example.shoppingmall.security.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    private Member getLoginMember() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return null; // 필요하면 여기서 예외 던져도 됨
        }

        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();


        return user.getMember();
    }

    @GetMapping
    public String cartPage(Model model) {
        Member member = getLoginMember();
        if (member == null) {
            return "redirect:/member/login";
        }
        model.addAttribute("cartItems", cartService.getCartItems(member));
        return "cart/cart";
    }

    @GetMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId) {
        Member member = getLoginMember();
        if (member == null) {
            return "redirect:/member/login";
        }
        cartService.addToCart(member, productId);
        return "redirect:/cart";
    }

    @GetMapping("/remove/{cartId}")
    public String removeFromCart(@PathVariable Long cartId) {
        cartService.removeItem(cartId);
        return "redirect:/cart";
    }

    @GetMapping("/clear")
    public String clearCart() {
        Member member = getLoginMember();
        if (member != null) {
            cartService.clearCart(member);
        }
        return "redirect:/cart";
    }
}
