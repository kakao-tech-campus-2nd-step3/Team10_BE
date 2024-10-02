package poomasi.domain.product.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poomasi.domain.product.repository.ProductRepository;
import poomasi.domain.product.service.ProductResponse;
import poomasi.global.error.BusinessError;
import poomasi.global.error.BusinessException;

import java.util.List;

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
        return productRepository.findByIdAndDeletedAtIsNull(productId)
                .map(ProductResponse::fromEntity)
                .orElseThrow(() -> new BusinessException(BusinessError.PRODUCT_NOT_FOUND));
    }
}
