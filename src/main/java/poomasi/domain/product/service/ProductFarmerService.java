package poomasi.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.category.entity.Category;
import poomasi.domain.category.repository.CategoryRepository;
import poomasi.domain.product.dto.ProductRegisterRequest;
import poomasi.domain.product.entity.Product;
import poomasi.domain.product.repository.ProductRepository;
import poomasi.global.error.BusinessError;
import poomasi.global.error.BusinessException;

@Service
@RequiredArgsConstructor
public class ProductFarmerService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Long registerProduct(ProductRegisterRequest productRequest) {
        //token이 farmer인지 확인하기
        Category category = getCategory(productRequest);
        Product saveProduct = productRepository.save(productRequest.toEntity(category));
        category.addProduct(saveProduct);

        System.out.println(category.getProducts().size());
        return saveProduct.getId();
    }

    private Category getCategory(ProductRegisterRequest product) {
        return categoryRepository.findById(product.categoryId())
                .orElseThrow(() -> new BusinessException(BusinessError.CATEGORY_NOT_FOUND));
    }

    @Transactional
    public void modifyProduct(ProductRegisterRequest productRequest, Long productId) {
        //주인인지 알아보기
        Category category = getCategory(productRequest);
        Product product = getProductByProductId(productId);

        product.getCategory().deleteProduct(product); //원래 카테고리에서 상품 삭제
        product = productRepository.save(product.modify(category, productRequest)); //상품 갱신
        category.addProduct(product);//새로운 카테고리에 추가
    }

    @Transactional
    public void deleteProduct(Long productId) {
        //주인인지 알아보기
        Product product = getProductByProductId(productId);
        Category category = product.getCategory();

        category.deleteProduct(product);
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
