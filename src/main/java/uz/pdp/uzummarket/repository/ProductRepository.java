package uz.pdp.uzummarket.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.uzummarket.entity.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Page<Product> findAllByUserIdAndCategoryId(UUID sellerId, UUID categoryId,PageRequest pageRequest);
    List<Product> getAllByUserIdAndCategory_id(UUID sellerId, UUID categoryId);
    void deleteById(UUID productId);
    List<Product> searchProductByCategory_NameOrNameContainingIgnoreCase(String category_name, String name);
    List<Product> getProductsByCategory_IdAndUser_Id(UUID categoryId , UUID sellerId);

    Optional<Object> findByName(String name);
}
