package poomasi.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplicationError {
	EXAMPLE_ERROR("애플리케이션 에러 예시입니다.");

	private final String message;

}
