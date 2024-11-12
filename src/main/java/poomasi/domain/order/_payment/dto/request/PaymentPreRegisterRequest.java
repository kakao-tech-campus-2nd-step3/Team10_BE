package poomasi.domain.order._payment.dto.request;

import java.math.BigDecimal;

public record PaymentPreRegisterRequest(String merchantUid, BigDecimal amount) {
}
