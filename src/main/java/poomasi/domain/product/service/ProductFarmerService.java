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

    public Long registerProduct(String token, ProductRegisterRequest product) {
        //token이 farmer인지 확인하기

        Product saveProduct = productRepository.save(product.toEntity());
        return saveProduct.getId();
    }

    public Long modifyProduct(String token, ProductRegisterRequest productRequest, Long productId) {
        //주인인지 알아보기
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(BusinessError.PRODUCT_NOT_FOUND));

        Product modifyProduct = productRepository.save(product.modify(productRequest));
        return modifyProduct.getId();
    }

    @Transactional
    public void deleteProduct(String token, Long productId) {
        //주인인지 알아보기
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(BusinessError.PRODUCT_NOT_FOUND));

        product.close();
        productRepository.delete(product);
    }

    @Transactional
    public void addQuantity(String token, Long productId, Integer quantity) {
        //주인인지 알아보기
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(BusinessError.PRODUCT_NOT_FOUND));
        product.addQuantity(quantity);
    }
}
