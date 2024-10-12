package poomasi.domain.product._cart.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.product._cart.dto.CartResponse;
import poomasi.domain.product._cart.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT new poomasi.domain.product._cart.dto.CartResponse(p.name, p.price, c.count, c.selected,f.name) " +
            "FROM Cart c " +
            "INNER JOIN Product p ON c.productId = p.id " +
            "INNER JOIN Farm f ON f.ownerId = :memberId")
    List<CartResponse> findByMemberId(Long memberId);

    @Query("select p.price * c.count from Cart c inner join Product p on c.productId = p.id where c.memberId = :memberId and c.selected = true")
    Integer getPrice(Long memberId);

    @Query("select c from Cart c where c.memberId = :memberId and c.productId = :productId")
    Optional<Cart>findByMemberIdAndProductId(Long memberId, Long productId);

    @Modifying
    @Transactional
    @Query("delete from Cart c where c.memberId = :memberId")
    void deleteAllByMemberId(Long memberId);

    @Modifying
    @Transactional
    @Query("delete from Cart c where c.memberId = :memberId and c.selected = true")
    void deleteByMemberIdAndSelected(Long id);
}
