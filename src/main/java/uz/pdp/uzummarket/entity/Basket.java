package uz.pdp.uzummarket.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name= "basket")
@Table(name = "basket")
public class Basket extends BaseEntity{
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int count;
    @ManyToOne(fetch = FetchType.LAZY)
    private Product products;
}
