package poomasi.domain.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poomasi.domain.auth.dto.response.TokenResponse;
import poomasi.domain.member.dto.request.BusinessRegistrationRequest;
import poomasi.domain.member.service.MemberService;
import poomasi.domain.member.dto.request.LoginRequest;

import static poomasi.domain.member.entity.LoginType.LOCAL;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private MemberService memberService;

    // 일반, 구매자 회원 가입
    @PostMapping("/sign_up")
    public ResponseEntity<TokenResponse> signUp(@RequestBody LoginRequest loginRequest) {
        TokenResponse responseBody = memberService.signUp(loginRequest, LOCAL);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + responseBody.accessToken())
                .body(responseBody);
    }

    // 농부로 업그레이드
    @PutMapping("/toFarmer/{memberId}")
    public ResponseEntity<Void> upgradeToFarmer(@PathVariable Long memberId,
                                                @RequestBody BusinessRegistrationRequest request) {
        memberService.upgradeToFarmer(memberId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/logout/{memberId}")
    public ResponseEntity<Void> logout(@PathVariable Long memberId) {
        memberService.logout(memberId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
