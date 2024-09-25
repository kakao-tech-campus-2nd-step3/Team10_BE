package poomasi.global.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessError {
    // Farm
    FARM_NOT_FOUND(HttpStatus.NOT_FOUND, "농장을 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;

    private final String message;
}
