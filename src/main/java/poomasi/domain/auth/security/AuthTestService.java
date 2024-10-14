package poomasi.domain.auth.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import poomasi.domain.auth.security.userdetail.UserDetailsImpl;
import poomasi.domain.member.entity.Member;

@Slf4j
@Service
public class AuthTestService {

    public String Test(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object impl = authentication.getPrincipal();
        Member member = ((UserDetailsImpl) impl).getMember();

        log.info("member : " + member.getEmail());
        log.info("member  : " + member.getId().toString());

        return "SUCCESS";
    }

}
