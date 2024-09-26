package poomasi.domain.product.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.product.entity.Product;
import poomasi.domain.product.repository.ProductRepository;
import poomasi.global.error.BusinessError;
import poomasi.global.error.BusinessException;

@Service
@AllArgsConstructor
public class ProductAdminService {
    private ProductRepository productRepository;
    //private MemberRepository memberRepository;

    @Transactional
    public void openProduct(Long productId) {
        getProductByProductId(productId).open();
    }

    private Product getProductByProductId(Long productId) {
        return productRepository.findById(productId).orElseThrow(()-> new BusinessException(BusinessError.PRODUCT_NOT_FOUND));
    }
}
