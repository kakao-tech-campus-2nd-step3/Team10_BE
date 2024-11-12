package poomasi.domain.store.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import poomasi.domain.member.entity.Member;
import poomasi.domain.product.entity.Product;
import poomasi.domain.store.dto.StoreRegisterRequest;

@Entity
@NoArgsConstructor
@Getter
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String name;

    @Setter
    private String address;
    private String phone;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    private Member owner;

    private String ownerPhone;
    @Comment("사업자 번호")
    private String businessNumber;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    List<Product> products = new ArrayList<>();

    @Builder
    public Store(Long id, String name, String address, String phone, Member owner,
            String ownerPhone, String businessNumber) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.owner = owner;
        this.ownerPhone = ownerPhone;
        this.businessNumber = businessNumber;
    }

    public void updateStore(StoreRegisterRequest storeRegisterRequest) {
        this.name = storeRegisterRequest.name();
        this.address = storeRegisterRequest.address();
        this.phone = storeRegisterRequest.phone();
        this.ownerPhone = storeRegisterRequest.ownerPhone();
        this.businessNumber = storeRegisterRequest.businessNumber();
    }

    public void addProduct(Product saveProduct) {
        this.products.add(saveProduct);
    }
}
