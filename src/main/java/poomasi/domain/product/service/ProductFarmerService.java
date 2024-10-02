package poomasi.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.member.service.MemberService;
import poomasi.domain.product._category.service.CategoryService;
import poomasi.domain.product.dto.ProductRegisterRequest;
import poomasi.domain.product.dto.ProductResponse;
import poomasi.domain.product.dto.UpdateProductQuantityRequest;
import poomasi.domain.product.entity.Product;
import poomasi.domain.product.repository.ProductRepository;
import poomasi.global.error.BusinessError;
import poomasi.global.error.BusinessException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductFarmerService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final MemberService memberService;

    public Long registerProduct(ProductRegisterRequest request) {
        memberService.isFarmer(request.farmerId());
        categoryService.getCategory(request.categoryId());

        Product saveProduct = productRepository.save(request.toEntity());
        return saveProduct.getId();
    }

    public void modifyProduct(ProductRegisterRequest productRequest, Long productId) {
        // TODO: 주인인지 알아보기
        Product product = getProductByProductId(productId);
        productRepository.save(product.modify(productRequest));
    }

    @Transactional
    public void deleteProduct(Long productId) {
        //TODO: 주인인지 알아보기
        Product product = getProductByProductId(productId);
        productRepository.delete(product);
    }

    @Transactional
    public void addQuantity(Long productId, UpdateProductQuantityRequest request) {
        //TODO: 주인인지 혹은 관리자인지 알아보기
        getProductByProductId(productId).addQuantity(request.quantity());
    }

    private Product getProductByProductId(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(BusinessError.PRODUCT_NOT_FOUND));
    }

    @Service
    @RequiredArgsConstructor
    public static class ProductService {
        private final ProductRepository productRepository;


        public List<poomasi.domain.product.dto.ProductResponse.ProductResponse> getAllProducts() {
            return productRepository.findAllByDeletedAtIsNull()
                    .stream()
                    .map(poomasi.domain.product.dto.ProductResponse.ProductResponse::fromEntity)
                    .toList();
        }

        public poomasi.domain.product.dto.ProductResponse.ProductResponse getProductByProductId(Long productId) {
            return productRepository.findByIdAndDeletedAtIsNull(productId)
                    .map(ProductResponse.ProductResponse::fromEntity)
                    .orElseThrow(() -> new BusinessException(BusinessError.PRODUCT_NOT_FOUND));
        }
    }
}
