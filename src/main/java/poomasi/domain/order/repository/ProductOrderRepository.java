package poomasi.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poomasi.domain.order.entity._product.ProductOrder;

import java.util.List;
import java.util.Optional;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
    List<ProductOrder> findByMemberId(Long memberId);
    Optional<ProductOrder> findByMerchantUid(String merchantUid);
    //Optional<ProductOrder> findByImpUid(String impUid);
    //Optional<ProductOrder> findByMerchantUidAndImpUid(String merchantUid, String impUid);
}
