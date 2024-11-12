package poomasi.domain.member.dto.request;

public record FarmerUpdateRequest(
        String name,
        String email,
        String password,
        String phoneNumber,
        String storeName,
        String storeAddress) {
}
