package poomasi.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplicationError {
    ENCRYPT_ERROR("암호화 에러입니다.");

    private final String message;

}
