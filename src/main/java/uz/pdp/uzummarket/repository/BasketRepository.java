package uz.pdp.uzummarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.uzummarket.entity.Basket;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BasketRepository extends JpaRepository<Basket, UUID> {
    Basket getById(UUID basketId);
    Optional<Basket> findBasketByUserId(UUID userId);
    Basket getBasketByUser_Id(UUID userId);
}
