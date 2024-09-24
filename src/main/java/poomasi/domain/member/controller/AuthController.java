package poomasi.domain.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poomasi.domain.member.service.MemberService;
import poomasi.domain.member.dto.request.LoginRequest;
import poomasi.domain.member.dto.response.LoginResponse;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse responseBody = memberService.login(loginRequest);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + responseBody.accessToken())
                .body(responseBody);
    }
}
