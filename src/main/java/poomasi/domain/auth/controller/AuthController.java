package poomasi.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poomasi.domain.auth.dto.request.TokenRequest;
import poomasi.domain.auth.service.AuthService;
import poomasi.domain.auth.dto.request.SignUpRequest;

import static poomasi.domain.member.entity.LoginType.LOCAL;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    // 일반, 구매자 회원 가입
    @PostMapping("/sign-up")
    public ResponseEntity<Void> SignUp(@RequestBody SignUpRequest signUpRequest) {
        authService.signUp(signUpRequest, LOCAL);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/logout/{memberId}")
    public ResponseEntity<Void> logout(@PathVariable Long memberId, @RequestBody TokenRequest tokenRequest) {
        authService.logout(memberId, tokenRequest.accessToken());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
