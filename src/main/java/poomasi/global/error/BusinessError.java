package poomasi.global.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BusinessError {
    // Product
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),
    PRODUCT_STOCK_ZERO(HttpStatus.BAD_REQUEST, "재고가 없습니다."),
    STOCK_QUANTITY_EXCEEDED(HttpStatus.BAD_REQUEST, "장바구나 수량이 남은 재고를 초과하였습니다"),

    // Category
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다."),

    // Review
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다."),

    // Member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다."),
    DUPLICATE_MEMBER_EMAIL(HttpStatus.CONFLICT, "중복된 이메일입니다."),
    INVALID_FARMER_QUALIFICATION(HttpStatus.BAD_REQUEST, "농부 자격 증명이 필요합니다."),
    MEMBER_ALREADY_CUSTOMER(HttpStatus.BAD_REQUEST, "이미 고객인 회원입니다."),
    MEMBER_ALREADY_FARMER(HttpStatus.BAD_REQUEST, "이미 농부인 회원입니다."),
    MEMBER_ID_MISMATCH(HttpStatus.FORBIDDEN, "권한이 없는 요청입니다."),
    INVALID_ROLE(HttpStatus.FORBIDDEN, "권한이 없는 요청입니다."),

    // MemberProfile
    MEMBER_PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "회원 세부 정보가 존재하지 않습니다."),

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
    START_TIME_SHOULD_BE_BEFORE_END_TIME(HttpStatus.BAD_REQUEST, "시작 시간은 종료 시간보다 이전이어야 합니다."),


    // Reservation
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "예약을 찾을 수 없습니다."),
    RESERVATION_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 예약이 존재합니다."),
    RESERVATION_NOT_ACCESSIBLE(HttpStatus.FORBIDDEN, "접근할 수 없는 예약입니다."),
    RESERVATION_ALREADY_CANCELED(HttpStatus.BAD_REQUEST, "이미 취소된 예약입니다."),
    RESERVATION_CANCELLATION_PERIOD_EXPIRED(HttpStatus.BAD_REQUEST, "예약 취소 기간이 지났습니다."),
    RESERVATION_FULL(HttpStatus.BAD_REQUEST, "예약이 꽉 찼습니다."),
    RESERVATION_MEMBER_EXCEED(HttpStatus.BAD_REQUEST, "최대 수용 가능 인원을 초과했습니다."),

    //Cart
    CART_NOT_FOUND(HttpStatus.NOT_FOUND, "장바구니를 찾을 수 없습니다."),

    //ProductTag
    INVALID_TAG_NAME(HttpStatus.BAD_REQUEST, "존재하지 않는 태그명입니다."),
    TAG_NOT_FOUND(HttpStatus.NOT_FOUND, "태그가 존재하지 않습니다."),

    // ETC
    START_DATE_SHOULD_BE_BEFORE_END_DATE(HttpStatus.BAD_REQUEST, "시작 날짜는 종료 날짜보다 이전이어야 합니다."),

    // Image
    IMAGE_LIMIT_EXCEED(HttpStatus.BAD_REQUEST, "사진은 최대 5장까지 등록 가능합니다."),
    IMAGE_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 이미지가 존재합니다"),
    IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "이미지를 찾을 수 없습니다."),
    IMAGE_OWNER_MISMATCH(HttpStatus.FORBIDDEN, "해당 이미지의 소유자가 아닙니다."),

    // Order
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."),
    INVALID_ORDER_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 주문 요청입니다."),
    ORDER_VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "주문 검증에 실패했습니다."),
    ORDER_ALREADY_PROCESSED(HttpStatus.CONFLICT, "해당 주문은 이미 처리되었습니다."),
    ORDER_VERIFICATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, "주문 검증에 실패했습니다."),
    ORDER_NOT_OWNED_EXCEPTION(HttpStatus.UNAUTHORIZED, "허가되지 않은 주문입니다."),
    ORDER_PRODUCT_DETAILS_NOT_FOUND(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."),
    ORDER_PRODUCT_DETAILS_NOT_OWNED_EXCEPTION(HttpStatus.UNAUTHORIZED, "허가되지 않은 주문입니다."),
    ORDERED_PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "찾을 수 없는 주문입니다."),


    // PAYMENT
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "결제를 찾을 수 없습니다."),
    PAYMENT_AMOUNT_MISMATCH(HttpStatus.BAD_REQUEST, "사전 결제 금액과 사후 결제 금액이 일치하지 않습니다."),
    PAYMENT_BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못 된 결제 요청입니다."),
    CHECKSUM_EXCESSIVE_REFUND_AMOUNT(HttpStatus.BAD_REQUEST, "환불 요청 금액이 환불 가능한 금액보다 더 많습니다"),

    //Store
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "등록된 상점이 없습니다."),

    // After sales
    SHIPPING_ALREADY_IN_PROGRESS(HttpStatus.BAD_REQUEST, "배송 준비 중이거나 배송 중인 주문입니다."),
    EXCHANGE_NOT_ALLOWED_IN_TRANSIT(HttpStatus.BAD_REQUEST, "배송 중인 상품에 대해선 교환 요청을 할 수 없습니다."),
    SHIPPING_COST_GREATER_THAN_REFUND(HttpStatus.BAD_REQUEST, "배송비가 환불 금액보다 더 큽니다."),
    CANCEL_QUANTITY_EXCEEDED(HttpStatus.BAD_REQUEST, "취소 가능한 수량을 초과한 요청입니다."),
    REFUND_QUANTITY_EXCEEDED(HttpStatus.BAD_REQUEST, "환불 가능한 수량을 초과한 요청입니다."),
    PURCHASE_ALREADY_CONFIRMED(HttpStatus.BAD_REQUEST, "이미 구매 확정이 된 상태입니다."),
    REFUND_NOT_ALLOWED_BEFORE_SHIPPING(HttpStatus.BAD_REQUEST, "배송 대기 전 상태에서는 환불을 요청할 수 없습니다."),
    REFUND_AFTER_SALES_NOT_FOUND(HttpStatus.NOT_FOUND , "찾을 수 없는 환불 요청입니다."),
    REFUND_AFTER_SALES_REQUEST_INVALID_OWNER(HttpStatus.BAD_REQUEST, "판매자의 환불 요청이 아닙니다."),
    ;

    private final HttpStatus httpStatus;

    private final String message;
}

