package poomasi.global.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessError {
	EXAMPLE_ERROR(HttpStatus.BAD_REQUEST, "에러 예시입니다."),

	PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),
	CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다."),
	;

	private final HttpStatus httpStatus;

	private final String message;
}
