package poomasi.global.config.s3.dto.request;

import java.util.Map;

public record PresignedUrlPutRequest(String keyPrefix, Map<String, String> metadata) {
}
