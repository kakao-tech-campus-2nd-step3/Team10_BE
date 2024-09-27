package poomasi.global.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessError {
	// Member
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "멤버를 찾지 못했습니다."),
	MEMBER_NOT_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "인증이 되지 않았습니다."),
	MEMBER_NOT_AUTHORIZATION(HttpStatus.FORBIDDEN, "권한이 없습니다."),

    // Farm
    FARM_NOT_FOUND(HttpStatus.NOT_FOUND, "농장을 찾을 수 없습니다."),
    FARM_OWNER_MISMATCH(HttpStatus.FORBIDDEN, "해당 농장의 소유자가 아닙니다.")
    ;

	private final HttpStatus httpStatus;

	private final String message;
}
