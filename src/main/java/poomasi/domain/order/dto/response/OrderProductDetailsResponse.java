package poomasi.domain.order.dto.response;

import poomasi.domain.order.entity._product.OrderedProduct;

import java.math.BigDecimal;

public record OrderProductDetailsResponse(
        Long orderId,
        Long orderProductDetailsId,
        Long productId,
        String productName,
        Integer count,
        BigDecimal price, //총 결제 금액
        String invoiceNumber
        ) {
    public static OrderProductDetailsResponse fromEntity(OrderedProduct orderedProduct) {
        return new OrderProductDetailsResponse(
                orderedProduct.getOrderId(),
                orderedProduct.getId(),
                orderedProduct.getProduct().getId(),
                orderedProduct.getProductName(),
                orderedProduct.getCount(),
                orderedProduct.getPrice(),
                orderedProduct.getInvoiceNumber()
        );
    }

}
