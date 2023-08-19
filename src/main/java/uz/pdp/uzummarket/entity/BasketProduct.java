package uz.pdp.uzummarket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "basketProduct")
@Table(name = "basket_product")
public class BasketProduct extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Basket basket;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;
    private int count;
}
