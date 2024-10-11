package poomasi.domain.reservation.dto.request;

import lombok.Builder;
import poomasi.domain.farm._schedule.entity.FarmSchedule;
import poomasi.domain.farm.entity.Farm;
import poomasi.domain.member.entity.Member;
import poomasi.domain.reservation.entity.Reservation;

import java.time.LocalDate;

@Builder
public record ReservationRequest(
        Long farmId,
        Long memberId,
        LocalDate reservationDate,

        int reservationCount,
        String request
) {
    public Reservation toEntity(Member member, Farm farm, FarmSchedule farmSchedule) {
        return Reservation.builder()
                .member(member)
                .farm(farm)
                .scheduleId(farmSchedule)
                .reservationDate(reservationDate)
                .reservationCount(reservationCount)
                .request(request)
                .build();
    }
}
