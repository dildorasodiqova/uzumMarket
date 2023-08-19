package uz.pdp.uzummarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.uzummarket.entity.Basket;
import uz.pdp.uzummarket.entity.BasketProduct;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BasketProductRepository  extends JpaRepository<BasketProduct, UUID> {
    Optional<BasketProduct> findByBasketIdAndProductId(UUID basket_id, UUID product_id);
    List<BasketProduct> findAllByBasketId(UUID basket_id);
}
