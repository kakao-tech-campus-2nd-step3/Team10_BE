package poomasi.domain.order.dto.request;

public record ProductOrderRegisterRequest(String destinationAddress,
                                          String destinationAddressDetail,
                                          String deliveryRequest) {
}
