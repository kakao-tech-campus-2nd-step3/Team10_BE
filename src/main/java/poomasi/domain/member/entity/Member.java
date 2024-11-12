package poomasi.domain.member.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import poomasi.domain.store.entity.Store;
import poomasi.domain.member._profile.entity.MemberProfile;
import poomasi.domain.order.entity._product.ProductOrder;
import poomasi.domain.wishlist.entity.WishList;
import poomasi.global.error.BusinessError;
import poomasi.global.error.BusinessException;
import java.util.*;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor
@SQLDelete(sql = "UPDATE member SET deleted_at = current_timestamp WHERE id = ?")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = true, length = 50)
    private String name;

    @Setter
    @Column(unique = true, nullable = true, length = 50)
    private String email;

    @Setter
    @Column(nullable = true)
    private String password;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private LoginType loginType;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Role role;

    @Column(nullable = true)
    private String provideId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private MemberProfile memberProfile;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WishList> wishLists;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOrder> productOrderLists;

    @Setter
    @Column(nullable = true)
    private String farmerTierCode;

    @Setter
    @OneToOne(mappedBy="owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Store store;

    public Member(String name, String email, String password, LoginType loginType, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.loginType = loginType;
        this.role = role;
        this.memberProfile = getOrCreateProfile();
    }

    @Builder
    public Member(Long id, String email, String password, Role role, LoginType loginType, String provideId, MemberProfile memberProfile) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.role = role;
        this.loginType = loginType;
        this.provideId = provideId;
        this.memberProfile = memberProfile;
    }

    public boolean isCustomer() {
        return role == Role.ROLE_CUSTOMER;
    }

    public boolean isFarmer() {
        return role == Role.ROLE_FARMER;
    }

    public boolean isAdmin() {
        return role == Role.ROLE_ADMIN;
    }

    public Store getStore() {
        if(store == null)
            throw new BusinessException(BusinessError.STORE_NOT_FOUND);
        return store;
    }

    public MemberProfile getOrCreateProfile() {
        if (this.memberProfile == null) {
            this.memberProfile = new MemberProfile();
        }
        return memberProfile;
    }

    public Store getOrCreateStore() {
        if (this.store == null) {
            this.store = new Store();
            this.store.setOwner(this);
        }
        return store;
    }



}
