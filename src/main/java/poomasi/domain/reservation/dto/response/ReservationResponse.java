package poomasi.domain.reservation.dto.response;

import lombok.Builder;
import poomasi.domain.reservation.entity.ReservationStatus;

import java.time.LocalDate;

@Builder
public record ReservationResponse(
        Long farmId,
        Long memberId,
        Long scheduleId,
        LocalDate reservationDate,
        int reservationCount,
        ReservationStatus status,
        String request

) {
}
