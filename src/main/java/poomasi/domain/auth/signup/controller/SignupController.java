package poomasi.domain.auth.signup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poomasi.domain.auth.signup.dto.response.SignUpResponse;
import poomasi.domain.auth.signup.service.SignupService;
import poomasi.domain.auth.signup.dto.request.SignupRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SignupController {

    private final SignupService signupService;

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody SignupRequest signupRequest) {
        return ResponseEntity.ok(signupService
                .signUp(signupRequest));
    }

}


