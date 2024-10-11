package poomasi.domain.wishlist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.member.entity.Member;
import poomasi.domain.member.service.MemberService;
import poomasi.domain.product.entity.Product;
import poomasi.domain.product.service.ProductService;
import poomasi.domain.wishlist.dto.request.WishListAddRequest;
import poomasi.domain.wishlist.repository.WishListRepository;

@Service
@RequiredArgsConstructor
public class WishListService {
    private final WishListRepository wishListRepository;
    private final MemberService memberService;
    private final ProductService productService;

    @Transactional
    public void addWishList(WishListAddRequest request) {
        Member member = memberService.findMemberById(request.memberId());
        Product product = productService.findProductById(request.productId());
        wishListRepository.save(request.toEntity(member, product));
    }

    public void deleteByMemberIdAndProductId(Long memberId, Long productId) {
        wishListRepository.deleteByMemberIdAndProductId(memberId, productId);
    }


}
