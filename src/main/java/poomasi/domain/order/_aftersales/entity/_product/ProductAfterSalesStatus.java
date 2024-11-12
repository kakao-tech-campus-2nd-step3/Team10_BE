package poomasi.domain.order._aftersales.entity._product;

public enum ProductAfterSalesStatus {
    EXCHANGE,
    CANCEL,
    REFUND,


    //환불
    REFUND_REQUESTED,           // 환불 요청됨
    REFUND_APPROVED,            // 환불 승인됨
    REFUND_SHIPMENT_STARTED,    // 환불 배송 시작 (반품 물품의 배송 시작)
    REFUND_IN_TRANSIT,          // 환불 배송 중 (반품 물품이 배송 중)
    REFUND_DELIVERED,           // 환불 배송 완료 (반품 물품이 도착함)
    REFUND_IN_PROGRESS,         // 환불 처리 중 (반품 수거 중이거나 처리 대기 중)
    REFUND_COMPLETED,           // 환불 완료
    REFUND_DENIED              // 환불 요청 거절됨


    ;

}
