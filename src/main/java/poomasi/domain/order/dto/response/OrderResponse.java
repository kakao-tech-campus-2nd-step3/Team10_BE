package poomasi.domain.order.dto.response;

import poomasi.domain.order.entity._product.ProductOrder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record OrderResponse(Long orderId,
                            String merchantUid,
                            LocalDateTime createdAt,
                            List<OrderProductDetailsResponse> orderProductDetailsResponseList) {
    public static OrderResponse fromEntity(ProductOrder productOrder) {
        return new OrderResponse(
                productOrder.getId(),
                productOrder.getMerchantUid(),
                productOrder.getCreatedAt(),
                productOrder.getOrderedProducts()
                        .stream()
                        .map(OrderProductDetailsResponse::fromEntity)
                        .collect(Collectors.toList())
                );
    }
}
