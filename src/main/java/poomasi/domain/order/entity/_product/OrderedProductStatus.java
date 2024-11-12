package poomasi.domain.order.entity._product;

public enum OrderedProductStatus {
    
    PENDING_SELLER_APPROVAL, // 판매자 수락 전 (주문 완료 후 대기 상태)
    SHIPMENT_STARTED,        // 배송 시작 (판매자 수락을 하면 바뀌는 상태)
    IN_TRANSIT,              // 배송 중
    DELIVERED,                // 배송 완료
    CANCELLED,             // 주문 취소 완료 (취소가 최종적으로 완료된 상태)

    //교환
    EXCHANGE_PENDING,      // 교환 요청 대기중
    EXCHANGE_APPROVED,       // 교환 요청 승인됨
    EXCHANGE_IN_PROGRESS,    // 교환 처리 중 (배송 중이거나 준비 중)
    EXCHANGE_COMPLETED,      // 교환 완료
    EXCHANGE_DENIED,          // 교환 요청 거절됨
    
    //환불
    REFUND_REQUESTED,           // 환불 요청됨
    REFUND_APPROVED,            // 환불 승인됨
    REFUND_SHIPMENT_STARTED,    // 환불 배송 시작 (반품 물품의 배송 시작)
    REFUND_IN_TRANSIT,          // 환불 배송 중 (반품 물품이 배송 중)
    REFUND_DELIVERED,           // 환불 배송 완료 (반품 물품이 도착함)
    REFUND_IN_PROGRESS,         // 환불 처리 중 (반품 수거 중이거나 처리 대기 중)
    REFUND_COMPLETED,           // 환불 완료
    REFUND_DENIED,              // 환불 요청 거절됨

    //주문 취소
    CANCEL_PENDING,           // 주문 취소 대기 중 (취소 요청을 받은 상태)
    ;
}