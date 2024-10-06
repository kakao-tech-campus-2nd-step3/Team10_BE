package poomasi.domain.auth.token.refreshtoken.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.auth.token.refreshtoken.entity.RefreshToken;
import poomasi.domain.auth.token.refreshtoken.repository.TokenRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenJpaService implements TokenStorageService {

    private final TokenRepository tokenRepository;

    @Override
    @Transactional
    public void setValues(String key, String data, Duration duration) {
        RefreshToken tokenEntity = new RefreshToken();
        tokenEntity.setTokenKey(key);
        tokenEntity.setData(data);
        tokenEntity.setExpireAt(LocalDateTime.now().plusSeconds(duration.getSeconds()));
        tokenRepository.save(tokenEntity);
    }

    @Override
    public Optional<String> getValues(String key, String data) {
        return tokenRepository.findByKeyAndExpireAtAfter(key, LocalDateTime.now())
                .map(RefreshToken::getData);
    }

    @Override
    @Transactional
    public void removeRefreshTokenById(final Long memberId) {
        tokenRepository.deleteAllByData(String.valueOf(memberId));
    }

    @Transactional
    public void removeExpiredTokens() {
        tokenRepository.deleteAllByExpireAtBefore(LocalDateTime.now());
    }
}
