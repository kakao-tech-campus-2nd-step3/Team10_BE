package poomasi.domain.auth.token.reissue.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import poomasi.domain.auth.token.reissue.dto.ReissueRequest;
import poomasi.domain.auth.token.reissue.dto.ReissueResponse;
import poomasi.domain.auth.token.reissue.service.ReissueTokenService;

@RestController
@RequiredArgsConstructor
public class ReissueTokenController {

    private final ReissueTokenService reissueTokenService;

    @PostMapping("/api/reissue")
    public ResponseEntity<ReissueResponse> reissue(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                                   @RequestBody ReissueRequest reissueRequest){

        String accessToken = authorizationHeader.replace("Bearer ", "");

        return ResponseEntity.ok(reissueTokenService.reissueToken(accessToken, reissueRequest));
    }

}
