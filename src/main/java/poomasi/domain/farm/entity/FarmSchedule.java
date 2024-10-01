package poomasi.domain.farm.entity;

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

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;

    @Comment("예약 가능 날짜")
    private LocalDate date;

    @Comment("예약 가능 여부")
    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    @Builder
    public FarmSchedule(Farm farm, LocalDate date, ScheduleStatus status) {
        this.farm = farm;
        this.date = date;
        this.status = status;
    }
}
