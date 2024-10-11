package poomasi.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.reservation.dto.response.ReservationResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationFarmerService {
    private final ReservationService reservationService;

    @Transactional(readOnly = true)
    public List<ReservationResponse> getReservationsByFarmerId(Long farmerId) {
        return reservationService.getReservationsByFarmerId(farmerId);
    }


}
