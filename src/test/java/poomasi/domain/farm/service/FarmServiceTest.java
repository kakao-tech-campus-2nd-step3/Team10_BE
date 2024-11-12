package poomasi.domain.farm.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import poomasi.domain.farm.FarmTestHelper;
import poomasi.domain.farm.entity.Farm;
import poomasi.domain.farm.repository.FarmRepository;
import poomasi.global.error.BusinessError;
import poomasi.global.error.BusinessException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FarmServiceTest {
    @InjectMocks
    private FarmService farmService;

    @Mock
    private FarmRepository farmRepository;

    @Nested
    @DisplayName("ID로 농장 가져오기")
    class getFarmByFarmId {
        @Test
        @DisplayName("농장이 존재하는 경우 농장 가져오기에 성공한다")
        void should_successGetFarm_when_farmExist() {
            // given
            Farm farm = FarmTestHelper.makeRandomFarm();
            given(farmRepository.findByIdAndDeletedAtIsNull(farm.getId())).willReturn(Optional.of(farm));

            // when
            Farm result = farmService.getFarmByFarmId(farm.getId());

            // then
            assertThat(result.getId()).isEqualTo(farm.getId());
            assertThat(result.getName()).isEqualTo(farm.getName());
            assertThat(result.getStatus()).isEqualTo(farm.getStatus());
        }

        @Test
        @DisplayName("농장이 존재하지 않는 경우 예외를 발생시킨다")
        void should_throwException_when_farmNotExist() {
            // given
            Long farmId = 1L;
            given(farmRepository.findByIdAndDeletedAtIsNull(farmId)).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> farmService.getFarmByFarmId(farmId))
                    .isInstanceOf(BusinessException.class)
                    .hasFieldOrPropertyWithValue("businessError", BusinessError.FARM_NOT_FOUND);
        }

        @Test
        @DisplayName("농장이 삭제된 경우 예외를 발생시킨다")
        void should_throwException_when_farmIsDeleted() {
            // given
            Farm farm = FarmTestHelper.makeRandomFarm();
            farm.delete();

            // when & then
            assertThatThrownBy(() -> farmService.getFarmByFarmId(farm.getId()))
                    .isInstanceOf(BusinessException.class)
                    .hasFieldOrPropertyWithValue("businessError", BusinessError.FARM_NOT_FOUND);
        }
    }

}
