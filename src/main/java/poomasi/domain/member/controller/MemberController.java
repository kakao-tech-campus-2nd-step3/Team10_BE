package poomasi.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poomasi.domain.member.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/member")
public class MemberController {

    private final MemberService memberService;

    @PutMapping("/toFarmer/{memberId}")
    public ResponseEntity<Void> upgradeToFarmer(@PathVariable Long memberId,
                                                @RequestBody Boolean hasFarmerQualification) {
        memberService.upgradeToFarmer(memberId, hasFarmerQualification);
        return ResponseEntity.noContent().build();
    }
}
