package poomasi.domain.member._profile.dto.request;

public record AddressUpdateRequest(
        String defaultAddress,
        String addressDetail,
        Long coordinateX,
        Long coordinateY) {
}
