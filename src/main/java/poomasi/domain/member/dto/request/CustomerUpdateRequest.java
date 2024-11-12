package poomasi.domain.member.dto.request;

public record CustomerUpdateRequest(
        String name,
        String email,
        String password,
        String phoneNumber) {
}
