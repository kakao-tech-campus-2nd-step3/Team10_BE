package poomasi.domain.order.entity._farm;


import jakarta.persistence.*;
import poomasi.domain.farm.entity.Farm;

@Entity
@Table(name = "ordered_farm")
public class OrderedFarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ordered_farm_id")
    private Long id;

    @OneToOne
    private Farm farm;


}
