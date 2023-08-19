package uz.pdp.uzummarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.uzummarket.entity.Order;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findAllByUserId(UUID user_id) ;
}
