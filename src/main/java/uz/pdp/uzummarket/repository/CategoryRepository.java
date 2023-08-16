package uz.pdp.uzummarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.uzummarket.entity.Category;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> getCategoryById(UUID id);
    Optional<Category> getByName(String name);
}
