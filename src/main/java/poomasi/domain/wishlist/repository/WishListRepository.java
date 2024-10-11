package poomasi.domain.wishlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poomasi.domain.wishlist.entity.WishList;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList, Long> {
    List<WishList> findByMemberId(Long memberId);

    void deleteByMemberIdAndProductId(Long memberId, Long productId);
}
