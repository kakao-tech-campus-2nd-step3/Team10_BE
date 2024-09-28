package poomasi.domain.product.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.product.dto.ProductRegisterRequest;
import poomasi.domain.product.entity.Product;
import poomasi.domain.product.repository.ProductRepository;
import poomasi.global.error.BusinessError;
import poomasi.global.error.BusinessException;

@Service
@AllArgsConstructor
public class ProductFarmerService {

    private final ProductRepository productRepository;
    //private final MemberRepository memberRepository;

    public Long registerProduct(ProductRegisterRequest product) {
        //token이 farmer인지 확인하기

        Product saveProduct = productRepository.save(product.toEntity());
        return saveProduct.getId();
    }

    public void modifyProduct(ProductRegisterRequest productRequest, Long productId) {
        //주인인지 알아보기
        Product product = getProductByProductId(productId);
        productRepository.save(product.modify(productRequest));
    }

    @Transactional
    public void deleteProduct(Long productId) {
        //주인인지 알아보기
        Product product = getProductByProductId(productId);
        productRepository.delete(product);
    }

    @Transactional
    public void addQuantity(Long productId, Integer quantity) {
        //주인인지 알아보기
        getProductByProductId(productId).addQuantity(quantity);
    }

    private Product getProductByProductId(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(BusinessError.PRODUCT_NOT_FOUND));
    }
}
