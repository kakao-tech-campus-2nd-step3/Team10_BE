package poomasi.domain.auth.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import poomasi.domain.member.entity.Role;
import poomasi.global.redis.service.RedisService;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
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

    public String generateTokenInFilter(String email, String role , String tokenType, Long memberId){
        Map<String, Object> claims = this.createClaims(email, role, tokenType);
        String memberIdString = memberId.toString();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(memberIdString)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    private Map<String, Object> createClaims(String email, String role, String tokenType) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("role", role);
        claims.put("tokenType" , tokenType);
        return claims;
    }

    public Boolean validateTokenInFilter(String token){

        log.info("jwt util에서 토큰 검증을 진행합니다 . .");

        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.info("jwt util에서 토큰 검증 하다가 exception 터졌습니다.");
            log.info(e.getMessage());
            return false;
        }

    }



    ///////////////////////

    //subject 추출하기
    public String getSubjectFromToken(final String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public String getEmailFromToken(final String token) {
        return getClaimFromToken(token, "email", String.class);
    }

    public String getRoleFromToken(final String token) {
        return getClaimFromToken(token, "role", String.class);
    }

    public String getTokenTypeFromToken(final String token) {
        return getClaimFromToken(token, "tokenType", String.class);
    }





    // 토큰 생성
    public String generateAccessToken(final String memberId, final Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(memberId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(final String memberId, final Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(memberId)
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public <T> T getClaimFromToken(final String token, String claimKey, Class<T> claimType) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get(claimKey, claimType);
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