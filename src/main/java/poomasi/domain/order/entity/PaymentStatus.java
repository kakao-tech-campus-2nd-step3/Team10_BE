package poomasi.domain.order.entity;

public enum PaymentStatus {
    PAYMENT_PENDING,      // 결제 대기 중
    PAYMENT_COMPLETE,     // 결제 성공
    PAYMENT_DECLINED, // 결제 거부
    PAYMENT_INSUFFICIENT_QUANTITY
    ;
}

