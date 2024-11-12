package poomasi.domain.order._payment.dto.response;

import poomasi.domain.order._payment.entity.Payment;
import poomasi.domain.order._payment.entity.PaymentMethod;

import java.math.BigDecimal;

public record PaymentResponse(Long paymentId,
                              BigDecimal totalPrice,
                              BigDecimal discountPrice,
                              BigDecimal finalPrice,
                              PaymentMethod paymentMethod
) {
    public static PaymentResponse fromEntity(Payment payment){
        return new PaymentResponse(
                payment.getId(),
                payment.getTotalPrice(),
                payment.getDiscountPrice(),
                payment.getFinalPrice(),
                payment.getPaymentMethod()
        );
    }
}
