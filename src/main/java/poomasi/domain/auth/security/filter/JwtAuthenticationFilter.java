package poomasi.domain.auth.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import poomasi.domain.auth.security.userdetail.UserDetailsImpl;
import poomasi.domain.auth.token.util.JwtUtil;
import poomasi.domain.member.entity.Member;
import poomasi.domain.member.entity.Role;

import java.io.IOException;
import java.io.PrintWriter;

@Description("access token을 검증하는 필터")
@AllArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = request.getHeader("access");

        // refresh 재발급이나 다른 요청에 대해서 넘어감
        // access <~token~>
        if (accessToken  == null) {
            log.info("access token이 존재하지 않아서 다음 filter로 넘어갑니다.");
            filterChain.doFilter(request, response);
            return;
        }

        // 만료 검사
        if(jwtUtil.isTokenExpired(accessToken)){
            log.warn("[인증 실패] - 토큰이 만료되었습니다.");
            PrintWriter writer = response.getWriter();
            writer.print("만료된 토큰입니다.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 유효성 검사
        if(jwtUtil.validateToken(accessToken)) {
            log.warn("[인증 실패] - 위조된 토큰입니다.");
            PrintWriter writer = response.getWriter();
            writer.print("위조된 토큰입니다.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // access token 추출하기
        String tokenType = jwtUtil.getEmailFromToken(accessToken);

        if(!tokenType.equals("access")){
            log.info("[인증 실패] - 위조된 토큰입니다.");
            PrintWriter writer = response.getWriter();
            writer.print("위조된 토큰입니다.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String username = jwtUtil.getEmailFromToken(accessToken);
        String role = jwtUtil.getRoleFromToken(accessToken);

        Member member = new Member(username, Role.valueOf(role));
        UserDetailsImpl userDetailsImpl = new UserDetailsImpl(member);

        // (ID, password, auth)
        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetailsImpl, null, userDetailsImpl.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);


    }
}
