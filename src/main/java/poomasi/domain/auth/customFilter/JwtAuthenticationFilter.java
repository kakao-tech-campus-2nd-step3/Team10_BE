package poomasi.domain.auth.customFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// 24.09.27 -> 토큰 만료 되면 기존에는 다시 로그인하는 방향
// 하지만 refresh 토큰 사용한다면 : 넘기지 않고 expired 되었다고 프론트에게 알리기
// refresh로 Access token 재발급 하도록 핸들링
// refresh도 만료가 됐다면? 재로그인 해야 함.
// security단에서 해야 하는가? 아니면 mvc pattern으로 넘겨야 하는가?
//TODO :

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    //TODO : jwt 주입받기
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //request에서 Authorization 헤더를 찾음
        String authorization= request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.info("[인증 실패] - 토큰 헤더가 잘못됐거나, 토큰이 존재하지 않습니다.");
            filterChain.doFilter(request, response);
            return;
           }

        String token = authorization.split(" ")[1]; // 토큰 분리
        /*TODO : token 유효성 검사
        if (token이 유효하지 않다면) {
            log.info("[인증 실패] - 토큰이 유효하지 않습니다");
            filterChain.doFilter(request, response);
            return;
        }*/

        /*TODO : token에서 username과 role와 같은 정보를 획득하고
        member 생성해야 함.
        이후 , UserDetailsImpl 객체 만든 후, UsernamePasswordAuthenticationToken 만들어서
        security context에 등록을 해야 함
        그러고 filterChain.doFilter(request, response); 로 다음 filter로 넘어가야 한다.
         */

    }
}
