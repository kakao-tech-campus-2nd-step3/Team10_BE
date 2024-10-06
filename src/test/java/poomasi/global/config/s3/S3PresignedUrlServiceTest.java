package poomasi.global.config.s3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import poomasi.global.util.EncryptionUtil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class S3PresignedUrlServiceTest {

    @Autowired
    private S3PresignedUrlService s3PresignedUrlService;

    @Autowired
    private EncryptionUtil encryptionUtil;

    private final String bucketName = "poomasi";
    private final String keyPrefix = "test-files";

    @Test
    void testCreatePresignedPutUrl() throws IOException {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("uploadedBy", "test-user");

        // Presigned PUT URL 생성
        String presignedPutUrl = s3PresignedUrlService.createPresignedPutUrl(bucketName, keyPrefix, metadata);
        System.out.println("Generated PUT URL: " + presignedPutUrl);

        // URL을 사용해 실제로 파일을 업로드할 수 있는지 테스트
        URL url = new URL(presignedPutUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("PUT");

        // PUT 요청 전송 후 응답 코드 확인
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);
        assertThat(responseCode).isEqualTo(200);
    }

    @Test
    void testCreatePresignedGetUrl() throws IOException {
        String keyName = keyPrefix + "/your-file.jpg"; // 실제 파일 이름으로 변경

        // Presigned GET URL 생성
        String presignedGetUrl = s3PresignedUrlService.createPresignedGetUrl(bucketName, keyName);
        System.out.println("Generated GET URL: " + presignedGetUrl);

        // URL을 사용해 실제로 파일을 다운로드할 수 있는지 테스트
        URL url = new URL(presignedGetUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // GET 요청 전송 후 응답 코드 확인
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);
        assertThat(responseCode).isEqualTo(200);
    }
}
