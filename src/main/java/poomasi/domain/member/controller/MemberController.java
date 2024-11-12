package poomasi.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import poomasi.domain.auth.security.userdetail.UserDetailsImpl;
import poomasi.domain.member._profile.dto.request.AddressUpdateRequest;
import poomasi.domain.member.dto.request.CustomerUpdateRequest;
import poomasi.domain.member.dto.request.FarmerUpdateRequest;
import poomasi.domain.member.dto.response.*;
import poomasi.domain.member.entity.Member;
import poomasi.domain.member.service.MemberService;
import poomasi.domain.member.dto.request.SignupRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody SignupRequest signupRequest) {
        return ResponseEntity.ok(memberService
                .signUp(signupRequest));
    }

    @PutMapping("/toFarmer")
    @Secured("ROLE_CUSTOMER")
    public ResponseEntity<Void> convertToFarmer(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        memberService.convertToFarmer(member);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/toCustomer/{memberId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> convertToCustomer(@PathVariable Long memberId) {
        memberService.convertToCustomer(memberId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{memberId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<MemberResponse> getMemberById(@PathVariable Long memberId) {
        MemberResponse memberResponse = memberService.getMemberById(memberId);
        return ResponseEntity.ok(memberResponse);
    }

    @GetMapping("/summary")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Page<MemberSummaryResponse>> getMembersSummary(@PageableDefault(size = 10) Pageable pageable) {
        Page<MemberSummaryResponse> memberSummaryResponses = memberService.getAllMembersSummary(pageable);
        return ResponseEntity.ok(memberSummaryResponses);
    }

    @GetMapping("/self")
    @Secured({"ROLE_CUSTOMER", "ROLE_FARMER", "ROLE_ADMIN"})
    public ResponseEntity<MemberResponse> getSelfMember(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        MemberResponse memberResponse = memberService.getMemberById(member.getId());
        return ResponseEntity.ok(memberResponse);
    }

    @GetMapping("/summary/{memberId}")
    @Secured({"ROLE_CUSTOMER", "ROLE_FARMER", "ROLE_ADMIN"})
    public ResponseEntity<MemberSummaryResponse> getMemberSummaryById(@PathVariable Long memberId) {
        MemberSummaryResponse memberSummaryResponse = memberService.getMemberSummary(memberId);
        return ResponseEntity.ok(memberSummaryResponse);
    }

    @PutMapping("/customer/update")
    @Secured("ROLE_CUSTOMER")
    public ResponseEntity<MemberResponse> updateCustomer(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody CustomerUpdateRequest customerUpdateRequest) {

        Member member = userDetails.getMember();
        Member updatedMember = memberService.updateCustomer(member, customerUpdateRequest);

        MemberResponse memberResponse = MemberResponse.fromEntity(updatedMember);
        return ResponseEntity.ok(memberResponse);
    }

    @PutMapping("/farmer/update")
    @Secured("ROLE_FARMER")
    public ResponseEntity<FarmerResponse> updateFarmer(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody FarmerUpdateRequest farmerUpdateRequest) {

        Member member = userDetails.getMember();
        Member updatedMember = memberService.updateFarmer(member, farmerUpdateRequest);

        FarmerResponse memberResponse = FarmerResponse.fromEntity(updatedMember);
        return ResponseEntity.ok(memberResponse);
    }

    @PutMapping("/customer/update/address")
    @Secured("ROLE_CUSTOMER")
    public ResponseEntity<Void> updateAddress(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody AddressUpdateRequest addressUpdateRequest
    ) {
        Member member = userDetails.getMember();
        memberService.updateAddress(member, addressUpdateRequest);
        return ResponseEntity.ok().build();
    }

    // 회원 탈퇴, 복구, 금지
    // s3스케줄러 구현하긴해야함

    // 이미지 validator 타입 추가

    // 이미지 업로드 실패할시 처리





}
