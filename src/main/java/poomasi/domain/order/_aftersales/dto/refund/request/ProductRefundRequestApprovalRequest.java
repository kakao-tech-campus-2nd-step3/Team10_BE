package poomasi.domain.order._aftersales.dto.refund.request;

public record ProductRefundRequestApprovalRequest(Long productAfterSalesDetailId,
                                                  String invoiceNumber) {
}
