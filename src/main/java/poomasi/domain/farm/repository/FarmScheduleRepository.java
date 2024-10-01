package poomasi.domain.farm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import poomasi.domain.farm.entity.FarmSchedule;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FarmScheduleRepository extends JpaRepository<FarmSchedule, Long> {
    @Query("SELECT f FROM FarmSchedule f WHERE f.farmId = :farmId AND f.date BETWEEN :startDate AND :endDate")
    List<FarmSchedule> findByFarmIdAndDateRange(Long farmId, LocalDate startDate, LocalDate endDate);

    Optional<FarmSchedule> findByFarmIdAndDate(Long aLong, LocalDate date);

}
