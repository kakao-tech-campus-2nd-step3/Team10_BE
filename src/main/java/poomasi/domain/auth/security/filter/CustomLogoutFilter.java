

package poomasi.domain.auth.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.filter.OncePerRequestFilter;
import poomasi.domain.auth.token.util.JwtUtil;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class CustomLogoutFilter extends LogoutFilter {

    private JwtUtil jwtUtil;

    public CustomLogoutFilter(JwtUtil jwtUtil, LogoutSuccessHandler logoutSuccessHandler, LogoutHandler... handlers) {
        super(logoutSuccessHandler, handlers);
        this.jwtUtil=jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        log.info("[logout filter] - 로그아웃 진행합니다.");

        // POST : /api/logout 아니라면 넘기기
        String requestURI = request.getRequestURI();
        String requestMethod = request.getMethod();
        if (!"/api/logout".equals(requestURI) || !requestMethod.equals("POST")) {
            log.info("[logout url not matching] ");
            filterChain.doFilter(request, response);
            return;
        }


        boolean isLogoutSuccess = true;

        if(isLogoutSuccess){
            PrintWriter out = response.getWriter();
            out.println("logout success~. ");
            return;
        }

        /*
         * 로그아웃 로직
         * access token , refresh token 관리하기
         * */
        PrintWriter out = response.getWriter();
        out.println("logout success~. ");
        //return;
        //filterChain.doFilter(request, response);
    }
}

