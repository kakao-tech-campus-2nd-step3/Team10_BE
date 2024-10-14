package poomasi.domain.product._cart.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.auth.security.userdetail.UserDetailsImpl;
import poomasi.domain.member.entity.Member;
import poomasi.domain.product._cart.dto.CartRegisterRequest;
import poomasi.domain.product._cart.dto.CartRequest;
import poomasi.domain.product._cart.dto.CartResponse;
import poomasi.domain.product._cart.entity.Cart;
import poomasi.domain.product._cart.repository.CartRepository;
import poomasi.domain.product.entity.Product;
import poomasi.domain.product.repository.ProductRepository;
import poomasi.global.error.BusinessError;
import poomasi.global.error.BusinessException;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Long addCart(CartRegisterRequest cartRequest) {
        Member member = getMember();
        Product product = getProductById(cartRequest.productId());

        Optional<Cart> cartOptional = cartRepository.findByMemberIdAndProductId(member.getId(),
                product.getId());
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            return cart.getId();
        }

        Cart cart = cartRequest.toEntity(member);
        if (product.getStock() < cart.getCount()) {
            throw new BusinessException(BusinessError.PRODUCT_STOCK_ZERO);
        }
        cart = cartRepository.save(cart);
        return cart.getId();

    }

    @Transactional
    public void deleteCart(CartRequest cartRequest) {
        Member member = getMember();
        Cart cart = getCartById(cartRequest.cartId());
        checkAuth(member, cart);
        cartRepository.delete(cart);
    }

    private void checkAuth(Member member, Cart cart) {
        if (!member.getId().equals(cart.getMemberId())) {
            throw new BusinessException(BusinessError.MEMBER_ID_MISMATCH);
        }
    }

    @Transactional
    public void addCount(CartRequest cartRequest) {
        Member member = getMember();
        Cart cart = getCartById(cartRequest.cartId());
        Product product = getProductById(cart.getProductId());
        if (product.getStock().equals(cart.getCount())) {
            throw new BusinessException(BusinessError.PRODUCT_STOCK_ZERO);
        }
        checkAuth(member, cart);
        cart.addCount();
    }

    @Transactional
    public void subCount(CartRequest cartRequest) {
        Member member = getMember();
        Cart cart = getCartById(cartRequest.cartId());
        checkAuth(member, cart);
        cart.subCount();
    }

    private Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new BusinessException(BusinessError.CART_NOT_FOUND));
    }

    private Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(BusinessError.PRODUCT_NOT_FOUND));
    }

    public List<CartResponse> getCart() {
        Member member = getMember();
        return cartRepository.findByMemberId(member.getId());
    }

    private Member getMember() {
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        Object impl = authentication.getPrincipal();
        Member member = ((UserDetailsImpl) impl).getMember();
        return member;
    }

    @Transactional
    public void changeSelect(CartRequest cartRequest) {
        Member member = getMember();
        Cart cart = getCartById(cartRequest.cartId());
        checkAuth(member, cart);
        cart.changeSelect();
    }

    public Integer getPrice() {
        Member member = getMember();
        return cartRepository.getPrice(member.getId());
    }

    @Transactional
    public void deleteAll() {
        Member member = getMember();
        cartRepository.deleteAllByMemberId(member.getId());
    }

    @Transactional
    public void removeSelected() {
        Member member = getMember();
        cartRepository.deleteByMemberIdAndSelected(member.getId());
    }
}
