package poomasi.global.config.s3;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("aws")
public class AwsProperties {
    private String access;
    private String secret;
    private S3Properties s3;

    @Data
    public static class S3Properties {
        private String bucket;
        private String region;
    }
}
