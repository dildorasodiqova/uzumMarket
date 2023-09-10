package uz.pdp.uzummarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.uzummarket.entity.Basket;
import uz.pdp.uzummarket.entity.BasketProduct;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BasketProductRepository  extends JpaRepository<BasketProduct, UUID> {
    Optional<BasketProduct> findByBasketIdAndProductId(UUID basket_id, UUID product_id);
    List<BasketProduct> findAllByBasketId(UUID basket_id);
    @Modifying
    @Transactional
    @Query("UPDATE basketProduct bp " +
            "SET bp.count = bp.count + :count " +
            "WHERE bp.basket.id = :basketId AND bp.product.id = :productId")
    List<BasketProduct> updateCountByBasketIdAndProductId(UUID basketId, UUID productId, int count);
}
