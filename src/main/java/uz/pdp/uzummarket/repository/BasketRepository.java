package uz.pdp.uzummarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.uzummarket.entity.Basket;

import java.util.UUID;

public interface BasketRepository extends JpaRepository<Basket, UUID> {
    Basket getById(UUID basketId);
}
