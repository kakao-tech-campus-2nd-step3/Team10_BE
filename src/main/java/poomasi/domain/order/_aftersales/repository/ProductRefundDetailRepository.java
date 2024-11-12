package poomasi.domain.order._aftersales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poomasi.domain.order._aftersales.entity._product.ProductRefundDetail;

@Repository
public interface ProductRefundDetailRepository extends JpaRepository<ProductRefundDetail, Long> {
}
