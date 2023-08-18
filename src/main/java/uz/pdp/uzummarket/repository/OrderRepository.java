package uz.pdp.uzummarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.uzummarket.entity.Order;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
