package poomasi.domain.image.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import poomasi.domain.product.repository.ProductRepository;

@Component
@RequiredArgsConstructor
public class ProductOwnerValidator implements ImageOwnerValidator{
    private final ProductRepository productRepository;

    @Override
    public boolean validateOwner(Long memberId, Long referenceId) {
        return productRepository.findById(referenceId)
                .filter(product -> product.getFarmerId().equals(memberId))
                .isPresent();
    }
}

