package org.example.shoppingmall.Service;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.domain.Cart;
import org.example.shoppingmall.domain.Member;
import org.example.shoppingmall.domain.Product;
import org.example.shoppingmall.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;

    // 장바구니 목록 조회
    public List<Cart> getCartItems(Member member) {
        return cartRepository.findByMember(member);
    }

    // 장바구니 추가
    public void addToCart(Member member, Long productId) {
        Product product = productService.findById(productId);

        // 이미 담겨있으면 수량+1
        Cart cart = cartRepository.findByMemberAndProductId(member, productId)
                .orElse(null);

        if (cart != null) {
            cart.setQuantity(cart.getQuantity() + 1);
            cartRepository.save(cart);
            return;
        }

        // 새로운 상품 추가
        Cart newCart = new Cart();
        newCart.setMember(member);
        newCart.setProduct(product);
        newCart.setQuantity(1);

        cartRepository.save(newCart);
    }

    // 장바구니에서 삭제
    public void removeItem(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    // 장바구니 전체 삭제
    public void clearCart(Member member) {
        List<Cart> items = cartRepository.findByMember(member);
        cartRepository.deleteAll(items);
    }
}
