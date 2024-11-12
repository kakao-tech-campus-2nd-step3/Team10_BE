package poomasi.domain.order._aftersales.dto.refund.response;

import poomasi.domain.order._aftersales.entity._product.ProductAfterSalesStatus;
import poomasi.domain.order.entity._product.OrderedProductStatus;

import java.math.BigDecimal;

public record ProductRefundRequestResponse(
        Long orderedProductId,
        OrderedProductStatus orderedProductStatus,

        Long productAfterSalesDetailId,
        Integer refundQuantity,
        ProductAfterSalesStatus productAfterSalesTypem,
        BigDecimal finalRefundAmount
) {
}


