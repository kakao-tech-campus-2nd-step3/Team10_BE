package poomasi.global.config.s3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import poomasi.global.util.EncryptionUtil;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class S3PresignedUrlServiceTest {

    private S3PresignedUrlService s3PresignedUrlService;

    @Autowired
    private AwsProperties awsProperties;

    @BeforeEach
    public void setUp() {
        String accessKey = awsProperties.getAccess();
        String secretKey = awsProperties.getSecret();
        String region = awsProperties.getS3().getRegion();

        // 자격 증명 설정
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                accessKey,
                secretKey
        );

        // S3Presigner 인스턴스 생성
        S3Presigner presigner = S3Presigner.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .region(Region.of(region))
                .build();

        // S3PresignedUrlService 초기화
        s3PresignedUrlService = new S3PresignedUrlService(presigner, new EncryptionUtil());
    }

    @Test
    public void testCreatePresignedGetUrl() {
        String objectKey = "object_key";
        String bucketName = awsProperties.getS3().getBucket();

        String presignedUrl = s3PresignedUrlService.createPresignedGetUrl(bucketName, objectKey);

        assertNotNull(presignedUrl);
        System.out.println("Presigned GET URL: " + presignedUrl);
    }

    @Test
    public void testCreatePresignedPutUrl() {
        String keyPrefix = "uploads";
        String bucketName = awsProperties.getS3().getBucket();

        // 메타데이터 생성
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", "image/jpg");
        metadata.put("x-amz-meta-title", "Test Image");

        // presigned PUT URL 생성
        String presignedUrl = s3PresignedUrlService.createPresignedPutUrl(bucketName, keyPrefix, metadata);

        assertNotNull(presignedUrl);
        System.out.println("Presigned PUT URL: " + presignedUrl);
    }
}
