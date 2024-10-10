package poomasi.domain.auth.token.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import poomasi.domain.auth.token.blacklist.service.TokenBlacklistService;
import poomasi.domain.auth.token.entity.TokenType;
import poomasi.domain.auth.token.refreshtoken.service.TokenStorageService;
import poomasi.domain.member.entity.Member;
import poomasi.domain.member.entity.Role;
import poomasi.domain.member.service.MemberService;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static poomasi.domain.auth.token.entity.TokenType.ACCESS;
import static poomasi.domain.auth.token.entity.TokenType.REFRESH;

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

    private final TokenBlacklistService tokenBlacklistService;
    private final TokenStorageService tokenStorageService;
    private final MemberService memberService;

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

    public String getRoleFromTokenInFilter(final String token) {
        return getClaimFromToken(token, "role", String.class);
    }


    // <----------------->
    // 토큰 생성
    public String generateAccessTokenById(final Long memberId) {
        Map<String, Object> claims = createClaims(memberId);
        claims.put("type", ACCESS);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(memberId))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshTokenById(final Long memberId) {
        Map<String, Object> claims = createClaims(memberId);
        claims.put("type", REFRESH);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(memberId))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Long getIdFromToken(final String token) {
        return getClaimFromToken(token, "id", Long.class);
    }

    public String getEmailFromToken(final String token) {
        return getClaimFromToken(token, "email", String.class);
    }

    public Role getRoleFromToken(final String token) {
        return getClaimFromToken(token, "role", Role.class);
    }

    public TokenType getTokenTypeFromToken(final String token) {
        return getClaimFromToken(token, "type", TokenType.class);
    }


    public <T> T getClaimFromToken(final String token, String claimKey, Class<T> claimType) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get(claimKey, claimType);
    }

    private Claims getAllClaimsFromToken(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Map<String, Object> createClaims(Long memberId) {
        Map<String, Object> claims = new HashMap<>();
        Member member = memberService.findMemberById(memberId);

        claims.put("id", memberId);
        claims.put("email", member.getEmail());
        claims.put("role", member.getRole());

        return claims;
    }

    public Date getExpirationDateFromToken(final String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    public long getAccessTokenExpiration() {
        return ACCESS_TOKEN_EXPIRATION_TIME;
    }

    // 토큰 유효성 검사
    public Boolean validateAccessToken(final String accessToken){
        if (!validateToken(accessToken)) {
            return false;
        }
        if (tokenBlacklistService.hasKeyBlackList(accessToken)){
            log.warn("로그아웃한 JWT token입니다.");
            return false;
        }
        return true;
    }

    public Boolean validateRefreshToken(final String refreshToken, final Long memberId) {
        if (!validateToken(refreshToken)) {
            return false;
        }
        String storedMemberId = tokenStorageService.getValues(refreshToken, memberId.toString())
                .orElse(null);

        if (storedMemberId == null || !storedMemberId.equals(memberId.toString())) {
            log.warn("리프레시 토큰과 멤버 ID가 일치하지 않습니다.");
            return false;
        }

        return true;
    }

    private Boolean validateToken(final String token) {
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

}