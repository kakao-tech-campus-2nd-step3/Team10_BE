package poomasi.global.config.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import poomasi.global.config.aws.AwsProperties;
import poomasi.global.config.s3.dto.request.PresignedUrlPutRequest;
import poomasi.global.config.s3.dto.response.PresignedPutUrlResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/s3")
public class S3PresignedUrlController {
    private final S3PresignedUrlService s3PresignedUrlService;
    private final AwsProperties awsProperties;

    @GetMapping("/presigned-url-get")
    @Secured({"ROLE_CUSTOMER", "ROLE_FARMER", "ROLE_ADMIN"})
    public ResponseEntity<?> presignedUrlGet(@RequestParam String keyname) {
        String presignedGetUrl = s3PresignedUrlService.createPresignedGetUrl(awsProperties.getS3().getBucket(), keyname);
        return ResponseEntity.ok(presignedGetUrl);
    }

    @PostMapping("/presigned-url-put")
    @Secured({"ROLE_CUSTOMER", "ROLE_FARMER", "ROLE_ADMIN"})
    public ResponseEntity<?> presignedUrlPut(@RequestBody PresignedUrlPutRequest request) {
        PresignedPutUrlResponse presignedPutUrl = s3PresignedUrlService.createPresignedPutUrl(
                awsProperties.getS3().getBucket(),
                awsProperties.getS3().getRegion(),
                request.keyPrefix(), request.metadata());
        return ResponseEntity.ok(presignedPutUrl);
    }
}
