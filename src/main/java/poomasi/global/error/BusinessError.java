package poomasi.global.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessError {
    //member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다."),
    DUPLICATE_MEMBER_EMAIL(HttpStatus.CONFLICT, "중복된 이메일입니다."),
    INVALID_FARMER_QUALIFICATION(HttpStatus.BAD_REQUEST, "농부 자격 증명이 필요합니다."),
    //auth
    INVALID_CREDENTIAL(HttpStatus.UNAUTHORIZED, "잘못된 비밀번호 입니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 유효하지 않습니다."),
    // Farm
    FARM_NOT_FOUND(HttpStatus.NOT_FOUND, "농장을 찾을 수 없습니다."),
    FARM_OWNER_MISMATCH(HttpStatus.FORBIDDEN, "해당 농장의 소유자가 아닙니다."),
    FARM_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 농장이 존재합니다."),

    // FarmSchedule
    FARM_SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 날짜의 스케줄을 찾을 수 없습니다."),
    FARM_SCHEDULE_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 스케줄이 존재합니다."),
    ;


    private final HttpStatus httpStatus;

    private final String message;
}
