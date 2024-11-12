package poomasi.domain.farm.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import poomasi.domain.farm.dto.FarmRegisterRequest;
import poomasi.domain.farm.dto.FarmUpdateRequest;
import poomasi.domain.farm.entity.Farm;
import poomasi.domain.farm.repository.FarmRepository;
import poomasi.domain.member.entity.Member;
import poomasi.global.error.BusinessError;
import poomasi.global.error.BusinessException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FarmFarmerServiceTest {

    @InjectMocks
    private FarmFarmerService farmFarmerService;

    @Mock
    private FarmRepository farmRepository;

    @Nested
    @DisplayName("농장 등록")
    class RegisterFarm {
        @Test
        @DisplayName("농장이 이미 존재하는 경우 예외를 발생시킨다")
        void should_throwException_when_farmAlreadyExists() {
            // given
            Member member = Member.builder()
                    .id(1L)
                    .build();
            Farm existingFarm = Farm.builder()
                    .id(1L)
                    .name("Existing Farm")
                    .ownerId(1L)
                    .build();

            given(farmRepository.getFarmByOwnerIdAndDeletedAtIsNull(member.getId())).willReturn(Optional.of(existingFarm));

            FarmRegisterRequest request = new FarmRegisterRequest("New Farm", "Address", "Detail", 1.0, 1.0, "010-1234-5678", "Description", 10000, 10, 5, "1234-1234");

            // when & then
            assertThatThrownBy(() -> farmFarmerService.registerFarm(member, request))
                    .isInstanceOf(BusinessException.class)
                    .hasFieldOrPropertyWithValue("businessError", BusinessError.FARM_ALREADY_EXISTS);
        }
    }

    @Nested
    @DisplayName("농장 정보 업데이트")
    class UpdateFarm {
        @Test
        @DisplayName("농장이 존재하지 않는 경우 예외를 발생시킨다")
        void should_throwException_when_farmNotExist() {
            // given
            Long farmId = 1L;
            FarmUpdateRequest request = new FarmUpdateRequest(farmId, "Updated Farm", "Description", "Address", "Detail", 1.0, 1.0);
            given(farmRepository.findByIdAndDeletedAtIsNull(farmId)).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> farmFarmerService.updateFarm(1L, request))
                    .isInstanceOf(BusinessException.class)
                    .hasFieldOrPropertyWithValue("businessError", BusinessError.FARM_NOT_FOUND);
        }

        @Test
        @DisplayName("농장 소유자가 아닌 경우 예외를 발생시킨다")
        void should_throwException_when_ownerMismatch() {
            // given
            Long farmId = 1L;
            Long farmerId = 2L;
            Farm farm = Farm.builder()
                    .id(farmId)
                    .name("Farm")
                    .ownerId(3L)
                    .build();
            given(farmRepository.findByIdAndDeletedAtIsNull(farmId)).willReturn(Optional.of(farm));

            FarmUpdateRequest request = new FarmUpdateRequest(farmId, "Updated Farm", "Description", "Address", "Detail", 1.0, 1.0);

            // when & then
            assertThatThrownBy(() -> farmFarmerService.updateFarm(farmerId, request))
                    .isInstanceOf(BusinessException.class)
                    .hasFieldOrPropertyWithValue("businessError", BusinessError.FARM_OWNER_MISMATCH);
        }
    }

    @Nested
    @DisplayName("농장 삭제")
    class DeleteFarm {
        @Test
        @DisplayName("소유자가 아닌 경우 예외를 발생시킨다")
        void should_throwException_when_ownerMismatchOnDelete() {
            // given
            Long farmId = 1L;
            Long farmerId = 2L;
            Farm farm = Farm.builder()
                    .id(farmId)
                    .name("Farm")
                    .ownerId(3L)
                    .build();
            given(farmRepository.findByIdAndDeletedAtIsNull(farmId)).willReturn(Optional.of(farm));

            // when & then
            assertThatThrownBy(() -> farmFarmerService.deleteFarm(farmerId, farmId))
                    .isInstanceOf(BusinessException.class)
                    .hasFieldOrPropertyWithValue("businessError", BusinessError.FARM_OWNER_MISMATCH);
        }

        @Test
        @DisplayName("농장 삭제에 성공한다")
        void should_deleteFarm_when_ownerMatches() {
            // given
            Long farmId = 1L;
            Long farmerId = 1L;
            Farm farm = Farm.builder()
                    .id(farmId)
                    .name("Farm")
                    .ownerId(farmerId)
                    .build();
            given(farmRepository.findByIdAndDeletedAtIsNull(farmId)).willReturn(Optional.of(farm));

            // when
            farmFarmerService.deleteFarm(farmerId, farmId);

            // then
            verify(farmRepository).delete(farm);
        }

        @Test
        @DisplayName("농장이 존재하지 않는 경우 예외를 발생시킨다")
        void should_throwException_when_farmNotExistOnDelete() {
            // given
            Long farmId = 1L;
            Long farmerId = 1L;
            given(farmRepository.findByIdAndDeletedAtIsNull(farmId)).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> farmFarmerService.deleteFarm(farmerId, farmId))
                    .isInstanceOf(BusinessException.class)
                    .hasFieldOrPropertyWithValue("businessError", BusinessError.FARM_NOT_FOUND);
        }

        @Test
        @DisplayName("농장이 이미 삭제된 경우 예외를 발생시킨다")
        void should_throwException_when_farmAlreadyDeleted() {
            // given
            Long farmId = 1L;
            Long farmerId = 1L;
            Farm farm = Farm.builder()
                    .id(farmId)
                    .name("Farm")
                    .ownerId(farmerId)
                    .deletedAt(null)
                    .build();

            given(farmRepository.findByIdAndDeletedAtIsNull(farmId)).willReturn(Optional.of(farm));

            // when
            farmFarmerService.deleteFarm(farmerId, farmId);

            // then
            verify(farmRepository).delete(farm);
        }
    }
}
