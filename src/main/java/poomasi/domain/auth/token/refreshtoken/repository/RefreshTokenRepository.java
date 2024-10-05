package poomasi.domain.auth.token.refreshtoken.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poomasi.domain.auth.token.refreshtoken.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByKey(String key);
    void deleteAllByData(String Data);
}