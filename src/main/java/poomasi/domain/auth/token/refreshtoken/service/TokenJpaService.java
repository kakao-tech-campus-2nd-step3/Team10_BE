package poomasi.domain.auth.token.refreshtoken.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import poomasi.domain.auth.token.refreshtoken.entity.RefreshToken;
import poomasi.domain.auth.token.refreshtoken.repository.RefreshTokenRepository;

import java.time.Duration;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenJpaService implements TokenStorageService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void setValues(String key, String data, Duration duration) {
        RefreshToken tokenEntity = new RefreshToken();
        tokenEntity.setKey(key);
        tokenEntity.setData(data);
        // 여기에서 만료 시간을 관리하기 위한 로직을 추가할 수 있음
        refreshTokenRepository.save(tokenEntity);
    }

    @Override
    public Optional<String> getValues(String key, String data) {
        return refreshTokenRepository.findByKey(key).map(RefreshToken::getData);
    }

    @Override
    public void removeRefreshTokenById(final Long memberId) {
        refreshTokenRepository.deleteAllByData(String.valueOf(memberId));
    }
}
