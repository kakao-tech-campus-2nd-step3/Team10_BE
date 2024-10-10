package poomasi.domain.auth.security;


import jdk.jfr.Description;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Description("접근 제어 확인 controller")
@RestController
public class testController {

    @Secured("ROLE_CUSTOMER")
    @GetMapping("/api/need-auth/customer")
    public String customer() {
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
