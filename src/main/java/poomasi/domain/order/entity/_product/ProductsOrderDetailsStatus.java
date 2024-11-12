package poomasi.domain.order.entity._product;

public enum ProductsOrderDetailsStatus {
    WAITING_SHIPPING,
    IN_SHIPPING, // 배송 중
    DELIVERED_COMPLETE, // 배송 완료
    CANCELLED, // 취소
    RETURNED,  // 환불
    EXCHANGED  // 교환
    ;
}
