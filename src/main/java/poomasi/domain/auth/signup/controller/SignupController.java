package poomasi.domain.auth.signup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poomasi.domain.auth.signup.service.SignupService;
import poomasi.domain.auth.signup.dto.request.SignupRequest;

import static poomasi.domain.member.entity.LoginType.LOCAL;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SignupController {

    private final SignupService signupService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignupRequest signupRequest) {
        return ResponseEntity.ok(signupService
                .signUp(signupRequest));
    }

}
