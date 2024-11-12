package poomasi.global.config.s3.dto.response;

public record PresignedPutUrlResponse(
        String presignedPutUrl,
        String keyName,
        String objectUrl) {

}