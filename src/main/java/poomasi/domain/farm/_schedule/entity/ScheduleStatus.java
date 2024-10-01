package poomasi.domain.farm._schedule.entity;

public enum ScheduleStatus {
    PENDING,
    RESERVED,
    ;

    public boolean isAvailable() {
        return this == PENDING;
    }

    public boolean isReserved() {
        return this == RESERVED;
    }
}
