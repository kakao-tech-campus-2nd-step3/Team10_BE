package poomasi.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.member.service.MemberService;
import poomasi.domain.product._category.entity.Category;
import poomasi.domain.product._category.service.CategoryService;
import poomasi.domain.product.dto.ProductRegisterRequest;
import poomasi.domain.product.dto.UpdateProductQuantityRequest;
import poomasi.domain.product.entity.Product;
import poomasi.domain.product.repository.ProductRepository;
import poomasi.global.error.BusinessError;
import poomasi.global.error.BusinessException;

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


    @Transactional
    public void modifyProduct(ProductRegisterRequest productRequest, Long productId) {
        // TODO: 주인인지 알아보기
        Product product = getProductByProductId(productId);


        // FIXME: 이거 수정해야할듯?
    /*    product.getCategory().deleteProduct(product); //원래 카테고리에서 상품 삭제
        product = productRepository.save(product.modify(category, productRequest)); //상품 갱신
        category.addProduct(product);//새로운 카테고리에 추가*/
    }

    @Transactional
    public void deleteProduct(Long productId) {
        //TODO: 주인인지 알아보기
        Product product = getProductByProductId(productId);

        // FIXME: 이거 수정해야할듯?
    /*    Category category = product.getCategory();

        category.deleteProduct(product);*/
        productRepository.delete(product);
    }

    @Transactional
    public void addQuantity(Long productId, UpdateProductQuantityRequest request) {
        Product productByProductId = getProductByProductId(productId);
        productByProductId.addQuantity(request.quantity());

        productRepository.save(productByProductId);
    }

    private Product getProductByProductId(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(BusinessError.PRODUCT_NOT_FOUND));
    }
}
