package poomasi.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poomasi.domain.product.entity.Product;
import poomasi.domain.product.repository.ProductRepository;
import poomasi.global.error.BusinessError;
import poomasi.global.error.BusinessException;

@Service
@RequiredArgsConstructor
public class ProductAdminService {
    private final ProductRepository productRepository;

    private Product getProductByProductId(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(BusinessError.PRODUCT_NOT_FOUND));
    }

    public void openProduct(Long productId) {
    }
}
