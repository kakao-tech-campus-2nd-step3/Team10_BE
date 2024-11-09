package poomasi.domain.store.dto;

import org.hibernate.annotations.Comment;
import poomasi.domain.member.entity.Member;
import poomasi.domain.store.entity.Store;

public record StoreRegisterRequest(
        String name,
        String address,
        String phone,
        String ownerPhone,
        @Comment("사업자 번호")
        String businessNumber
) {

    public Store toEntity(Member member) {
        return Store.builder()
                .name(name)
                .address(address)
                .phone(phone)
                .ownerPhone(ownerPhone)
                .businessNumber(businessNumber)
                .owner(member)
                .build();
    }
}
