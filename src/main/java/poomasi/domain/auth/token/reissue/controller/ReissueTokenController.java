package poomasi.domain.auth.token.reissue.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import poomasi.domain.auth.token.reissue.dto.ReissueRequest;
import poomasi.domain.auth.token.reissue.dto.ReissueResponse;
import poomasi.domain.auth.token.reissue.service.ReissueTokenService;

@RestController
public class ReissueTokenController {

    @Autowired
    private ReissueTokenService reissueTokenService;

    @GetMapping("/api/reissue")
    public ResponseEntity<ReissueResponse> reissue(@RequestBody ReissueRequest reissueRequest){
        return ResponseEntity.ok(reissueTokenService.reissueToken(reissueRequest));
    }
}
