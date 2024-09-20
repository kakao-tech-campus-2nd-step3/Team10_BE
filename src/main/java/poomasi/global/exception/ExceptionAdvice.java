package poomasi.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler(BusinessException.class)
	public ErrorResponse businessExceptionHandler(BusinessException exception) {
		BusinessError businessError = exception.getBusinessError();
		log.warn("Business error occurred: [{}] - {}", businessError.name(), businessError.getMessage());
		return ErrorResponse
			.builder(exception, businessError.getHttpStatus(), businessError.getMessage())
			.title(businessError.name())
			.build();
	}

	@ExceptionHandler(ApplicationException.class)
	public ErrorResponse applicationExceptionHandler(ApplicationException exception) {
		ApplicationError applicationError = exception.getApplicationError();
		log.error("Application error occurred: [{}] - {}", applicationError.name(), applicationError.getMessage(),
			exception);
		return ErrorResponse
			.builder(exception, HttpStatus.INTERNAL_SERVER_ERROR, applicationError.getMessage())
			.title(exception.getClass().getSimpleName())
			.build();
	}
}
