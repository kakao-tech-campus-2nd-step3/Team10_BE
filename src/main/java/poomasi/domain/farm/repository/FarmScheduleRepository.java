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
    @Query("SELECT FarmSchedule(f.id, f.date, f.status) FROM FarmSchedule f WHERE f.farm.id = :farmId AND MONTH(f.date) = :month")
    List<FarmSchedule> findByFarmIdAndMonth(Long farmId, Long month);

    Optional<FarmSchedule> findByFarmIdAndDate(Long aLong, LocalDate date);

}
