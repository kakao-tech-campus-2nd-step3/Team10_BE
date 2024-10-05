package poomasi.domain.auth.reissue.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import poomasi.domain.auth.reissue.service.ReissueTokenService;

@RestController
public class ReissueTokenController {

    @Autowired
    private ReissueTokenService reissueTokenService;

    @GetMapping("/api/reissue")
    public ResponseEntity<?> reissue(){
        return ResponseEntity.ok().build();
    }
}
