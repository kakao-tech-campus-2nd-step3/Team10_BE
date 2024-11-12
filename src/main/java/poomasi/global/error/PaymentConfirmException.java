package poomasi.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentConfirmException extends RuntimeException {

    private final PaymentConfirmError paymentConfirmError;

}
