package uz.pdp.uzummarket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.uzummarket.enums.OrderStatus;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "order")
@Table(name = "orders")
public class Order extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;
    @Column(nullable = false)
    private double price; ///total price

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.NEW;

    private boolean delivery; /// true bo'lsa uyiga yetkazgan bo'ladi, false bo'lsa punkitga

    public Order(User user, double price, OrderStatus status, boolean b) {
        this.user = user;
        this.price = price;
        this.status = status;
        this.delivery = b;
    }
}
