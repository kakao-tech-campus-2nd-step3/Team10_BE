package poomasi.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poomasi.domain.reservation.entity.Reservation;
import poomasi.domain.reservation.repository.ReservationRepository;
import poomasi.global.error.BusinessError;
import poomasi.global.error.BusinessException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    private Reservation getReservation(Long id) {
        return reservationRepository.findById(id).orElseThrow(() -> new BusinessException(BusinessError.RESERVATION_NOT_FOUND));
    }

    private List<Reservation> getReservationsByMemberId(Long memberId) {
        return reservationRepository.findAllByMemberId(memberId);
    }

    private List<Reservation> getReservationsByFarmId(Long farmId) {
        return reservationRepository.findAllByFarmId(farmId);
    }

    private Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }
}
