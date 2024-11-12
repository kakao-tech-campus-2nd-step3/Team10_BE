package poomasi.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poomasi.domain.order.entity._product.OrderedProduct;

import java.util.List;

public interface OrderedProductRepository extends JpaRepository<OrderedProduct, Long> {
}
