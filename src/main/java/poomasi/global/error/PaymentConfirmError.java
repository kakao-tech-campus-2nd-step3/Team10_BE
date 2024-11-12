package poomasi.global.error;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PaymentConfirmError {

    PAYMENT_PROUCT_CONFIRM_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "결제 직전 상품 수량 부족")
    ;

    private final HttpStatus httpStatus;
    private final String reason;


}
