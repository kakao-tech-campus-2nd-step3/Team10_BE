package poomasi.domain.order.dto.response;

import poomasi.domain.order.entity._product.ProductOrderDetails;

public record OrderDetailsResponse(
        String address,
        String addressDetails,
        String deliveryRequest
) {
    public static OrderDetailsResponse fromEntity(ProductOrderDetails productOrderDetails) {
        return new OrderDetailsResponse(
                productOrderDetails.getDestinationAddress(),
                productOrderDetails.getDestinationAddressDetail(),
                productOrderDetails.getDeliveryRequest()
        );
    }
}
