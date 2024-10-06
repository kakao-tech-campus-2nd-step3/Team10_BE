package poomasi.domain.auth.token.blacklist.service;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
public interface TokenBlacklistService {
    void setBlackList(String key, String data, Duration duration);
    Optional<String> getBlackList(String key);
    void deleteBlackList(String key);
    boolean hasKeyBlackList(String key);

}
