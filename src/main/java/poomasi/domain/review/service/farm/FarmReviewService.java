package poomasi.domain.review.service.farm;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.farm.entity.Farm;
import poomasi.domain.farm.repository.FarmRepository;
import poomasi.domain.review.dto.ReviewRequest;
import poomasi.domain.review.dto.ReviewResponse;
import poomasi.domain.review.entity.EntityType;
import poomasi.domain.review.entity.Review;
import poomasi.domain.review.repository.ReviewRepository;
import poomasi.global.error.BusinessError;
import poomasi.global.error.BusinessException;

@Service
@RequiredArgsConstructor
public class FarmReviewService {

    private final ReviewRepository reviewRepository;
    private final FarmRepository farmRepository;

    public List<ReviewResponse> getFarmReview(Long farmId) {
        getFarmByFarmId(farmId); //상품이 존재하는지 체크

        return reviewRepository.findByFarmId(farmId).stream()
                .map(ReviewResponse::fromEntity).toList();
    }

    @Transactional
    public Long registerFarmReview(Long entityId, ReviewRequest reviewRequest) {
        // s3 이미지 저장하고 주소 받아와서 review에 추가해주기

        Review pReview = reviewRequest.toEntity(entityId);
        pReview.setReviewType(EntityType.FARM);
        pReview = reviewRepository.save(pReview);

        return pReview.getId();
    }

    private Farm getFarmByFarmId(Long farmId) {
        return farmRepository.findById(farmId)
                .orElseThrow(() -> new BusinessException(BusinessError.FARM_NOT_FOUND));
    }
}
