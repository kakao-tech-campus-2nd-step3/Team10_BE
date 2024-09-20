package poomasi.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BusinessException extends RuntimeException {
	private final BusinessError businessError;
}
