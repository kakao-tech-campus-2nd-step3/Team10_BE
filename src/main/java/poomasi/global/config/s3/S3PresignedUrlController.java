package poomasi.global.config.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poomasi.global.config.aws.AwsProperties;
import poomasi.global.config.s3.dto.request.PresignedUrlPutRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/s3")
public class S3PresignedUrlController {
    private final S3PresignedUrlService s3PresignedUrlService;
    private final AwsProperties awsProperties;

    @GetMapping("/presigned-url-get")
    public ResponseEntity<?> presignedUrlGet(@RequestParam String keyname) {
        String presignedGetUrl = s3PresignedUrlService.createPresignedGetUrl(awsProperties.getS3().getBucket(), keyname);
        return ResponseEntity.ok(presignedGetUrl);
    }

    @PostMapping("/presigned-url-put")
    public ResponseEntity<?> presignedUrlPut(@RequestBody PresignedUrlPutRequest request) {
        String presignedPutUrl = s3PresignedUrlService.createPresignedPutUrl(awsProperties.getS3().getBucket(), request.keyPrefix(), request.metadata());
        return ResponseEntity.ok(presignedPutUrl);
    }
}
