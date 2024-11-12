package poomasi.domain.order._aftersales.dto.refund.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductRefundRequest(
        @NotNull Long orderedProductId,  // 필수 필드
        @Positive Integer refundRequestQuantity, //필수
        @Size(max = 500) String refundReason, // 필수 필드
        @Size(max = 20) String request // nullable 필드
) {
}
