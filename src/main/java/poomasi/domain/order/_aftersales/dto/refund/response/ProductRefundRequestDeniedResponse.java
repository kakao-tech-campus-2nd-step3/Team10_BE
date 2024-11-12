package poomasi.domain.order._aftersales.dto.refund.response;

import poomasi.domain.order._aftersales.entity._product.ProductAfterSalesStatus;

public record ProductRefundRequestDeniedResponse(Long productAfterSalesDetailId,
                                                 ProductAfterSalesStatus productAfterSalesStatus,
                                                 String productRefundDeniedReason) {
}
