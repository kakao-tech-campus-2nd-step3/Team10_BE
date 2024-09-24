package poomasi.domain.product.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.product.entity.Product;
import poomasi.domain.product.repository.ProductRepository;

@Service
@AllArgsConstructor
public class ProductAdminService {
    private ProductRepository productRepository;

    @Transactional
    public void openProduct(Long productId) {
        productRepository.findById(productId).ifPresent(Product::open);
    }
}
