package poomasi.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BusinessError {
    EXAMPLE_ERROR(HttpStatus.BAD_REQUEST, "에러 예시입니다."),

    // Product
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),
    // Category
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다."),
    // Review
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다."),
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
    FARM_OWNER_MISMATCH(HttpStatus.FORBIDDEN, "해당 농장의 소유자가 아닙니다.");


    private final HttpStatus httpStatus;

    private final String message;
}
