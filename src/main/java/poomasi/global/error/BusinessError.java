package poomasi.global.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessError {
	EXAMPLE_ERROR(HttpStatus.BAD_REQUEST, "에러 예시입니다."),


	// Member Error
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "멤버를 찾지 못했습니다."),
	MEMBER_NOT_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "인증이 되지 않았습니다."),
	MEMBER_NOT_AUTHORIZATION(HttpStatus.FORBIDDEN, "권한이 없습니다.")


	;

	private final HttpStatus httpStatus;

	private final String message;
}