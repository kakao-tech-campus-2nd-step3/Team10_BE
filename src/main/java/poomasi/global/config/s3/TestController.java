package poomasi.global.config.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final S3PresignedUrlService s3PresignedUrlService;

    @GetMapping("/presigned-url-put")
    public ResponseEntity<?> presignedUrlPut() {
        String presignedPutUrl = s3PresignedUrlService.createPresignedPutUrl("poomasi", "test", null);
        return ResponseEntity.ok(presignedPutUrl);
    }

    @GetMapping("/presigned-url-get")
    public ResponseEntity<?> presignedUrlGet(@RequestParam String keyname) {
        String presignedGetUrl = s3PresignedUrlService.createPresignedGetUrl("poomasi", keyname);
        return ResponseEntity.ok(presignedGetUrl);
    }
}
