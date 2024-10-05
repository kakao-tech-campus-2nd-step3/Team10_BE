package poomasi.domain.auth.token.refreshtoken.service;

import java.time.Duration;
import java.util.Optional;

public interface TokenStorageService {
    void setValues(String key, String data, Duration duration);
    Optional<String> getValues(String key, String data);
    void removeRefreshTokenById(final Long memberId);
}