package poomasi.global.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessError {
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다."),
	INVALID_CREDENTIAL(HttpStatus.UNAUTHORIZED, "잘못된 비밀번호 입니다."),
	REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 유효하지 않습니다."),
	DUPLICATE_MEMBER_EMAIL(HttpStatus.CONFLICT, "중복된 이메일입니다."),
	INVALID_BUSINESS_REGISTRATION(HttpStatus.BAD_REQUEST, "유효하지 않은 사업자 등록번호입니다.");

	private final HttpStatus httpStatus;

	private final String message;
}
