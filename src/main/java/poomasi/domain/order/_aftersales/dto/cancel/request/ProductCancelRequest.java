package poomasi.domain.order._aftersales.dto.cancel.request;

public record ProductCancelRequest(Long orderedProductId, Integer cancelRequestQuantity, String cancelReason) {
}
