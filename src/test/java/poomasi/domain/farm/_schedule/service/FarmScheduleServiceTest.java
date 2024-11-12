package poomasi.domain.farm._schedule.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import poomasi.domain.farm._schedule.dto.FarmScheduleRequest;
import poomasi.domain.farm._schedule.dto.FarmScheduleResponse;
import poomasi.domain.farm._schedule.dto.FarmScheduleUpdateRequest;
import poomasi.domain.farm._schedule.entity.FarmSchedule;
import poomasi.domain.farm._schedule.repository.FarmScheduleRepository;
import poomasi.global.error.BusinessException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static poomasi.global.error.BusinessError.*;

@ExtendWith(MockitoExtension.class)
class FarmScheduleServiceTest {
    @InjectMocks
    private FarmScheduleService farmScheduleService;

    @Mock
    private FarmScheduleRepository farmScheduleRepository;

    @Nested
    @DisplayName("농장 스케줄 추가")
    class AddFarmSchedule {
        @Test
        @DisplayName("정상적으로 스케줄을 추가한다")
        void should_addFarmSchedule() {
            // given
            FarmScheduleUpdateRequest request = new FarmScheduleUpdateRequest(1L, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(12, 0));

            given(farmScheduleRepository.findByFarmIdAndDate(1L, LocalDate.now())).willReturn(List.of());

            // when
            farmScheduleService.addFarmSchedule(request);

            // then
            FarmSchedule farmSchedule = FarmSchedule.builder()
                    .farmId(1L)
                    .date(LocalDate.now())
                    .startTime(LocalTime.of(10, 0))
                    .endTime(LocalTime.of(12, 0))
                    .build();
            assertAll(
                    () -> assertEquals(1L, farmSchedule.getFarmId()),
                    () -> assertEquals(LocalDate.now(), farmSchedule.getDate()),
                    () -> assertEquals(LocalTime.of(10, 0), farmSchedule.getStartTime()),
                    () -> assertEquals(LocalTime.of(12, 0), farmSchedule.getEndTime())
            );
        }

        @Test
        @DisplayName("시작 시간이 종료 시간보다 늦은 경우 예외를 발생시킨다")
        void should_throwException_when_startTimeIsAfterEndTime() {
            // given
            FarmScheduleUpdateRequest request = new FarmScheduleUpdateRequest(1L, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(9, 0));

            // when & then
            BusinessException exception = assertThrows(BusinessException.class, () -> farmScheduleService.addFarmSchedule(request));
            assertEquals(START_TIME_SHOULD_BE_BEFORE_END_TIME, exception.getBusinessError());
        }

        @Test
        @DisplayName("이미 등록된 스케줄이 있는 경우 예외를 발생시킨다")
        void should_throwException_when_scheduleAlreadyExists() {
            // given
            FarmSchedule farmSchedule = FarmSchedule.builder()
                    .startTime(LocalTime.of(10, 0))
                    .endTime(LocalTime.of(12, 0))
                    .build();
            List<FarmSchedule> farmSchedules = List.of(farmSchedule);

            given(farmScheduleRepository.findByFarmIdAndDate(1L, LocalDate.now())).willReturn(farmSchedules);

            FarmScheduleUpdateRequest request = new FarmScheduleUpdateRequest(1L, LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(13, 0));

            // when & then
            BusinessException exception = assertThrows(BusinessException.class, () -> farmScheduleService.addFarmSchedule(request));
            assertEquals(FARM_SCHEDULE_ALREADY_EXISTS, exception.getBusinessError());
        }

        @Test
        @DisplayName("중복된 스케줄이 있는 경우 save 메서드가 호출되지 않는다")
        void should_notCallSave_when_scheduleAlreadyExists() {
            // given
            FarmSchedule farmSchedule = FarmSchedule.builder()
                    .startTime(LocalTime.of(10, 0))
                    .endTime(LocalTime.of(12, 0))
                    .build();
            List<FarmSchedule> farmSchedules = List.of(farmSchedule);

            given(farmScheduleRepository.findByFarmIdAndDate(1L, LocalDate.now())).willReturn(farmSchedules);

            FarmScheduleUpdateRequest request = new FarmScheduleUpdateRequest(1L, LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(13, 0));

            // when & then
            assertThrows(BusinessException.class, () -> farmScheduleService.addFarmSchedule(request));
            verify(farmScheduleRepository, never()).save(any(FarmSchedule.class));
        }
    }

    @Nested
    @DisplayName("농장 스케줄 조회")
    class GetFarmSchedules {
        @Test
        @DisplayName("텅 빈 스케줄을 조회한다")
        void should_getEmptyFarmSchedules() {
            // given
            LocalDate date = LocalDate.now();
            given(farmScheduleRepository.findByFarmIdAndDate(1L, date)).willReturn(List.of());

            // when
            List<FarmSchedule> farmSchedules = farmScheduleService.getFarmScheduleByFarmIdAndDate(1L, date);

            // then
            assertTrue(farmSchedules.isEmpty());
        }

        @Test
        @DisplayName("특정 날짜의 스케줄을 조회한다")
        void should_getFarmSchedulesBySpecificDate() {
            // given
            LocalDate date = LocalDate.now();
            FarmSchedule farmSchedule = FarmSchedule.builder()
                    .farmId(1L)
                    .date(date)
                    .startTime(LocalTime.of(10, 0))
                    .endTime(LocalTime.of(12, 0))
                    .build();
            given(farmScheduleRepository.findByFarmIdAndDate(1L, date)).willReturn(List.of(farmSchedule));

            // when
            List<FarmSchedule> farmSchedules = farmScheduleService.getFarmScheduleByFarmIdAndDate(1L, date);

            // then
            assertAll(
                    () -> assertEquals(1, farmSchedules.size()),
                    () -> assertEquals(1L, farmSchedules.get(0).getFarmId()),
                    () -> assertEquals(date, farmSchedules.get(0).getDate()),
                    () -> assertEquals(LocalTime.of(10, 0), farmSchedules.get(0).getStartTime()),
                    () -> assertEquals(LocalTime.of(12, 0), farmSchedules.get(0).getEndTime())
            );
        }

        @Test
        @DisplayName("스케줄이 없는 날짜에 대해 빈 리스트를 반환한다")
        void should_returnEmptyList_when_noSchedulesOnDate() {
            // given
            LocalDate date = LocalDate.of(2024, 11, 12);
            given(farmScheduleRepository.findByFarmIdAndDate(1L, date)).willReturn(List.of());

            // when
            List<FarmSchedule> result = farmScheduleService.getFarmScheduleByFarmIdAndDate(1L, date);

            // then
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("특정 월에 해당하는 스케줄을 올바르게 조회한다")
        void should_returnSchedulesWithinSpecifiedMonth() {
            // given
            LocalDate startDate = LocalDate.of(2024, 11, 1);
            LocalDate endDate = LocalDate.of(2024, 11, 30);
            FarmSchedule farmSchedule = FarmSchedule.builder()
                    .farmId(1L)
                    .date(LocalDate.of(2024, 11, 10))
                    .startTime(LocalTime.of(10, 0))
                    .endTime(LocalTime.of(12, 0))
                    .build();

            given(farmScheduleRepository.findByFarmIdAndDateRange(1L, startDate, endDate)).willReturn(List.of(farmSchedule));

            // when
            FarmScheduleRequest request = new FarmScheduleRequest(1L, 2024, 11);
            List<FarmScheduleResponse> result = farmScheduleService.getFarmSchedulesByYearAndMonth(request);

            // then
            assertEquals(1, result.size());
            assertEquals(farmSchedule.getDate(), result.get(0).date());
            assertEquals(farmSchedule.getStartTime(), result.get(0).startTime());
            assertEquals(farmSchedule.getEndTime(), result.get(0).endTime());
        }

        @Test
        @DisplayName("존재하지 않는 스케줄 ID로 조회 시 예외가 발생한다")
        void should_throwException_when_scheduleIdNotFound() {
            // given
            Long invalidId = 999L;
            given(farmScheduleRepository.findById(invalidId)).willReturn(Optional.empty());

            // when & then
            BusinessException exception = assertThrows(BusinessException.class, () -> farmScheduleService.getFarmScheduleByScheduleId(invalidId));
            assertEquals(FARM_SCHEDULE_NOT_FOUND, exception.getBusinessError());
        }
    }
}
