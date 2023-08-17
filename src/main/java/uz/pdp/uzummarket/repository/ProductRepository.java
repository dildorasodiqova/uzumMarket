package uz.pdp.uzummarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.uzummarket.entity.Product;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    void deleteById(UUID productId);
    List<Product> searchProductByCategory_NameOrNameContainingIgnoreCase(String word);
}
