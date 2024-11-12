package poomasi.domain.order._aftersales.dto.cancel.response;

import poomasi.domain.order._aftersales.entity._product.ProductAfterSalesStatus;
import poomasi.domain.order.entity._product.OrderedProductStatus;

import java.math.BigDecimal;

public record ProductCancelResponse(
        Long orderedProductId,
        OrderedProductStatus orderedProductStatus,

        Long productAfterSalesDetailId,
        Integer cancelQuantity,
        ProductAfterSalesStatus productAfterSalesStatus,
        BigDecimal finalCancelAmount) {
}
