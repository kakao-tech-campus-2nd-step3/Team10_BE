package poomasi.domain.farm._schedule.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "farm_schedule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FarmSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("농장")
    private Long farmId;

    @Comment("예약 가능 날짜")
    private LocalDate date;

    @Comment("예약 가능 여부")
    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    @Builder
    public FarmSchedule(Long farmId, LocalDate date, ScheduleStatus status) {
        this.farmId = farmId;
        this.date = date;
        this.status = status;
    }

    public void updateStatus(ScheduleStatus status) {
        this.status = status;
    }
}
