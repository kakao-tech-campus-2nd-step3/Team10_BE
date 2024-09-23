package poomasi.global.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessError {
	EXAMPLE_ERROR(HttpStatus.BAD_REQUEST, "에러 예시입니다."),
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다."),
	INVALID_CREDENTIAL(HttpStatus.UNAUTHORIZED, "잘못된 비밀번호 입니다.");

	private final HttpStatus httpStatus;

	private final String message;
}
