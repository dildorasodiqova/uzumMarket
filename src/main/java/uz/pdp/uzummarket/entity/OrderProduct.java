package uz.pdp.uzummarket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "order_product")
@Table(name = "order_product")
public class OrderProduct extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private Double price;
}

