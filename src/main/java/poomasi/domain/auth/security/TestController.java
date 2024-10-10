package poomasi.domain.auth.security;


import jdk.jfr.Description;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import poomasi.domain.auth.security.userdetail.UserDetailsImpl;
import poomasi.domain.member.entity.Member;

@Description("접근 제어 확인 controller")
@RestController
public class TestController {

    @Secured("ROLE_CUSTOMER")
    @GetMapping("/api/need-auth/customer")
    public String customer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object impl = authentication.getPrincipal();
        Member member = ((UserDetailsImpl) impl).getMember();

        System.out.println("email : " + member.getEmail());
        return "hi. customer";
    }

    @Secured("ROLE_FARMER")
    @GetMapping("/api/need-auth/farmer")
    public String farmer() {
        return "hi. farmer";
    }

    @GetMapping("/api/need-auth")
    public String needAuth() {
        return "auth";
    }

}
