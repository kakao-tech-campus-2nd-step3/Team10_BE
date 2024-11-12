package poomasi.domain.image.linker;

import org.springframework.stereotype.Service;
import poomasi.domain.image.entity.Image;
import poomasi.domain.image.entity.ImageType;
import poomasi.domain.product.entity.Product;
import poomasi.domain.product.service.ProductService;

@Service
public class ProductImageLinker implements ImageLinker {

    private final ProductService productService;

    public ProductImageLinker(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public boolean supports(ImageType type) {
        return type == ImageType.PRODUCT;
    }

    @Override
    public void link(Long referenceId, Image savedImage) {
        Product product = productService.findProductById(referenceId);
        product.setImageUrl(savedImage.getImageUrl());
        productService.saveExistedProduct(product);
    }
}