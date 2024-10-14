package poomasi.domain.product.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poomasi.domain.product.dto.ProductResponse;
import poomasi.domain.product.entity.Product;
import poomasi.domain.product.repository.ProductRepository;
import poomasi.global.error.BusinessError;
import poomasi.global.error.BusinessException;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAllByDeletedAtIsNull()
                .stream()
                .map(ProductResponse::fromEntity)
                .toList();
    }

    public ProductResponse getProductByProductId(Long productId) {
        return ProductResponse.fromEntity(findProductById(productId));
    }


    public Product findValidProductById(Long productId) {
        Product product = findProductById(productId);
        if (product.getStock() == 0) {
            throw new BusinessException(BusinessError.PRODUCT_STOCK_ZERO);
        }
        return product;
    }

    public Product findProductById(Long productId) {
        return productRepository.findByIdAndDeletedAtIsNull(productId)
                .orElseThrow(() -> new BusinessException(BusinessError.PRODUCT_NOT_FOUND));
    }
}
