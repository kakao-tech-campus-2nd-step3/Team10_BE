package poomasi.global.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import poomasi.global.redis.service.RedisService;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey secretKey;

    @Value("${jwt.access-token-expiration-time}")
    private long ACCESS_TOKEN_EXPIRATION_TIME;

    @Value("${jwt.refresh-token-expiration-time}")
    private long REFRESH_TOKEN_EXPIRATION_TIME;

    private final RedisService redisService;

    @PostConstruct
    public void init() {
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // 토큰 생성
    public String generateAccessToken(final String email, final Map<String, Object> claims) {
        claims.put("email", email);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(final String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰에서 정보 추출
    public String getEmailFromToken(final String token) {
        return getClaimFromToken(token, "email");
    }

    public String getClaimFromToken(final String token, String claimKey) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get(claimKey, String.class);
    }

    public Date getExpirationDateFromToken(final String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Claims getAllClaimsFromToken(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰 유효성 검사
    public Boolean validateAccessToken(final String accessToken){
        if (!validateToken(accessToken)) {
            return false;
        }
        if (redisService.hasKeyBlackList(accessToken)){
            log.warn("로그아웃한 JWT token입니다.");
            return false;
        }
        return true;
    }

    public Boolean validateRefreshToken(final String refreshToken, final Long memberId) {
        if (!validateToken(refreshToken)) {
            return false;
        }
        String storedMemberId = redisService.getValues(refreshToken)
                .orElse(null);

        if (storedMemberId == null || !storedMemberId.equals(memberId.toString())) {
            log.warn("리프레시 토큰과 멤버 ID가 일치하지 않습니다.");
            return false;
        }

        return true;
    }

    public Boolean validateToken(final String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException e) {
            log.error("잘못된 JWT 서명입니다.");
        } catch (MalformedJwtException e) {
            log.error("잘못된 JWT token입니다.");
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT token입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT token입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT token이 비어있습니다.");
        }

        return false;
    }

    // 토큰 만료 여부 확인
    public boolean isTokenExpired(String token) {
        try {
            Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public long getAccessTokenExpiration() {
        return ACCESS_TOKEN_EXPIRATION_TIME;
    }
}