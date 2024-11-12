package poomasi.global.error;

import com.siot.IamportRestClient.exception.IamportResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    public ErrorResponse businessExceptionHandler(BusinessException exception) {
        BusinessError businessError = exception.getBusinessError();
        log.warn("[{}] : {}", businessError.name(), businessError.getMessage());
        return ErrorResponse
                .builder(exception, businessError.getHttpStatus(), businessError.getMessage())
                .title(businessError.name())
                .build();
    }

    @ExceptionHandler(ApplicationException.class)
    public ErrorResponse applicationExceptionHandler(ApplicationException exception) {
        ApplicationError applicationError = exception.getApplicationError();
        log.error("[{}] : {}", applicationError.name(), applicationError.getMessage(),
                exception);
        return ErrorResponse
                .builder(exception, HttpStatus.INTERNAL_SERVER_ERROR, applicationError.getMessage())
                .title(exception.getClass().getSimpleName())
                .build();
    }

    @ExceptionHandler(PaymentConfirmException.class)
    public ErrorResponse paymentConfirmExceptionHandler(PaymentConfirmException exception) {
        PaymentConfirmError paymentConfirmError = exception.getPaymentConfirmError();
        log.error("[{}] : {}", paymentConfirmError.name(), paymentConfirmError.getReason());
        return ErrorResponse
                .builder(exception, paymentConfirmError.getHttpStatus(), paymentConfirmError.getReason())
                .title(paymentConfirmError.name())
                .build();
    }

    @ExceptionHandler(IamportResponseException.class)
    public ErrorResponse IamportResponseExceptionHandler(IamportResponseException exception) {

        log.error("[{}] : {}", "IamportResponseException", exception.getMessage());
        return ErrorResponse
                .builder(exception, HttpStatus.BAD_GATEWAY, exception.getMessage())
                .title("아임포트 서버 응답 장애 발생")
                .build();
    }

    @ExceptionHandler(IOException.class)
    public ErrorResponse IamportResponseExceptionHandler(IOException exception) {
        log.error("[{}] : {}", "IOException", exception.getMessage());
        return ErrorResponse
                .builder(exception, HttpStatus.BAD_GATEWAY, exception.getMessage())
                .title("통신 도중 IOException 발생")
                .build();
    }


}
