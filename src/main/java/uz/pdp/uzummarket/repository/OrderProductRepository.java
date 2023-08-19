package uz.pdp.uzummarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.uzummarket.entity.OrderProduct;

import java.util.UUID;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, UUID> {
}
