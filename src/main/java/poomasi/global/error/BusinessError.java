package poomasi.global.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessError {
    // Product
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),

    // Category
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다."),

    // Member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다."),
    DUPLICATE_MEMBER_EMAIL(HttpStatus.CONFLICT, "중복된 이메일입니다."),
    INVALID_FARMER_QUALIFICATION(HttpStatus.BAD_REQUEST, "농부 자격 증명이 필요합니다."),

    // Auth
    INVALID_CREDENTIAL(HttpStatus.UNAUTHORIZED, "잘못된 비밀번호 입니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "리프레시 토큰이 없습니다."),
    REFRESH_TOKEN_NOT_VALID(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 유효하지 않습니다."),

    // Farm
    FARM_NOT_FOUND(HttpStatus.NOT_FOUND, "농장을 찾을 수 없습니다."),
    FARM_OWNER_MISMATCH(HttpStatus.FORBIDDEN, "해당 농장의 소유자가 아닙니다."),
    FARM_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 농장이 존재합니다."),
    FARM_NOT_OPEN(HttpStatus.BAD_REQUEST, "오픈되지 않은 농장입니다."),

    // FarmSchedule
    FARM_SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 날짜의 스케줄을 찾을 수 없습니다."),
    FARM_SCHEDULE_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 스케줄이 존재합니다."),
    FARM_SCHEDULE_ALREADY_RESERVED(HttpStatus.CONFLICT, "해당 날짜에 이미 예약이 존재합니다."),
    FARM_SCHEDULE_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "예약이 불가능한 날짜입니다."),

    // Reservation
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "예약을 찾을 수 없습니다."),
    RESERVATION_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 예약이 존재합니다."),

    // ETC
    START_DATE_SHOULD_BE_BEFORE_END_DATE(HttpStatus.BAD_REQUEST, "시작 날짜는 종료 날짜보다 이전이어야 합니다.");


    private final HttpStatus httpStatus;

    private final String message;
}
